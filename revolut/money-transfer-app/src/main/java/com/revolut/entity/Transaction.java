package com.revolut.entity;

import com.revolut.enums.TransactionState;
import com.revolut.enums.TransactionType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents transaction
 *
 * @author vsushko
 */
@NamedQueries({
        @NamedQuery(name = "Transaction.findAll",
                query = "SELECT t FROM Transaction t"),
        @NamedQuery(name = "Transaction.findByTransactionId",
                query = "SELECT t FROM Transaction t where t.transactionId = :transactionId")
})
@Entity
@Table(name = "transaction")
public class Transaction {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_generator")
    @SequenceGenerator(name = "transaction_generator", sequenceName = "transaction_seq")
    @Column(name = "Id", nullable = false)
    private Long id;

    /**
     * Creation date
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date",
            nullable = false)
    private Date creationDate;

    /**
     * Modification date
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "modification_date",
            nullable = false)
    private Date modificationDate;

    /**
     * Transaction id
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * State
     */
    @Enumerated(EnumType.STRING)
    private TransactionState state;

    /**
     * Type
     */
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * Amount
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * Account
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Default constructor
     */
    public Transaction() {
        super();
        this.state = TransactionState.NEW;
    }

    /**
     * @return the {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@link #creationDate}
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the {@link #creationDate}  to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the {@link #modificationDate}
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * @param modificationDate the {@link #modificationDate}  to set
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * @return the {@link #transactionId}
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the {@link #transactionId}  to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the {@link #state}
     */
    public TransactionState getState() {
        return state;
    }

    /**
     * @param state the {@link #state}  to set
     */
    public void setState(TransactionState state) {
        this.state = state;
    }

    /**
     * @return the {@link #type}
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the {@link #type}  to set
     */
    public void setType(TransactionType type) {
        this.type = type;
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
     * @return the {@link #account}
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the {@link #account}  to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
