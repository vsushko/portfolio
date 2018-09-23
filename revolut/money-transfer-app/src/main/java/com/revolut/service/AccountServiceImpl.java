package com.revolut.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.revolut.dao.AccountDAO;
import com.revolut.dao.CustomerDAO;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import com.revolut.enums.AccountStatus;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Implementation {@link AccountService}
 *
 * @author vsushko
 */
public class AccountServiceImpl implements AccountService {

    /**
     * @see AccountDAO
     */
    @Inject
    @Named("AccountDAO")
    private AccountDAO accountDao;

    /**
     * @see CustomerDAO
     */
    @Inject
    @Named("CustomerDAO")
    private CustomerDAO customerDao;

    @Override
    @Transactional
    public List<Account> getAccounts() {
        return accountDao.findAll();
    }

    @Override
    @Transactional
    public Account createAccount(Long customerId) {
        Account account = new Account();
        account.setCreationDate(new Date());
        account.setModificationDate(new Date());
        account.setBalance(BigDecimal.TEN);
        account.setOwner(getCustomer(customerId));
        account.setStatus(AccountStatus.ACTIVE);
        accountDao.createAccount(account);
        return account;
    }

    /**
     * Returns customer by id
     *
     * @param customerId customer id
     * @return customer
     */
    private Customer getCustomer(Long customerId) {
        return customerDao.findById(customerId);
    }
}
