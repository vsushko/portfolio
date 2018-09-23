package com.revolut;

import com.revolut.entity.Customer;

/**
 * Utils
 *
 * @author vsushko
 */
public final class Utils {

    /**
     * Use only allowed methods
     */
    private Utils() {
        super();
    }

    /**
     * Returns full name
     *
     * @param customer the customer
     * @return full name
     */
    public static String getFullName(Customer customer) {
        return String.join(" ", customer.getFirstName(), customer.getMiddleName(), customer.getLastName());
    }
}
