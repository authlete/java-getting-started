package com.authlete.sample.oauth;

import com.authlete.sample.ecommerce.entity.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OAuthUtils {
  private static final Logger logger = LogManager.getLogger();
  private static final Client client = ClientBuilder.newClient(new ClientConfig());
  private static OAuthService oauthService;

  public static synchronized OAuthService getOAuthService(ServletContext context) throws IOException {
    if (oauthService == null) {
      ObjectMapper mapper = new ObjectMapper();
      InputStream inputStream = context.getResourceAsStream("/WEB-INF/oauthService.json");
      oauthService = mapper.readValue(inputStream, OAuthService.class);

      // Read credentials from environment variables
      OAuthCredential credential = new OAuthCredential();
      credential.setClientId(System.getenv("CLIENT_ID"));
      credential.setClientSecret(System.getenv("CLIENT_SECRET"));

      oauthService.setCredential(credential);
    }

    return oauthService;
  }

  public static String renderServiceText(HttpServletRequest request) throws ServletException {
    // Retrieve OAuth response from session
    OAuthResponse oauthResponse = (OAuthResponse)request.getSession().getAttribute(OAuthResponse.class.getSimpleName());
    if (oauthResponse == null) {
      return null;
    }

    try {
      // Get the current customer data from the loyalty program API
      String currentCustomerUrl = oauthService.getApiEndpoint();
      logger.info("Calling loyalty program current customer API at {}", currentCustomerUrl);
      Response customerResponse = client.target(currentCustomerUrl)
              .request()
              .header("Authorization", "Bearer " + oauthResponse.getAccessToken())
              .get();

      // Check it worked!
      int status = customerResponse.getStatus();
      String contentType = customerResponse.getHeaderString("Content-Type");
      if (status == HttpServletResponse.SC_OK && "application/json".equals(contentType)) {
        Customer customer = customerResponse.readEntity(Customer.class);
        return "Welcome, " + customer.getName() + ", your points balance is " + customer.getAccount().getBalance();
      }

      // Something went wrong!
      ParameterizedMessage errorMessage = new ParameterizedMessage("Calling API {}\nservice responded with status {}, " +
              "content type \"{}\" and response body:\n{}", currentCustomerUrl, status, contentType,
              customerResponse.readEntity(String.class) );
      logger.error(errorMessage);
      throw new ServletException(errorMessage.getFormattedMessage());
    } catch (WebApplicationException e) {
      String errorMessage = "Exception retrieving current customer data";
      logger.error(errorMessage, e);
      throw new ServletException(errorMessage, e);
    }
  }

  public static boolean isURL(String url) {
    try {
      new URL(url);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
