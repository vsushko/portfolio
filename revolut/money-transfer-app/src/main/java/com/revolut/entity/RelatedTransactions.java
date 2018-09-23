package com.revolut.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Stores foreign keys to the entities that are linked by the association
 *
 * @author vsushko
 */
@NamedQueries({
        @NamedQuery(name = "RelatedTransactions.findAll",
                query = "SELECT r FROM RelatedTransactions r"),
        @NamedQuery(name = "RelatedTransactions.getByDonorId",
                query = "SELECT r FROM RelatedTransactions r " +
                        "where r.donorTransaction.account.id = :donorAccountId " +
                        "and r.donorTransaction.transactionId = :donorTransactionId"),
        @NamedQuery(name = "RelatedTransactions.getByRecipientId",
                query = "SELECT r FROM RelatedTransactions r " +
                        "where r.recipientTransaction.account.id = :recipientAccountId " +
                        "and r.recipientTransaction.transactionId = :recipientTransactionId")
})
@Entity
@Table(name = "related_transactions")
public class RelatedTransactions {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "r_transactions_generator")
    @SequenceGenerator(name = "r_transactions_generator", sequenceName = "r_transactions_seq")
    @Column(name = "Id", nullable = false)
    private Long id;

    /**
     * Donor transaction
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_transaction_id")
    private Transaction donorTransaction;

    /**
     * Recipient transaction
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_transaction_id")
    private Transaction recipientTransaction;

    /**
     * @return the {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@link #donorTransaction}
     */
    public Transaction getDonorTransaction() {
        return donorTransaction;
    }

    /**
     * @param donorTransaction the {@link #donorTransaction}  to set
     */
    public void setDonorTransaction(Transaction donorTransaction) {
        this.donorTransaction = donorTransaction;
    }

    /**
     * @return the {@link #recipientTransaction}
     */
    public Transaction getRecipientTransaction() {
        return recipientTransaction;
    }

    /**
     * @param recipientTransaction the {@link #recipientTransaction}  to set
     */
    public void setRecipientTransaction(Transaction recipientTransaction) {
        this.recipientTransaction = recipientTransaction;
    }
}
