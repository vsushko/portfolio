package com.revolut;

import com.revolut.entity.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link Utils}
 *
 * @author vsushko
 */
public class UtilsTest {

    /**
     * Test {@link Utils#getFullName(Customer)}
     */
    @Test
    public void testGetFullName() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setMiddleName("MiddleName");
        customer.setLastName("LastName");
        assertEquals("FirstName MiddleName LastName", Utils.getFullName(customer));
    }
}