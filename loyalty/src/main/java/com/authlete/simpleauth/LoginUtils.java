package com.authlete.simpleauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;

public class LoginUtils {
  private static final String AUTHENTICATED_USER = "authenticatedUser";
  private static final String POST_LOGIN_URL = "postLoginUrl";

  // Return n secure random hex characters
  public static String getSalt(int n) {
    String zeros = "0".repeat(n);
    SecureRandom rnd = new SecureRandom();
    String str = Integer.toString(rnd.nextInt(1 << n), 16);
    return zeros.substring(str.length()) + str;
  }

  public static void setAuthenticatedUser(HttpSession session, UserAccount authenticatedUser) {
    session.setAttribute(AUTHENTICATED_USER, authenticatedUser);
  }

  public static UserAccount getAuthenticatedUser(HttpSession session) {
    return (session != null) ? (UserAccount)session.getAttribute(AUTHENTICATED_USER) : null;
  }

  public static void setPostLoginUrl(HttpSession session, String requestURI) {
    session.setAttribute(POST_LOGIN_URL, requestURI);
  }

  public static String getPostLoginUrl(HttpSession session) {
    return (String)session.getAttribute(POST_LOGIN_URL);
  }

  public static boolean isSecurePage(HttpServletRequest request) {
    // Index and css are the only pages you can see without authenticating
    String requestUri = request.getRequestURI();
    String contextPath = request.getContextPath();

    return !(requestUri.equals(contextPath + "/index.html") ||
        requestUri.equals(contextPath + "/") ||
        requestUri.startsWith(contextPath + "/css"));
  }
}
