package com.revolut.service;

import com.revolut.enums.AccountStatus;
import com.revolut.dao.AccountDAO;
import com.revolut.dao.CustomerDAO;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests {@link AccountServiceImpl}
 *
 * @author vsushko
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    /**
     * @see AccountDAO
     */
    @Mock
    private AccountDAO accountDAO;

    /**
     * @see CustomerDAO
     */
    @Mock
    private CustomerDAO customerDAO;

    /**
     * @see AccountServiceImpl
     */
    @InjectMocks
    private AccountServiceImpl serviceMock;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test {@link AccountServiceImpl#getAccounts()}
     */
    @Test
    public void testGetAccounts() {
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

        when(accountDAO.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<Account> accounts = serviceMock.getAccounts();
        assertNotNull(accounts);

        assertEquals(account1.getCreationDate(), accounts.get(0).getCreationDate());
        assertEquals(account1.getModificationDate(), accounts.get(0).getModificationDate());
        assertEquals(account1.getBalance(), accounts.get(0).getBalance());
        assertEquals(account1.getOwner().getId(), accounts.get(0).getOwner().getId());
        assertEquals(account1.getStatus(), accounts.get(0).getStatus());

        assertEquals(account2.getCreationDate(), accounts.get(1).getCreationDate());
        assertEquals(account2.getModificationDate(), accounts.get(1).getModificationDate());
        assertEquals(account2.getBalance(), accounts.get(1).getBalance());
        assertEquals(account2.getOwner().getId(), accounts.get(1).getOwner().getId());
        assertEquals(account2.getStatus(), accounts.get(1).getStatus());
    }

    /**
     * Test {@link AccountServiceImpl#createAccount(Long)}
     */
    @Test
    public void testCreateAccount() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");

        when(customerDAO.findById(customer.getId())).thenReturn(customer);

        Account account = serviceMock.createAccount(customer.getId());
        assertNotNull(account);
        assertNotNull(account.getCreationDate());
        assertNotNull(account.getModificationDate());
        assertEquals(BigDecimal.TEN, account.getBalance());
        assertEquals(customer.getId(), account.getOwner().getId());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
    }
}