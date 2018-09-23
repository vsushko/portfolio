package com.revolut.entity;

import com.revolut.enums.AccountStatus;

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
 * Represents account entity
 *
 * @author vsushko
 */
@NamedQueries({
        @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a where a.id = :id")
})
@Entity
@Table(name = "account")
public class Account {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq")
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
     * Status
     */
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /**
     * Customer
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    /**
     * Balance
     */
    @Column(name = "balance",
            nullable = false)
    private BigDecimal balance;

    /**
     * Reserved
     */
    @Column(name = "reserved")
    private BigDecimal reserved;

    /**
     * Default constructor
     */
    public Account() {
        super();
        this.status = AccountStatus.OPEN;
    }

    /**
     * @return the {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the {@link #id}  to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the {@link #status}
     */
    public AccountStatus getStatus() {
        return status;
    }

    /**
     * @param status the {@link #status}  to set
     */
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    /**
     * @return the {@link #owner}
     */
    public Customer getOwner() {
        return owner;
    }

    /**
     * @param owner the {@link #owner}  to set
     */
    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    /**
     * @return the {@link #balance}
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance the {@link #balance}  to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return the {@link #reserved}
     */
    public BigDecimal getReserved() {
        return reserved;
    }

    /**
     * @param reserved the {@link #reserved}  to set
     */
    public void setReserved(BigDecimal reserved) {
        this.reserved = reserved;
    }
}
