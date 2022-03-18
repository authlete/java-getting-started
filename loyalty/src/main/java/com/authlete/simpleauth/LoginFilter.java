package com.authlete.simpleauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="loginFilter")
public class LoginFilter implements Filter {
  private static final Logger logger = LogManager.getLogger();

  public LoginFilter() {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    logger.info("Requested {} to {}", request.getMethod(), request.getRequestURL());

    // 1. If the request is for a public page, we can allow it to proceed
    if (LoginUtils.isPublicPage(request)) {
      logger.info("Allowing request for public page");
      chain.doFilter(request, response);
      return;
    }

    // 2. Try to retrieve user information from the HTTP session
    HttpSession session = request.getSession();
    UserAccount authenticatedUser = LoginUtils.getAuthenticatedUser(session);

    // 3. If there is an authenticated user we attach their username to the request and allow it to proceed
    if (authenticatedUser != null) {
      logger.info("Allowing request for authenticated user");
      chain.doFilter(new UserRequestWrapper(authenticatedUser.getUsername(), request), response);
      return;
    }

    // 4. There is no authenticated user - redirect for login
    logger.info("Redirecting for login");
    LoginUtils.redirectForLogin(request, response);
  }

  @Override
  public void init(FilterConfig fConfig) {
  }

}
