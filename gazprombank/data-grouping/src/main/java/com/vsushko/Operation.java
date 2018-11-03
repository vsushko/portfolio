package com.vsushko;

import java.time.LocalDate;

/**
 * @author vsushko
 */
public class Operation {
    private LocalDate operationDate;
    private String officeNumber;
    private String operationNumber;
    private String operationSum;

    public Operation(LocalDate operationDate, String officeNumber, String operationNumber, String operationSum) {
        this.operationDate = operationDate;
        this.officeNumber = officeNumber;
        this.operationNumber = operationNumber;
        this.operationSum = operationSum;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getOperationSum() {
        return operationSum;
    }
}