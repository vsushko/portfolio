package com.revolut.dao;

import com.revolut.entity.RelatedTransactions;

import java.util.List;

/**
 * Related transactions DAO
 *
 * @author vsushko
 */
public interface RelatedTransactionsDAO {

    /**
     * Returns all existing related transactions
     *
     * @return related transactions list
     */
    List<RelatedTransactions> findAll();

    /**
     * Returns related transactions row by donor id
     *
     * @param donorId            the donor id
     * @param donorTransactionId the donor transaction id
     * @return related transactions
     */
    RelatedTransactions findByDonorTransactionId(Long donorId, String donorTransactionId);

    /**
     * Returns related transactions row by recipient id
     *
     * @param recipientId            the recipient id
     * @param recipientTransactionId the recipient transaction id
     * @return related transactions
     */
    RelatedTransactions findByRecipientTransactionId(Long recipientId, String recipientTransactionId);

    /**
     * Saves related transactions
     *
     * @param relatedTransactions related transactions
     */
    void save(RelatedTransactions relatedTransactions);
}
