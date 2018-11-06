package com.revolut.dao;

import com.revolut.entity.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Collection;

import static com.revolut.db.SessionFactoryUtil.getSessionFactory;

/**
 * Implementation {@link TransactionDAO}
 *
 * @author vsushko
 */
public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public Collection<Transaction> findAll() {
        Session session = getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        Collection<Transaction> transactions = session.createNamedQuery("Transaction.findAll").list();
        session.close();

        return transactions;
    }

    @Override
    public Transaction findByTransactionId(String transactionId) {
        Session session = getSessionFactory().openSession();

        Transaction transaction = (Transaction) session.createNamedQuery("Transaction.findByTransactionId")
                .setParameter("transactionId", transactionId).getSingleResult();
        session.close();

        return transaction;
    }

    @Override
    public void saveTransactions(Collection<Transaction> transactions) {
        org.hibernate.Transaction transaction = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();

            for (Transaction inputTransaction : transactions) {
                session.saveOrUpdate(inputTransaction);
            }

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

    @Override
    public void save(Transaction transaction) {
        org.hibernate.Transaction tx = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(transaction);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
