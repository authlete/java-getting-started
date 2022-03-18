package com.authlete.simpleauth.oauth;

import com.authlete.simpleauth.LoginUtils;
import com.authlete.simpleauth.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/oauth/authorization")
public class OAuthAuthorizationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> authApiResponse = (Map<String, Object>) request.getSession().getAttribute("authApiResponse");
        request.getSession().removeAttribute("authApiResponse");
        logger.info("{} API response in the session", authApiResponse == null ? "No" : "Found an");
        if (authApiResponse == null) {
            initiateAuthleteAuthorization(request, response);
        } else {
            processAuthleteAuthorization(request, response, authApiResponse);
        }
    }

    private void initiateAuthleteAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Get a Jersey HTTP client
        Client client = OAuthUtils.getClient(getServletContext());

        // 2. We will wrap the incoming query string in a JSON object
        Map<String, Object> requestMap = Collections.singletonMap("parameters", request.getQueryString());

        // 3. Call the Authlete Authorization endpoint
        String url = "https://api.authlete.com/api/auth/authorization";

        logger.info("Sending API request to {}:\n{}", url, OAuthUtils.prettyPrint(requestMap));

        // 4. Make the API call, parsing the JSON response into a map
        Map<String, Object> authApiResponse = client.target(url)
                .request()
                .post(Entity.entity(requestMap, MediaType.APPLICATION_JSON_TYPE), new GenericType<>() {
                });

        logger.info("Received API response:\n{}", OAuthUtils.prettyPrint(authApiResponse));

        // 5. 'action' tells us what to do next, 'responseContent' is the payload we'll return
        String action = (String) authApiResponse.get("action");
        String responseContent = (String) authApiResponse.get("responseContent");

        // 6. Perform the action
        switch (action) {
            case "INTERACTION":
                List<Object> prompts = (List<Object>) authApiResponse.get("prompts");
                for (Object prompt : prompts) {
                    if (prompt.equals("LOGIN")) {
                        // 7. Prompt the user to login
                        request.getSession().setAttribute("authApiResponse", authApiResponse);
                        LoginUtils.redirectForLogin(request, response);
                        return;
                    }
                }
                break;

            // 8. Handle errors
            case "INTERNAL_SERVER_ERROR":
                OAuthUtils.setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, responseContent);
                return;

            case "BAD_REQUEST":
                OAuthUtils.setResponseBody(response, HttpServletResponse.SC_BAD_REQUEST, responseContent);
                return;
        }

        // 9. We should never get here!
        Map<String, String> errorResponse = Map.of(
            "error", "unexpected_error",
            "error_description", "Contact the service owner for details"
        );
        OAuthUtils.setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper.writeValueAsString(errorResponse));
    }

    private void processAuthleteAuthorization(HttpServletRequest request, HttpServletResponse response, Map<String, Object> authApiResponse) throws IOException {
        // Not yet implemented!
        Map<String, String> errorResponse = Map.of(
            "error", "not_yet_implemented",
            "error_description", "This step is not yet implemented"
        );
        OAuthUtils.setResponseBody(response, HttpServletResponse.SC_NOT_IMPLEMENTED, mapper.writeValueAsString(errorResponse));
    }
}