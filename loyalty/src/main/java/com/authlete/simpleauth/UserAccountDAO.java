package com.authlete.simpleauth;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class UserAccountDAO {
  private static final Map<String, UserAccount> userMap = new HashMap<>();

  static {
    UserAccount emp = new UserAccount("tatsuo", "password");
    UserAccount mng = new UserAccount("pat", "password");

    userMap.put(emp.getUsername(), emp);
    userMap.put(mng.getUsername(), mng);
  }

  public static UserAccount authenticateUser(String username, String password) {
    UserAccount user = userMap.get(username);
    return (user != null && user.verifyPassword(password)) ? user : null;
  }

  public static UserAccount getUser(String username) {
    return userMap.get(username);
  }
}