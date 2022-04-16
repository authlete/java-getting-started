package com.authlete.sample.oauth;

import org.glassfish.jersey.client.ClientConfig;

import java.io.*;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebServlet(name = "oauthServlet", value = "/oauth")
public class OAuthServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    OAuthService oauthService = OAuthUtils.getOAuthService(getServletContext());

    response.setContentType("text/html");
    String nextPage = request.getContextPath() + "/";

    Map<String, String[]> params = request.getParameterMap();

    HttpSession session = request.getSession();

    if (params.containsKey("unlink")) {
      session.removeAttribute(params.get("unlink")[0]);
    } else if (params.containsKey("link")) {
      String authUri = oauthService.getAuthUri();

      if (!OAuthUtils.isURL(authUri)) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Invalid authUri: " + authUri);
        return;
      }

      nextPage = authUri
              + "?response_type=code"
              + "&client_id=" + oauthService.getClientId()
              + "&redirect_uri=" + oauthService.getRedirectUri();
      for (Map.Entry<String, String> param : oauthService.getQueryParams().entrySet()) {
        nextPage += "&" + param.getKey() + "=" +param.getValue();
      }
    } else {
      if (!params.containsKey("code")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Missing code parameter");
        return;
      }
      String code = params.get("code")[0];

      ClientConfig config = new ClientConfig();

      Client client = ClientBuilder.newClient(config);

      Form form = new Form();
      form.param("grant_type", "authorization_code");
      form.param("code", code);
      form.param("client_id", oauthService.getClientId());
      form.param("client_secret", oauthService.getClientSecret());
      form.param("redirect_uri", oauthService.getRedirectUri());

      try {
        Response res = client.target(oauthService.getTokenUri())
                .request()
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        int status = res.getStatus();
        if (status == HttpServletResponse.SC_OK) {
          session.setAttribute(OAuthResponse.class.getSimpleName(), res.readEntity(OAuthResponse.class));
        } else {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          response.getWriter().println(
                  "<h1>Service responded with status " + status + " and response body:</h1>\n" +
                          res.readEntity(String.class));
          return;
        }
      } catch (WebApplicationException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println(e.getResponse().readEntity(String.class));
        return;
      }
    }

    response.sendRedirect(nextPage);
  }
}