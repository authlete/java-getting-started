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
  private static final Object lock = new Object();
  private static volatile Map<String, OAuthService> oauthServices;
  private static final Client client = ClientBuilder.newClient(new ClientConfig());

  public static Map<String, OAuthService> getOAuthServices(ServletContext context) throws IOException {
    // Double-checked synchronization. See https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
    Map<String, OAuthService> localRef = oauthServices;
    if (localRef == null) {
      synchronized (lock) {
        localRef = oauthServices;
        if (localRef == null) {
          ObjectMapper mapper = new ObjectMapper();
          InputStream inputStream = context.getResourceAsStream("/WEB-INF/oauthServices.json");
          List<OAuthService> services = mapper.readValue(inputStream, new TypeReference<ArrayList<OAuthService>>() {});

          // Build a map for easy access to services
          oauthServices = localRef = new LinkedHashMap<>();
          for (OAuthService service : services) {
            localRef.put(service.getServiceName(), service);
          }

          // Store credentials in a separate file so it doesn't get checked into GitHub!
          inputStream = context.getResourceAsStream("/WEB-INF/oauthCredentials.json");
          List<OAuthCredential> credentials = mapper.readValue(inputStream, new TypeReference<ArrayList<OAuthCredential>>() {});

          // Merge the configurations
          for (OAuthCredential credential : credentials) {
            OAuthService service = localRef.get(credential.getServiceName());
            service.setCredential(credential);
          }
        }
      }
    }

    return localRef;
  }

  public static String renderServiceText(HttpServletRequest request, String serviceName) throws ServletException {
    // Retrieve OAuth response from session
    OAuthResponse oauthResponse = (OAuthResponse)request.getSession().getAttribute(serviceName);
    if (oauthResponse == null) {
      return null;
    }

    try {
      // Get the current customer data from the loyalty program API
      String currentCustomerUrl = oauthServices.get(serviceName).getApiEndpoint();
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
