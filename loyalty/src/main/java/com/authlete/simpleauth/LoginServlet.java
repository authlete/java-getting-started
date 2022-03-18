package com.authlete.simpleauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final Logger logger = LogManager.getLogger();
  private static final long serialVersionUID = 1L;

  public LoginServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher dispatcher
        = request.getRequestDispatcher("/WEB-INF/views/loginView.jsp");

    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UserAccount userAccount = UserAccountDAO.authenticateUser(username, password);

    if (userAccount == null) {
      String errorMessage = "Invalid username or Password";

      logger.error(errorMessage + ": " + username);

      request.setAttribute("errorMessage", errorMessage);

      RequestDispatcher dispatcher
          = request.getRequestDispatcher("/WEB-INF/views/loginView.jsp");

      dispatcher.forward(request, response);
      return;
    }

    logger.info("Authenticated user: {}", username);
    LoginUtils.setAuthenticatedUser(session, userAccount);

    String postLoginUrl = LoginUtils.getPostLoginUrl(session, request.getContextPath() + "/overview.html");
    LoginUtils.clearPostLoginUrl(session);

    logger.info("Redirecting to: {}", postLoginUrl);
    response.sendRedirect(postLoginUrl);
  }
}
