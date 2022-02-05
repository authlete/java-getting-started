package com.authlete.loyalty;

import com.authlete.loyalty.entity.Account;
import com.authlete.loyalty.entity.Customer;
import com.authlete.loyalty.entity.Transaction;
import com.authlete.loyalty.entity.TransactionType;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

public class LoyaltyDummyData {
  private static final Customer[] customers = {
      new Customer("00000001", "Tatsuo Kudo", "tatsuo"),
      new Customer("00000002", "Pat Patterson", "pat")
  };

  private static final Account[] accounts = {
    new Account("12345678", 0, customers[0]),
    new Account("12121212", 0, customers[1]),
  };

  private static final Transaction[] transactions = {
    new Transaction("2021-12-23T10:15:30.00Z", 123, TransactionType.EARNED_POINTS, accounts[0]),
    new Transaction("2022-01-03T22:43:52.00Z", 456, TransactionType.EARNED_POINTS, accounts[0]),
    new Transaction("2022-01-21T15:21:32.00Z", -234, TransactionType.CASH_REDEMPTION, accounts[0]),
    new Transaction("2022-01-22T16:12:23.00Z", -12, TransactionType.CASH_REDEMPTION, accounts[0]),
    new Transaction("2021-11-15T08:12:13.00Z", 654, TransactionType.EARNED_POINTS, accounts[1]),
    new Transaction("2021-12-30T11:45:51.00Z", -111, TransactionType.CASH_REDEMPTION, accounts[1]),
    new Transaction("2022-01-11T07:32:41.00Z", -222, TransactionType.CASH_REDEMPTION, accounts[1])
  };

  public static void load() {
    EntityManager entityManager = LoyaltyEntityManagerFactory.getInstance().createEntityManager();
    entityManager.getTransaction().begin();

    for (Customer customer : customers) {
      entityManager.persist(customer);
    }

    for (Account account : accounts) {
      entityManager.persist(account);
    }

    for (Transaction transaction : transactions) {
      entityManager.persist(transaction);
    }

    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
