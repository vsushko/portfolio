package com.revolut.service;

import com.revolut.entity.RelatedTransactions;

import java.util.Collection;

/**
 * Represents related transaction service
 *
 * @author vsushko
 */
public interface RelatedTransactionService {

    /**
     * Returns related transactions list
     *
     * @return related transactions
     */
    Collection<RelatedTransactions> getRelatedTransactions();

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
}
