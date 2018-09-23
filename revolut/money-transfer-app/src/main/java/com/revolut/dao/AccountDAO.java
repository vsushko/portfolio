package com.revolut.dao;

import com.revolut.entity.Account;

import java.util.List;

/**
 * Account DAO
 *
 * @author vsushko
 */
public interface AccountDAO {

    /**
     * Find all existing accounts
     *
     * @return accounts list
     */
    List<Account> findAll();

    /**
     * Returns account by id
     *
     * @param accountId the account id
     * @return account
     */
    Account findById(Long accountId);

    /**
     * Creates account
     *
     * @param account the account
     */
    void createAccount(Account account);

    /**
     * Saves account
     *
     * @param account the account
     */
    void save(Account account);
}
