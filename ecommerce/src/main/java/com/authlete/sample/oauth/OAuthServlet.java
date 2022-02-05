package com.authlete.sample.oauth;

import com.authlete.sample.salesforce.IdResponse;
import org.glassfish.jersey.client.ClientConfig;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@WebServlet(name = "oauthServlet", value = "/oauth")
public class OAuthServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    String nextPage = request.getContextPath() + "/";

    if (request.getParameter("unlink") != null) {
      request.getSession().removeAttribute("salesforceId");
    } else if (request.getParameter("link") != null) {
      nextPage = "https://superpat-dev-ed.my.salesforce.com/services/oauth2/authorize"
          + "?client_id=3MVG9uudbyLbNPZPZmVBKNsYMq12NJX0gDMs1VMe6d7UJCl9HYHew63R.J6P1y3hVsSXVi1JFIBHraOgAFbQP"
          + "&redirect_uri=http://localhost:8080/ecommerce/oauth"
          + "&response_type=code"
          + "&prompt=consent";
    } else {
      String code = request.getParameter("code");

      ClientConfig config = new ClientConfig();

      Client client = ClientBuilder.newClient(config);

      Form form = new Form();
      form.param("grant_type", "authorization_code");
      form.param("code", code);
      form.param("client_id", "3MVG9uudbyLbNPZPZmVBKNsYMq12NJX0gDMs1VMe6d7UJCl9HYHew63R.J6P1y3hVsSXVi1JFIBHraOgAFbQP");
      form.param("client_secret", "3091496166142957194");
      form.param("redirect_uri", "http://localhost:8080/ecommerce/oauth");

      try {
        OAuthResponse oauthResponse = client.target("https://superpat-dev-ed.my.salesforce.com/services/oauth2/token")
            .request()
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), OAuthResponse.class);

        IdResponse id = client.target(oauthResponse.getId()).request().header("Authorization",
            "Bearer " + oauthResponse.getAccessToken()
        ).get(IdResponse.class);

        request.getSession().setAttribute("salesforceId", id);
      } catch (BadRequestException e) {
        response.getWriter().println(e.getResponse().readEntity(String.class));
        return;
      }
    }

    response.sendRedirect(nextPage);
  }

  public void destroy() {
  }
}