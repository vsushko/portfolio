package com.revolut.service;

import com.revolut.entity.Transaction;
import com.revolut.service.dto.AccountOperation;

import java.util.Collection;

/**
 * Represents transaction service
 *
 * @author vsushko
 */
public interface TransactionService {

    /**
     * Returns list of transactions
     *
     * @return transactions
     */
    Collection<Transaction> getTransactions();

    /**
     * Executes account operations
     *
     * @param accountOperation account operation
     * @return transactions
     */
    Collection executeOperation(AccountOperation accountOperation);
}
