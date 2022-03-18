package com.authlete.simpleauth;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequestWrapper;

public class UserRequestWrapper extends HttpServletRequestWrapper {

  private final String username;
  private final HttpServletRequest request;

  public UserRequestWrapper(String username, HttpServletRequest request) {
    super(request);
    this.username = username;
    this.request = request;
  }

  @Override
  public Principal getUserPrincipal() {
    if (this.username == null) {
      return request.getUserPrincipal();
    }

    return () -> username;
  }
}
