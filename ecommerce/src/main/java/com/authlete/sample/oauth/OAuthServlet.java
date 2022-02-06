package com.authlete.sample.oauth;

import com.authlete.sample.salesforce.IdResponse;
import org.glassfish.jersey.client.ClientConfig;

import java.io.*;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@WebServlet(name = "oauthServlet", value = "/oauth")
public class OAuthServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, OAuthService> oauthServices = OAuthUtils.getOAuthServices(getServletContext());

    response.setContentType("text/html");
    String nextPage = request.getContextPath() + "/";

    Map<String, String[]> params = request.getParameterMap();

    if (params.containsKey("unlink")) {
      request.getSession().removeAttribute(params.get("unlink")[0]);
    } else if (params.containsKey("link")) {
      String serviceName = params.get("link")[0];
      OAuthService service = oauthServices.get(serviceName);

      if (service == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Can't find OAuth service " + serviceName);
        return;
      }

      String authUri = service.getAuthUri();

      if (!OAuthUtils.isURL(authUri)) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Invalid authUri for OAuth service " + serviceName);
        return;
      }

      nextPage = authUri
          + "?response_type=code"
          + "&client_id=" + service.getClientId()
          + "&redirect_uri=" + service.getRedirectUri()
          + "&state=" + serviceName;
      for (Map.Entry<String, String> param : service.getQueryParams().entrySet()) {
        nextPage += "&" + param.getKey() + "=" +param.getValue();
      }
    } else {
      for (String param : new String[]{"code", "state"}) {
        if (!params.containsKey(param)) {
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          response.getWriter().println("Missing " + param + " parameter");
          return;
        }
      }
      String serviceName = params.get("state")[0];
      String code = params.get("code")[0];

      ClientConfig config = new ClientConfig();

      Client client = ClientBuilder.newClient(config);

      OAuthService service = oauthServices.get(serviceName);

      Form form = new Form();
      form.param("grant_type", "authorization_code");
      form.param("code", code);
      form.param("client_id", service.getClientId());
      form.param("client_secret", service.getClientSecret());
      form.param("redirect_uri", service.getRedirectUri());

      try {
        OAuthResponse oauthResponse = client.target(service.getTokenUri())
            .request()
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), OAuthResponse.class);

        String name = client.target(oauthResponse.getId()).request().header("Authorization",
            "Bearer " + oauthResponse.getAccessToken()
        ).get(IdResponse.class).getDisplayName();

        request.getSession().setAttribute(serviceName, name);
      } catch (BadRequestException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println(e.getResponse().readEntity(String.class));
        return;
      }
    }

    response.sendRedirect(nextPage);
  }
}