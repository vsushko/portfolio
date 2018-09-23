package com.revolut.service;

import com.revolut.dao.AccountDAO;
import com.revolut.dao.RelatedTransactionsDAO;
import com.revolut.dao.TransactionDAO;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import com.revolut.entity.RelatedTransactions;
import com.revolut.entity.Transaction;
import com.revolut.enums.AccountStatus;
import com.revolut.enums.TransactionState;
import com.revolut.enums.TransactionType;
import com.revolut.service.dto.AccountOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.revolut.enums.AccountOperationType.ACCEPT_INCOME;
import static com.revolut.enums.AccountOperationType.ACCEPT_OUTCOME;
import static com.revolut.enums.AccountOperationType.CREATE_TRANSFER;
import static com.revolut.enums.AccountOperationType.REJECT_OUTCOME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests {@link TransactionServiceImpl}
 *
 * @author vsushko
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    /**
     * @see TransactionDAO
     */
    @Mock
    private TransactionDAO transactionDao;

    /**
     * @see AccountDAO
     */
    @Mock
    private AccountDAO accountDao;

    /**
     * @see RelatedTransactionsDAO
     */
    @Mock
    private RelatedTransactionsDAO relatedTransactionsDAO;

    /**
     * @see TransactionServiceImpl
     */
    @InjectMocks
    private TransactionServiceImpl serviceMock;

    /**
     * Test {@link TransactionServiceImpl#getTransactions()}
     */
    @Test
    public void testGetTransactions() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(BigDecimal.TEN);
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction outcomeTransaction = new Transaction();
        outcomeTransaction.setCreationDate(new Date());
        outcomeTransaction.setModificationDate(new Date());
        outcomeTransaction.setAccount(account1);
        outcomeTransaction.setType(TransactionType.OUTCOME);
        outcomeTransaction.setAmount(amount);
        outcomeTransaction.setState(TransactionState.PENDING);
        outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setCreationDate(new Date());
        incomeTransaction.setModificationDate(new Date());
        incomeTransaction.setAccount(account2);
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setState(TransactionState.NEW);
        incomeTransaction.setTransactionId(UUID.randomUUID().toString());

        when(transactionDao.findAll()).thenReturn(Arrays.asList(outcomeTransaction, incomeTransaction));

        List<Transaction> transactions = (List<Transaction>) serviceMock.getTransactions();
        assertNotNull(transactions);

        Transaction donorTransaction = transactions.get(0);
        assertNotNull(donorTransaction);
        assertEquals(outcomeTransaction.getCreationDate(), donorTransaction.getCreationDate());
        assertEquals(outcomeTransaction.getModificationDate(), donorTransaction.getModificationDate());
        assertEquals(outcomeTransaction.getAccount().getId(), donorTransaction.getAccount().getId());
        assertEquals(outcomeTransaction.getType(), donorTransaction.getType());
        assertEquals(outcomeTransaction.getAmount(), donorTransaction.getAmount());
        assertEquals(outcomeTransaction.getState(), donorTransaction.getState());
        assertEquals(outcomeTransaction.getTransactionId(), donorTransaction.getTransactionId());

        Transaction recipientTransaction = transactions.get(1);
        assertNotNull(recipientTransaction);
        assertEquals(incomeTransaction.getCreationDate(), recipientTransaction.getCreationDate());
        assertEquals(incomeTransaction.getModificationDate(), recipientTransaction.getModificationDate());
        assertEquals(incomeTransaction.getAccount().getId(), recipientTransaction.getAccount().getId());
        assertEquals(incomeTransaction.getType(), recipientTransaction.getType());
        assertEquals(incomeTransaction.getAmount(), recipientTransaction.getAmount());
        assertEquals(incomeTransaction.getState(), recipientTransaction.getState());
        assertEquals(incomeTransaction.getTransactionId(), recipientTransaction.getTransactionId());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteCreateTransferOperation() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(BigDecimal.TEN);
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        when(accountDao.findById(account1.getId())).thenReturn(account1);
        when(accountDao.findById(account2.getId())).thenReturn(account2);

        AccountOperation operation = new AccountOperation();
        operation.setOperation(CREATE_TRANSFER.getValue());
        operation.setDonorAccount(account1.getId());
        operation.setRecipientAccount(account2.getId());
        operation.setAmount(amount);

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);

        Transaction outcomeTransaction = transactions.get(0);
        assertNotNull(outcomeTransaction);
        assertNotNull(outcomeTransaction.getCreationDate());
        assertNotNull(outcomeTransaction.getModificationDate());
        assertNotNull(outcomeTransaction.getTransactionId());
        assertEquals(account1.getId(), outcomeTransaction.getAccount().getId());
        assertEquals(TransactionType.OUTCOME, outcomeTransaction.getType());
        assertEquals(amount, outcomeTransaction.getAmount());
        assertEquals(TransactionState.PENDING, outcomeTransaction.getState());

        Transaction incomeTransaction = transactions.get(1);
        assertNotNull(incomeTransaction);
        assertNotNull(incomeTransaction.getCreationDate());
        assertNotNull(incomeTransaction.getModificationDate());
        assertNotNull(incomeTransaction.getTransactionId());
        assertEquals(account2.getId(), incomeTransaction.getAccount().getId());
        assertEquals(TransactionType.INCOME, incomeTransaction.getType());
        assertEquals(amount, incomeTransaction.getAmount());
        assertEquals(TransactionState.NEW, incomeTransaction.getState());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteCreateTransferOperationWhenNotEnoughFunds() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setId(1L);
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(BigDecimal.ZERO);
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setId(2L);
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        when(accountDao.findById(account1.getId())).thenReturn(account1);

        AccountOperation operation = new AccountOperation();
        operation.setOperation(CREATE_TRANSFER.getValue());
        operation.setDonorAccount(account1.getId());
        operation.setRecipientAccount(account2.getId());
        operation.setAmount(amount);

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteAcceptOutcomeOperation() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(new BigDecimal("9"));
        account1.setReserved(new BigDecimal("1"));
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction outcomeTransaction = new Transaction();
        outcomeTransaction.setCreationDate(new Date());
        outcomeTransaction.setModificationDate(new Date());
        outcomeTransaction.setAccount(account1);
        outcomeTransaction.setType(TransactionType.OUTCOME);
        outcomeTransaction.setAmount(amount);
        outcomeTransaction.setState(TransactionState.PENDING);
        outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setCreationDate(new Date());
        incomeTransaction.setModificationDate(new Date());
        incomeTransaction.setAccount(account2);
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setState(TransactionState.NEW);
        incomeTransaction.setTransactionId(UUID.randomUUID().toString());

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setDonorTransaction(outcomeTransaction);
        relatedTransactions.setRecipientTransaction(incomeTransaction);

        AccountOperation operation = new AccountOperation();
        operation.setOperation(ACCEPT_OUTCOME.getValue());
        operation.setOperationUUID(outcomeTransaction.getTransactionId());

        when(transactionDao.findByTransactionId(outcomeTransaction.getTransactionId())).thenReturn(outcomeTransaction);
        when(relatedTransactionsDAO.findByDonorTransactionId(account1.getId(), outcomeTransaction.getTransactionId()))
                .thenReturn(relatedTransactions);

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);

        Transaction donorTransaction = transactions.get(0);
        assertNotNull(donorTransaction);
        assertEquals(new BigDecimal(String.valueOf("9")), donorTransaction.getAccount().getBalance());
        assertEquals(TransactionState.ACCEPTED, donorTransaction.getState());

        Transaction recipientTransaction = transactions.get(1);
        assertNotNull(recipientTransaction);
        assertEquals(TransactionState.PENDING, recipientTransaction.getState());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteRejectOutcomeOperation() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(new BigDecimal("9"));
        account1.setReserved(new BigDecimal("1"));
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction outcomeTransaction = new Transaction();
        outcomeTransaction.setCreationDate(new Date());
        outcomeTransaction.setModificationDate(new Date());
        outcomeTransaction.setAccount(account1);
        outcomeTransaction.setType(TransactionType.OUTCOME);
        outcomeTransaction.setAmount(amount);
        outcomeTransaction.setState(TransactionState.PENDING);
        outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setCreationDate(new Date());
        incomeTransaction.setModificationDate(new Date());
        incomeTransaction.setAccount(account2);
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setState(TransactionState.NEW);
        incomeTransaction.setTransactionId(UUID.randomUUID().toString());

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setDonorTransaction(outcomeTransaction);
        relatedTransactions.setRecipientTransaction(incomeTransaction);

        AccountOperation operation = new AccountOperation();
        operation.setOperation(REJECT_OUTCOME.getValue());
        operation.setOperationUUID(outcomeTransaction.getTransactionId());

        when(transactionDao.findByTransactionId(outcomeTransaction.getTransactionId())).thenReturn(outcomeTransaction);
        when(relatedTransactionsDAO.findByDonorTransactionId(account1.getId(), outcomeTransaction.getTransactionId()))
                .thenReturn(relatedTransactions);

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);

        Transaction donorTransaction = transactions.get(0);
        assertNotNull(donorTransaction);
        assertEquals(new BigDecimal(String.valueOf("10")), donorTransaction.getAccount().getBalance());
        assertEquals(new BigDecimal(String.valueOf("0")), donorTransaction.getAccount().getReserved());
        assertEquals(TransactionState.CANCELED, donorTransaction.getState());

        Transaction recipientTransaction = transactions.get(1);
        assertNotNull(recipientTransaction);
        assertEquals(TransactionState.CANCELED, recipientTransaction.getState());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteAcceptIncomeOperation() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        Account account1 = new Account();
        account1.setCreationDate(new Date());
        account1.setModificationDate(new Date());
        account1.setBalance(new BigDecimal("9"));
        account1.setReserved(BigDecimal.ZERO);
        account1.setOwner(customer1);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setCreationDate(new Date());
        account2.setModificationDate(new Date());
        account2.setBalance(BigDecimal.TEN);
        account2.setOwner(customer2);
        account2.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction outcomeTransaction = new Transaction();
        outcomeTransaction.setCreationDate(new Date());
        outcomeTransaction.setModificationDate(new Date());
        outcomeTransaction.setAccount(account1);
        outcomeTransaction.setType(TransactionType.OUTCOME);
        outcomeTransaction.setAmount(amount);
        outcomeTransaction.setState(TransactionState.COMPLETED);
        outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setCreationDate(new Date());
        incomeTransaction.setModificationDate(new Date());
        incomeTransaction.setAccount(account2);
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setState(TransactionState.PENDING);
        incomeTransaction.setTransactionId(UUID.randomUUID().toString());

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setDonorTransaction(outcomeTransaction);
        relatedTransactions.setRecipientTransaction(incomeTransaction);

        AccountOperation operation = new AccountOperation();
        operation.setOperation(ACCEPT_INCOME.getValue());
        operation.setOperationUUID(incomeTransaction.getTransactionId());

        when(transactionDao.findByTransactionId(incomeTransaction.getTransactionId())).thenReturn(incomeTransaction);
        when(relatedTransactionsDAO.findByRecipientTransactionId(
                account2.getId(), incomeTransaction.getTransactionId())).thenReturn(relatedTransactions);

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);

        Transaction donorTransaction = transactions.get(0);
        assertNotNull(donorTransaction);
        assertEquals(new BigDecimal(String.valueOf("9")), donorTransaction.getAccount().getBalance());
        assertEquals(BigDecimal.ZERO, donorTransaction.getAccount().getReserved());
        assertEquals(TransactionState.COMPLETED, donorTransaction.getState());

        Transaction recipientTransaction = transactions.get(1);
        assertNotNull(recipientTransaction);
        assertEquals(TransactionState.COMPLETED, recipientTransaction.getState());
        assertEquals(new BigDecimal(String.valueOf("11")), recipientTransaction.getAccount().getBalance());
    }

    /**
     * Test {@link TransactionServiceImpl#executeOperation(AccountOperation)}
     */
    @Test
    public void testExecuteUnknownOperation() {
        AccountOperation operation = new AccountOperation();

        List<Transaction> transactions = (List<Transaction>) serviceMock.executeOperation(operation);
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }
}