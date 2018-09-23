package com.revolut.service;

import com.revolut.entity.Account;

import java.util.List;

/**
 * Represents accounts service
 *
 * @author vsushko
 */
public interface AccountService {

    /**
     * Returns list of accounts
     *
     * @return accounts
     */
    List<Account> getAccounts();

    /**
     * Creates account
     *
     * @param customerId the account owner
     * @return account
     */
    Account createAccount(Long customerId);
}
