package com.revolut.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.revolut.dao.RelatedTransactionsDAO;
import com.revolut.entity.RelatedTransactions;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Implementation {@link RelatedTransactionService}
 *
 * @author vsushko
 */
public class RelatedTransactionServiceImpl implements RelatedTransactionService {

    /**
     * @see RelatedTransactionsDAO
     */
    @Inject
    @Named("RelatedTransactionsDAO")
    private RelatedTransactionsDAO relatedTransactionsDAO;

    @Override
    @Transactional
    public Collection<RelatedTransactions> getRelatedTransactions() {
        return relatedTransactionsDAO.findAll();
    }

    @Override
    @Transactional
    public RelatedTransactions findByDonorTransactionId(Long donorId, String donorTransactionId) {
        return relatedTransactionsDAO.findByDonorTransactionId(donorId, donorTransactionId);
    }

    @Override
    @Transactional
    public RelatedTransactions findByRecipientTransactionId(Long recipientId, String recipientTransactionId) {
        return relatedTransactionsDAO.findByRecipientTransactionId(recipientId, recipientTransactionId);
    }
}
