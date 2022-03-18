package com.authlete.loyalty;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class LoyaltyContextListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    LoyaltyDummyData.load();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    LoyaltyEntityManagerFactory.getInstance().close();
  }
}
