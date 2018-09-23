package com.revolut.dao;

import com.revolut.entity.Transaction;

import java.util.Collection;

/**
 * Transaction DAO
 *
 * @author vsushko
 */
public interface TransactionDAO {

    /**
     * Returns all existing transactions
     *
     * @return transactions list
     */
    Collection<Transaction> findAll();

    /**
     * Returns transaction with passed transaction id
     *
     * @param transactionId the transaction id
     * @return transaction
     */
    Transaction findByTransactionId(String transactionId);

    /**
     * Stores transactions
     *
     * @param transactions transactions list
     */
    void saveTransactions(Collection<Transaction> transactions);

    /**
     * Saves transaction
     *
     * @param transaction the transaction
     */
    void save(Transaction transaction);
}
