package com.authlete.loyalty;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LoyaltyEntityManagerFactory {
  private final EntityManagerFactory entityManagerFactory;
  private static LoyaltyEntityManagerFactory instance;

  private LoyaltyEntityManagerFactory() {
    entityManagerFactory = Persistence.createEntityManagerFactory( "com.authlete.loyalty" );
  }

  public static LoyaltyEntityManagerFactory getInstance() {
    if (instance == null)
      instance = new LoyaltyEntityManagerFactory();

    return instance;
  }

  public EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  public void close() {
    if (entityManagerFactory != null)
      entityManagerFactory.close();
  }
}
