package com.revolut.service;

import com.revolut.dao.RelatedTransactionsDAO;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import com.revolut.entity.RelatedTransactions;
import com.revolut.entity.Transaction;
import com.revolut.enums.AccountStatus;
import com.revolut.enums.TransactionState;
import com.revolut.enums.TransactionType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests {@link RelatedTransactionServiceImpl}
 *
 * @author vsushko
 */
public class RelatedTransactionServiceImplTest {

    /**
     * @see RelatedTransactionsDAO
     */
    @Mock
    private RelatedTransactionsDAO relatedTransactionsDAO;

    /**
     * @see RelatedTransactionServiceImpl
     */
    @InjectMocks
    private RelatedTransactionServiceImpl serviceMock;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test {@link RelatedTransactionServiceImpl#getRelatedTransactions()}
     */
    @Test
    public void testGetRelatedTransactions() {
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

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setDonorTransaction(outcomeTransaction);
        relatedTransactions.setRecipientTransaction(incomeTransaction);

        when(relatedTransactionsDAO.findAll()).thenReturn(Collections.singletonList(relatedTransactions));

        List<RelatedTransactions> relatedTransactionsList = (List<RelatedTransactions>) serviceMock.getRelatedTransactions();
        assertNotNull(relatedTransactionsList);

        RelatedTransactions queriedRelTransactions = relatedTransactionsList.get(0);
        Transaction donorTransaction = queriedRelTransactions.getDonorTransaction();
        assertNotNull(donorTransaction);
        assertEquals(outcomeTransaction.getCreationDate(), donorTransaction.getCreationDate());
        assertEquals(outcomeTransaction.getModificationDate(), donorTransaction.getModificationDate());
        assertEquals(outcomeTransaction.getAccount().getId(), donorTransaction.getAccount().getId());
        assertEquals(outcomeTransaction.getType(), donorTransaction.getType());
        assertEquals(outcomeTransaction.getAmount(), donorTransaction.getAmount());
        assertEquals(outcomeTransaction.getState(), donorTransaction.getState());
        assertEquals(outcomeTransaction.getTransactionId(), donorTransaction.getTransactionId());

        Transaction recipientTransaction = queriedRelTransactions.getRecipientTransaction();
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
     * Test {@link RelatedTransactionServiceImpl#findByDonorTransactionId(Long, String)}
     */
    @Test
    public void testFindByDonorTransactionId() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");

        Account account = new Account();
        account.setId(1L);
        account.setCreationDate(new Date());
        account.setModificationDate(new Date());
        account.setBalance(BigDecimal.TEN);
        account.setOwner(customer);
        account.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction outcomeTransaction = new Transaction();
        outcomeTransaction.setCreationDate(new Date());
        outcomeTransaction.setModificationDate(new Date());
        outcomeTransaction.setAccount(account);
        outcomeTransaction.setType(TransactionType.OUTCOME);
        outcomeTransaction.setAmount(amount);
        outcomeTransaction.setState(TransactionState.PENDING);
        outcomeTransaction.setTransactionId(UUID.randomUUID().toString());

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setDonorTransaction(outcomeTransaction);

        when(relatedTransactionsDAO.findByDonorTransactionId(
                outcomeTransaction.getAccount().getId(), outcomeTransaction.getTransactionId()))
                .thenReturn(relatedTransactions);

        RelatedTransactions relatedTransactionsMocked = serviceMock.findByDonorTransactionId(
                outcomeTransaction.getAccount().getId(), outcomeTransaction.getTransactionId());
        assertNotNull(relatedTransactionsMocked);

        Transaction donorTransaction = relatedTransactionsMocked.getDonorTransaction();
        assertNotNull(donorTransaction);
        assertEquals(outcomeTransaction.getCreationDate(), donorTransaction.getCreationDate());
        assertEquals(outcomeTransaction.getModificationDate(), donorTransaction.getModificationDate());
        assertEquals(outcomeTransaction.getAccount().getId(), donorTransaction.getAccount().getId());
        assertEquals(outcomeTransaction.getType(), donorTransaction.getType());
        assertEquals(outcomeTransaction.getAmount(), donorTransaction.getAmount());
        assertEquals(outcomeTransaction.getState(), donorTransaction.getState());
        assertEquals(outcomeTransaction.getTransactionId(), donorTransaction.getTransactionId());
    }

    /**
     * Test {@link RelatedTransactionServiceImpl#findByRecipientTransactionId(Long, String)}
     */
    @Test
    public void testGetRelatedIncomeTransactions() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");

        Account account = new Account();
        account.setCreationDate(new Date());
        account.setModificationDate(new Date());
        account.setBalance(BigDecimal.TEN);
        account.setOwner(customer);
        account.setStatus(AccountStatus.ACTIVE);

        BigDecimal amount = BigDecimal.ONE;

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setCreationDate(new Date());
        incomeTransaction.setModificationDate(new Date());
        incomeTransaction.setAccount(account);
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setAmount(amount);
        incomeTransaction.setState(TransactionState.NEW);
        incomeTransaction.setTransactionId(UUID.randomUUID().toString());

        RelatedTransactions relatedTransactions = new RelatedTransactions();
        relatedTransactions.setRecipientTransaction(incomeTransaction);

        when(relatedTransactionsDAO.findByRecipientTransactionId(
                incomeTransaction.getAccount().getId(), incomeTransaction.getTransactionId()))
                .thenReturn(relatedTransactions);

        RelatedTransactions relatedTransactionsMocked = serviceMock.findByRecipientTransactionId(
                incomeTransaction.getAccount().getId(), incomeTransaction.getTransactionId());
        assertNotNull(relatedTransactionsMocked);

        Transaction recipientTransaction = relatedTransactionsMocked.getRecipientTransaction();
        assertNotNull(recipientTransaction);
        assertEquals(incomeTransaction.getCreationDate(), recipientTransaction.getCreationDate());
        assertEquals(incomeTransaction.getModificationDate(), recipientTransaction.getModificationDate());
        assertEquals(incomeTransaction.getAccount().getId(), recipientTransaction.getAccount().getId());
        assertEquals(incomeTransaction.getType(), recipientTransaction.getType());
        assertEquals(incomeTransaction.getAmount(), recipientTransaction.getAmount());
        assertEquals(incomeTransaction.getState(), recipientTransaction.getState());
        assertEquals(incomeTransaction.getTransactionId(), recipientTransaction.getTransactionId());
    }
}