package com.revolut.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.revolut.Utils;
import com.revolut.dao.CustomerDAO;
import com.revolut.entity.Customer;
import com.revolut.enums.CustomerStatus;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Implementation {@link CustomerService}
 *
 * @author vsushko
 */
public class CustomerServiceImpl implements CustomerService {

    /**
     * @see CustomerDAO
     */
    @Inject
    @Named("CustomerDAO")
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.findAll();
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        customer.setCreationDate(new Date());
        customer.setModificationDate(new Date());
        customer.setFullName(Utils.getFullName(customer));
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setBirthDate(new Date());
        customerDAO.createCustomer(customer);
        return customer;
    }
}
