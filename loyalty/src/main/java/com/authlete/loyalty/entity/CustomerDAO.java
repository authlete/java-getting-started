package com.authlete.loyalty.entity;

import com.authlete.loyalty.LoyaltyEntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerDAO {
  public Set<Customer> getAll() {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Set<Customer> customers = new LinkedHashSet<>(entityManager.createNamedQuery("Customer.findAll").getResultList());
    entityManager.close();
    return customers;
  }

  public Customer get(String customerId) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Customer customer = entityManager.find(Customer.class, customerId);
    entityManager.close();
    return customer;
  }

  public Customer getByUsername(String username) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Customer customer = (Customer)entityManager.createNamedQuery("Customer.findByUsername")
        .setParameter("username", username)
        .getSingleResult();
    entityManager.close();
    return customer;
  }
}
