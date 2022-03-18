package com.authlete.loyalty.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table( name = "ACCOUNT" )
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findTransaction", query = "SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber AND t.id = :transactionId")
})
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "accountNumber")
public class Account {
    @Id
    @Column(name="account_number")
    private String accountNumber;

    @Column(name="balance")
    private long balance;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable=false)
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy="account", targetEntity=Transaction.class, fetch = FetchType.EAGER)
    @OrderBy("timestamp DESC")
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
        this.balance += transaction.getAmount();
    }
}
