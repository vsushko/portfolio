package com.revolut.dao;

import com.revolut.entity.Transaction;
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
        Session session = getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        for (Transaction inputTransaction : transactions) {
            session.saveOrUpdate(inputTransaction);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public void save(Transaction transaction) {
        Session session = getSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.saveOrUpdate(transaction);
        tx.commit();
        session.close();
    }
}
