package com.revolut.service;

import com.revolut.entity.Customer;

import java.util.List;

/**
 * Represents customer service
 *
 * @author vsushko
 */
public interface CustomerService {

    /**
     * Returns list of customers
     *
     * @return customers
     */
    List<Customer> getCustomers();

    /**
     * Creates customer
     *
     * @param customer the customer
     * @return customer
     */
    Customer createCustomer(Customer customer);
}
