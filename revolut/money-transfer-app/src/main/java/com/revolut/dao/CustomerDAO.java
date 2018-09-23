package com.revolut.dao;

import com.revolut.entity.Customer;

import java.util.List;

/**
 * Customer DAO
 *
 * @author vsushko
 */
public interface CustomerDAO {

    /**
     * Returns all existing customers
     *
     * @return customers list
     */
    List<Customer> findAll();

    /**
     * Returns customer by id
     *
     * @param customerId the customer id
     */
    Customer findById(Long customerId);

    /**
     * Creates customer
     *
     * @param customer the customer
     */
    void createCustomer(Customer customer);
}
