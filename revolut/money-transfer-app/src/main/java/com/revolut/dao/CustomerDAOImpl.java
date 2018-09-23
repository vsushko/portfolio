package com.revolut.dao;

import com.revolut.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static com.revolut.db.SessionFactoryUtil.getSessionFactory;

/**
 * Implementation {@link CustomerDAO}
 *
 * @author vsushko
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> findAll() {
        Session session = getSessionFactory().openSession();

        @SuppressWarnings("unchecked")
        List<Customer> customers = session.createNamedQuery("Customer.findAll").list();
        session.close();

        return customers;
    }

    @Override
    public Customer findById(Long customerId) {
        Session session = getSessionFactory().openSession();
        Customer customer = (Customer) session.createNamedQuery("Customer.findById")
                .setParameter("id", customerId).getSingleResult();
        session.close();
        return customer;
    }

    @Override
    public void createCustomer(Customer customer) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(customer);
        transaction.commit();
        session.close();
    }
}
