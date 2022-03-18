package com.authlete.simpleauth;

import org.apache.commons.codec.digest.DigestUtils;

public class UserAccount {
  private String username;
  private String passwordHash;
  private String salt;
  private long lastAuthenticationTime;

  public UserAccount() {
  }

  public UserAccount(String username, String password) {
    this.username = username;
    setPassword(password);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.salt = LoginUtils.getSalt(64);
    this.passwordHash = DigestUtils.sha256Hex(password + this.salt);
  }

  public boolean verifyPassword(String password) {
    boolean success = DigestUtils.sha256Hex(password + salt).equals(passwordHash);
    if (success) {
      lastAuthenticationTime = System.currentTimeMillis() / 1000L;
    }
    return success;
  }

  public long getLastAuthenticationTime() {
    return lastAuthenticationTime;
  }
}
