package com.authlete.sample.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.LinkedHashSet;
import java.util.Set;

public class Account {
    private String accountNumber;

    private long balance;

    @JsonBackReference
    private Customer customer;

    @JsonManagedReference
    private Set<Transaction> transactions;

    public Account() {
    }

    public Account(String accountNumber, long balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
        this.customer.setAccount(this);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customer.setAccount(this);
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = new LinkedHashSet<>(transactions);
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new LinkedHashSet<>();
        }
        transactions.add(transaction);
    }
}
