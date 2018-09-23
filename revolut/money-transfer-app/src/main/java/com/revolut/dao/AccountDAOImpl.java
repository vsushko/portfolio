package com.revolut.dao;

import com.revolut.entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static com.revolut.db.SessionFactoryUtil.getSessionFactory;

/**
 * Implementation {@link AccountDAO}
 *
 * @author vsushko
 */
public class AccountDAOImpl implements AccountDAO {

    @Override
    public List<Account> findAll() {
        Session session = getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Account> accounts = session.createNamedQuery("Account.findAll").list();
        session.close();
        return accounts;
    }

    @Override
    public Account findById(Long accountId) {
        Session session = getSessionFactory().openSession();
        Account account = (Account) session.createNamedQuery("Account.findById")
                .setParameter("id", accountId).getSingleResult();
        session.close();
        return account;
    }

    @Override
    public void createAccount(Account account) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
        session.close();
    }

    @Override
    public void save(Account account) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(account);
        transaction.commit();
        session.close();
    }
}
