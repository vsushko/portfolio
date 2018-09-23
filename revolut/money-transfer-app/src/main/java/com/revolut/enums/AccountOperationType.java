package com.revolut.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Operation type enum
 *
 * @author vsushko
 */
public enum AccountOperationType {
    /**
     * Create transfer operation
     */
    CREATE_TRANSFER("create_transfer"),

    /**
     * Create accept outcome operation
     */
    ACCEPT_OUTCOME("accept_outcome"),

    /**
     * Create reject outcome operation
     */
    REJECT_OUTCOME("reject_outcome"),

    /**
     * Create accept income operation
     */
    ACCEPT_INCOME("accept_income");

    /**
     * Reverse-lookup map for getting a AccountOperationType from passed key
     */
    private static final Map<String, AccountOperationType> lookup = new HashMap<>();

    static {
        for (AccountOperationType operationType : AccountOperationType.values()) {
            lookup.put(operationType.getValue(), operationType);
        }
    }

    /**
     * Value
     */
    private String value;

    /**
     * Constructor with param
     *
     * @param value the value
     */
    AccountOperationType(String value) {
        this.value = value;
    }

    /**
     * Returns AccountOperationType by value
     *
     * @param value the value
     * @return account operation type
     */
    public static AccountOperationType get(String value) {
        return lookup.get(value);
    }

    /**
     * @return the {@link #value}
     */
    public String getValue() {
        return value;
    }
}
