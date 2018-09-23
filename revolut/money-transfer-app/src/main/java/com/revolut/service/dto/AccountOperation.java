package com.revolut.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Account operation DTO
 *
 * @author vsushko
 */
public class AccountOperation implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2001057173428309258L;

    /**
     * Customer id
     */
    private Long customerId;

    /**
     * Operation
     */
    private String operation;

    /**
     * Donor account
     */
    private Long donorAccount;

    /**
     * Recipient account
     */
    private Long recipientAccount;

    /**
     * Amount
     */
    private BigDecimal amount;

    /**
     * Operation UUID
     */
    private String operationUUID;

    /**
     * @return the {@link #customerId}
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the {@link #customerId}  to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the {@link #operation}
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the {@link #operation}  to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the {@link #donorAccount}
     */
    public Long getDonorAccount() {
        return donorAccount;
    }

    /**
     * @param donorAccount the {@link #donorAccount}  to set
     */
    public void setDonorAccount(Long donorAccount) {
        this.donorAccount = donorAccount;
    }

    /**
     * @return the {@link #recipientAccount}
     */
    public Long getRecipientAccount() {
        return recipientAccount;
    }

    /**
     * @param recipientAccount the {@link #recipientAccount}  to set
     */
    public void setRecipientAccount(Long recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    /**
     * @return the {@link #amount}
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the {@link #amount}  to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the {@link #operationUUID}
     */
    public String getOperationUUID() {
        return operationUUID;
    }

    /**
     * @param operationUUID the {@link #operationUUID}  to set
     */
    public void setOperationUUID(String operationUUID) {
        this.operationUUID = operationUUID;
    }
}
