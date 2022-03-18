package com.authlete.sample.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.Timestamp;
import java.time.Instant;

public class Transaction {
    private long id;

    private Timestamp timestamp;

    private long amount;

    private TransactionType transactionType;

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
