package com.authlete.loyalty.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table( name = "TRANSACTION" )
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t ORDER BY t.timestamp DESC")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name="timestamp")
    private Timestamp timestamp;

    @Column(name="amount")
    private long amount;

    @Column(name="transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="account_number", nullable=false)
    @JsonBackReference
    private Account account;

    public Transaction() {
    }

    public Transaction(String timestamp, long amount, TransactionType transactionType, Account account) {
        this.setTimestamp(timestamp);
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;

        this.account.addTransaction(this);
    }

    public void setAccount(Account account) {
        this.account = account;

        this.account.addTransaction(this);
    }

    public Account getAccount() {
        return account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    public String getTimestamp() {
        return timestamp.toInstant().toString();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = Timestamp.from(Instant.parse(timestamp));
    }
}
