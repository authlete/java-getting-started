package com.authlete.loyalty.entity;

import com.authlete.loyalty.LoyaltyEntityManagerFactory;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

public class AccountDAO {
  public Set<Account> getAll() {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Set<Account> accounts = new LinkedHashSet<>(entityManager.createNamedQuery("Account.findAll").getResultList());
    entityManager.close();
    return accounts;
  }

  public Account get(String accountNumber) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Account account = entityManager.find(Account.class, accountNumber);
    entityManager.close();
    return account;
  }

  public Transaction getTransactionFromAccount(String accountNumber, String transactionId) {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    Transaction transaction = (Transaction)entityManager.createNamedQuery("Account.findTransaction")
        .setParameter("accountNumber", accountNumber)
        .setParameter("transactionId", Long.parseLong(transactionId))
        .getSingleResult();
    entityManager.close();
    return transaction;
  }
}
