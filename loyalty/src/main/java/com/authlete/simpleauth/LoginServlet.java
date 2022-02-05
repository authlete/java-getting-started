package com.authlete.simpleauth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    RequestDispatcher dispatcher
        = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UserAccount userAccount = UserAccountDAO.authenticateUser(username, password);

    if (userAccount == null) {
      String errorMessage = "Invalid username or Password";

      request.setAttribute("errorMessage", errorMessage);

      RequestDispatcher dispatcher
          = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

      dispatcher.forward(request, response);
      return;
    }

    LoginUtils.setAuthenticatedUser(request.getSession(), userAccount);

    String requestUri = LoginUtils.getPostLoginUrl(request.getSession());
    if (requestUri != null) {
      response.sendRedirect(requestUri);
    } else {
      response.sendRedirect(request.getContextPath() + "/overview.html");
    }
  }
}
