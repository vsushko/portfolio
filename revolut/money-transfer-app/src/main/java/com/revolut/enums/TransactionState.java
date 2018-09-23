package com.revolut.enums;

/**
 * Transaction state
 *
 * @author vsushko
 */
public enum TransactionState {
    /**
     * New
     */
    NEW,

    /**
     * Pending
     */
    PENDING,

    /**
     * Approved
     */
    ACCEPTED,

    /**
     * Canceled
     */
    CANCELED,

    /**
     * Completed
     */
    COMPLETED
}