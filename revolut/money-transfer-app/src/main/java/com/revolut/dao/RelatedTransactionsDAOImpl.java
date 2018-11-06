package com.revolut.dao;

import com.revolut.entity.RelatedTransactions;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static com.revolut.db.SessionFactoryUtil.getSessionFactory;

/**
 * Implementation {@link RelatedTransactionsDAO}
 *
 * @author vsushko
 */
public class RelatedTransactionsDAOImpl implements RelatedTransactionsDAO {

    @Override
    public List<RelatedTransactions> findAll() {
        Session session = getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<RelatedTransactions> relatedTransactions = session.createNamedQuery("RelatedTransactions.findAll").list();
        session.close();

        return relatedTransactions;
    }

    @Override
    public RelatedTransactions findByDonorTransactionId(Long donorId, String donorTransactionId) {
        Session session = getSessionFactory().openSession();

        RelatedTransactions relatedTransactions =
                (RelatedTransactions) session.createNamedQuery("RelatedTransactions.getByDonorId")
                        .setParameter("donorAccountId", donorId)
                        .setParameter("donorTransactionId", donorTransactionId)
                        .getSingleResult();

        session.close();
        return relatedTransactions;
    }

    @Override
    public RelatedTransactions findByRecipientTransactionId(Long recipientId, String recipientTransactionId) {
        Session session = getSessionFactory().openSession();

        RelatedTransactions relatedTransactions =
                (RelatedTransactions) session.createNamedQuery("RelatedTransactions.getByRecipientId")
                        .setParameter("recipientAccountId", recipientId)
                        .setParameter("recipientTransactionId", recipientTransactionId)
                        .getSingleResult();
        session.close();
        return relatedTransactions;
    }

    @Override
    public void save(RelatedTransactions relatedTransactions) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(relatedTransactions);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
