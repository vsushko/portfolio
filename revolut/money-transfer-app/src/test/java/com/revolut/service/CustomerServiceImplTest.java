package com.revolut.service;

import com.revolut.enums.CustomerStatus;
import com.revolut.Utils;
import com.revolut.dao.CustomerDAO;
import com.revolut.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests {@link CustomerServiceImpl}
 *
 * @author vsushko
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    /**
     * @see CustomerDAO
     */
    @Mock
    private CustomerDAO customerDAO;

    /**
     * @see CustomerServiceImpl
     */
    @InjectMocks
    private CustomerServiceImpl serviceMock;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test {@link CustomerServiceImpl#getCustomers}
     */
    @Test
    public void testGetCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");

        when(customerDAO.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = serviceMock.getCustomers();
        assertNotNull(customers);

        assertEquals(customer1.getFirstName(), customers.get(0).getFirstName());
        assertEquals(customer2.getFirstName(), customers.get(1).getFirstName());
    }

    /**
     * Test {@link CustomerServiceImpl#createCustomer}
     */
    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setMiddleName("MiddleName");
        customer.setLastName("LastName");
        customer.setFullName(Utils.getFullName(customer));
        customer.setStatus(CustomerStatus.ACTIVE);

        Customer createdCustomer = serviceMock.createCustomer(customer);
        assertEquals(customer.getFirstName(), createdCustomer.getFirstName());
        assertEquals(customer.getMiddleName(), createdCustomer.getMiddleName());
        assertEquals(customer.getLastName(), createdCustomer.getLastName());
        assertEquals(customer.getFullName(), createdCustomer.getFullName());
        assertEquals(customer.getStatus(), createdCustomer.getStatus());
        assertNotNull(createdCustomer.getCreationDate());
        assertNotNull(createdCustomer.getModificationDate());
        assertNotNull(createdCustomer.getBirthDate());
    }
}
