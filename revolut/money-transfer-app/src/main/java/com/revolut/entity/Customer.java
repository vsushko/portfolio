package com.revolut.entity;

import com.revolut.enums.CustomerStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Represents customer entity
 *
 * @author vsushko
 */
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
        @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c where c.id = :id")
})
@Entity
@Table(name = "customer")
public class Customer {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_seq")
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
    private CustomerStatus status;

    /**
     * First name
     */
    @Column(name = "first_name",
            length = 64,
            nullable = false)
    private String firstName;

    /**
     * Middle name
     */
    @Column(name = "middle_name",
            length = 64,
            nullable = true)
    private String middleName;

    /**
     * Last name
     */
    @Column(name = "last_name",
            length = 64,
            nullable = false)
    private String lastName;

    /**
     * Full name
     */
    @Column(name = "full_name",
            length = 100,
            nullable = false)
    private String fullName;

    /**
     * Birth date
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date",
            nullable = false)
    private Date birthDate;

    /**
     * @return the {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@link #firstName}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the {@link #firstName}  to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the {@link #middleName}
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the {@link #middleName}  to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the {@link #lastName}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the {@link #lastName}  to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the {@link #fullName}
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the {@link #fullName}  to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the {@link #birthDate}
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the {@link #birthDate}  to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
    public CustomerStatus getStatus() {
        return status;
    }

    /**
     * @param status the {@link #status}  to set
     */
    public void setStatus(CustomerStatus status) {
        this.status = status;
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
}
