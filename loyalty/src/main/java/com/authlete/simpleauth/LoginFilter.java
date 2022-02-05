package com.authlete.simpleauth;

import java.io.IOException;

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

@WebFilter("/*")
public class LoginFilter implements Filter {
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

    String servletPath = request.getServletPath();
    HttpSession session = request.getSession();

    // User information stored in the Session.
    // (After successful login).
    UserAccount authenticatedUser = LoginUtils.getAuthenticatedUser(session);

    if (servletPath.equals("/login")) {
      // Carry on to login page
      chain.doFilter(request, response);
      return;
    }

    if (authenticatedUser != null) {
      // Carry on, with the user details
      chain.doFilter(new UserRequestWrapper(authenticatedUser.getUsername(), request), response);
      return;
    } else if (LoginUtils.isSecurePage(request)) {
      // Send the user to login
      LoginUtils.setPostLoginUrl(session, request.getRequestURI());
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    }

    // Allow unauthenticated user to see non-secure page
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig fConfig) {
  }

}
