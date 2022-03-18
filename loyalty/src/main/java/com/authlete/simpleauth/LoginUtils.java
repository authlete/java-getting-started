package com.authlete.simpleauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

  public static void redirectForLogin(HttpServletRequest request, HttpServletResponse response) throws
          IOException {
    String queryString = request.getQueryString();
    String postLoginUrl = request.getRequestURI() + ((queryString != null) ? "?" + queryString : "");
    request.getSession().setAttribute(POST_LOGIN_URL, postLoginUrl);
    response.sendRedirect(request.getContextPath() + "/login");
  }

  public static String getPostLoginUrl(HttpSession session, String defaultUrl) {
    String postLoginUrl = (String)session.getAttribute(POST_LOGIN_URL);
    if (postLoginUrl == null) {
      postLoginUrl = defaultUrl;
    }
    return postLoginUrl;
  }

  public static void clearPostLoginUrl(HttpSession session) {
    session.removeAttribute(POST_LOGIN_URL);
  }

  public static boolean isPublicPage(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    String contextPath = request.getContextPath();

    // The login page, front page and CSS path are public
    return requestUri.equals(contextPath + "/login") ||
            requestUri.equals(contextPath + "/index.html") ||
            requestUri.equals(contextPath + "/") ||
            requestUri.startsWith(contextPath + "/css") ||
            requestUri.startsWith(contextPath + "/oauth/");
  }
}
