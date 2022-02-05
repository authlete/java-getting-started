package com.authlete.loyalty.entity;

import com.authlete.loyalty.LoyaltyEntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

public class TransactionDAO {
  public Set<Transaction> getAll() {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Set<Transaction> transactions = new LinkedHashSet<>(entityManager.createNamedQuery("Transaction.findAll").getResultList());
    entityManager.close();
    return transactions;
  }

  public Transaction get(String id) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Transaction transaction = entityManager.find(Transaction.class, id);
    entityManager.close();
    return transaction;
  }

  public void create(String accountNumber, Transaction transaction) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    entityManager.getTransaction().begin();

    Account account = entityManager.find(Account.class, accountNumber);
    transaction.setAccount(account);
    entityManager.persist(account);
    entityManager.persist(transaction);

    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
