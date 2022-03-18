package com.authlete.sample.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.LinkedHashSet;
import java.util.Set;

public class Customer {
    private String id;

    private String name;

    private String username;

    @JsonManagedReference
    private Account account;

    public Customer() {
    }

    public Customer(String id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
