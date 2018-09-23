package com.revolut.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.revolut.dao.AccountDAO;
import com.revolut.dao.RelatedTransactionsDAO;
import com.revolut.dao.TransactionDAO;
import com.revolut.entity.Account;
import com.revolut.entity.RelatedTransactions;
import com.revolut.entity.Transaction;
import com.revolut.enums.AccountOperationType;
import com.revolut.enums.TransactionState;
import com.revolut.enums.TransactionType;
import com.revolut.service.dto.AccountOperation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * Implementation {@link TransactionService}
 *
 * @author vsushko
 */
public class TransactionServiceImpl implements TransactionService {

    /**
     * @see TransactionDAO
     */
    @Inject
    @Named("TransactionDAO")
    private TransactionDAO transactionDao;

    /**
     * @see AccountDAO
     */
    @Inject
    @Named("AccountDAO")
    private AccountDAO accountDao;

    /**
     * @see RelatedTransactionsDAO
     */
    @Inject
    @Named("RelatedTransactionsDAO")
    private RelatedTransactionsDAO relatedTransactionsDAO;

    @Override
    @Transactional
    public Collection<Transaction> getTransactions() {
        return transactionDao.findAll();
    }

    @Override
    public Collection<Transaction> executeOperation(AccountOperation operation) {
        AccountOperationType operationType = AccountOperationType.get(operation.getOperation());
        if (operationType != null) {
            switch (operationType) {
                case CREATE_TRANSFER:
                    return createTransfer(operation.getDonorAccount(), operation.getRecipientAccount(),
                            operation.getAmount());
                case ACCEPT_OUTCOME:
                    return acceptOutcome(operation.getOperationUUID());
                case REJECT_OUTCOME:
                    return rejectOutcome(operation.getOperationUUID());
                case ACCEPT_INCOME:
                    return acceptIncome(operation.getOperationUUID());
                default:
                    return Collections.EMPTY_LIST;
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Creates transfer from one account to another
     *
     * @param donorId     the donor account
     * @param recipientId the recipient account
     * @param amount      the amount
     * @return transactions
     */
    @Transactional
    private Collection<Transaction> createTransfer(Long donorId, Long recipientId, BigDecimal amount) {
        Account donorAccount = getAccount(donorId);

        BigDecimal balance = donorAccount.getBalance();
        BigDecimal result = balance.subtract(amount);

        if (result.compareTo(BigDecimal.ZERO) > 0) {
            donorAccount.setReserved(amount);
            donorAccount.setBalance(result);

            Transaction outcomeTransaction = new Transaction();
            outcomeTransaction.setCreationDate(new Date());
            outcomeTransaction.setModificationDate(new Date());
            outcomeTransaction.setAccount(donorAccount);
            outcomeTransaction.setType(TransactionType.OUTCOME);
            outcomeTransaction.setAmount(amount);
            outcomeTransaction.setState(TransactionState.PENDING);
            outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

            Transaction incomeTransaction = new Transaction();
            incomeTransaction.setCreationDate(new Date());
            incomeTransaction.setModificationDate(new Date());
            incomeTransaction.setAccount(getAccount(recipientId));
            incomeTransaction.setType(TransactionType.INCOME);
            incomeTransaction.setAmount(amount);
            incomeTransaction.setState(TransactionState.NEW);
            incomeTransaction.setTransactionId(UUID.randomUUID().toString());

            Collection<Transaction> transactions = Arrays.asList(outcomeTransaction, incomeTransaction);

            transactionDao.saveTransactions(transactions);

            RelatedTransactions relatedTransactions = new RelatedTransactions();
            relatedTransactions.setDonorTransaction(outcomeTransaction);
            relatedTransactions.setRecipientTransaction(incomeTransaction);
            relatedTransactionsDAO.save(relatedTransactions);

            return transactions;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Rejects outcome transaction
     *
     * @param operationUUID operationUUID
     * @return transactions
     */
    @Transactional
    private Collection<Transaction> rejectOutcome(String operationUUID) {
        Transaction donorTransaction = transactionDao.findByTransactionId(operationUUID);
        Account donorAccount = donorTransaction.getAccount();

        RelatedTransactions relatedTransactions = relatedTransactionsDAO.findByDonorTransactionId(
                donorAccount.getId(), donorTransaction.getTransactionId());
        Transaction recipientTransaction = relatedTransactions.getRecipientTransaction();

        if (TransactionType.OUTCOME.equals(donorTransaction.getType())
                && TransactionState.PENDING.equals(donorTransaction.getState())) {
            donorTransaction.setModificationDate(new Date());
            donorTransaction.setState(TransactionState.CANCELED);

            BigDecimal reserved = donorAccount.getReserved();
            BigDecimal amount = donorTransaction.getAmount();
            donorAccount.setModificationDate(new Date());
            donorAccount.setReserved(reserved.subtract(amount));
            donorAccount.setBalance(donorAccount.getBalance().add(amount));

            recipientTransaction.setModificationDate(new Date());
            recipientTransaction.setState(TransactionState.CANCELED);
            recipientTransaction.setModificationDate(new Date());
            transactionDao.save(recipientTransaction);

            accountDao.save(donorAccount);
            transactionDao.save(donorTransaction);
        }
        return Arrays.asList(donorTransaction, recipientTransaction);
    }

    /**
     * Accepts outcome transaction
     *
     * @param operationUUID the operationUUID
     * @return transactions
     */
    @Transactional
    private Collection<Transaction> acceptOutcome(String operationUUID) {
        Transaction donorTransaction = transactionDao.findByTransactionId(operationUUID);

        Account account = donorTransaction.getAccount();
        account.setModificationDate(new Date());
        account.setReserved(account.getReserved().subtract(donorTransaction.getAmount()));

        donorTransaction.setModificationDate(new Date());
        donorTransaction.setState(TransactionState.ACCEPTED);
        donorTransaction.setModificationDate(new Date());

        accountDao.save(account);
        transactionDao.save(donorTransaction);

        RelatedTransactions relatedTransactions = relatedTransactionsDAO.findByDonorTransactionId(
                account.getId(), donorTransaction.getTransactionId());
        Transaction recipientTransaction = relatedTransactions.getRecipientTransaction();
        recipientTransaction.setModificationDate(new Date());
        recipientTransaction.setState(TransactionState.PENDING);
        recipientTransaction.setModificationDate(new Date());
        transactionDao.save(recipientTransaction);

        return Arrays.asList(donorTransaction, recipientTransaction);
    }

    /**
     * Accepts income transaction
     *
     * @param operationUUID the operationUUID
     * @return transactions
     */
    @Transactional
    private Collection<Transaction> acceptIncome(String operationUUID) {
        Transaction recipientTransaction = transactionDao.findByTransactionId(operationUUID);
        Account recipientAccount = recipientTransaction.getAccount();

        RelatedTransactions relatedTransactions =
                relatedTransactionsDAO.findByRecipientTransactionId(
                        recipientAccount.getId(), recipientTransaction.getTransactionId());
        Transaction donorTransaction = relatedTransactions.getDonorTransaction();

        if (TransactionType.INCOME.equals(recipientTransaction.getType())
                && TransactionState.PENDING.equals(recipientTransaction.getState())) {

            BigDecimal balance = recipientAccount.getBalance();
            BigDecimal result = balance.add(recipientTransaction.getAmount());
            recipientAccount.setModificationDate(new Date());
            recipientAccount.setBalance(result);

            recipientTransaction.setModificationDate(new Date());
            recipientTransaction.setState(TransactionState.COMPLETED);
            donorTransaction.setModificationDate(new Date());
            donorTransaction.setState(TransactionState.COMPLETED);

            accountDao.save(recipientAccount);
            transactionDao.save(recipientTransaction);
            transactionDao.save(donorTransaction);
        }

        return Arrays.asList(donorTransaction, recipientTransaction);
    }

    /**
     * Returns account by id
     *
     * @param accountId account id
     * @return account
     */
    private Account getAccount(Long accountId) {
        return accountDao.findById(accountId);
    }
}
