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
        Map<String, Object> authApiResponse =
                (Map<String, Object>) request.getSession().getAttribute("authApiResponse");
        request.getSession().removeAttribute("authApiResponse");
        logger.info("{} API response in the session", authApiResponse == null ? "No" : "Found an");
        if (authApiResponse == null) {
            initiateAuthleteAuthorization(request, response);
        } else {
            processAuthleteAuthorization(request, response, authApiResponse);
        }
    }

    private void initiateAuthleteAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Call the Authlete Authorization endpoint, wrapping the incoming query string in a JSON object
        Map<String, Object> authApiResponse = OAuthUtils.handleAuthleteApiCall(
                getServletContext(), response, "/auth/authorization",
                Collections.singletonMap("parameters", request.getQueryString()));

        // 2. handleAuthleteApiCall() returns null if it already returned a response to the client
        if (authApiResponse == null) {
            return;
        }

        // 3. Perform the action
        String action = (String)authApiResponse.get("action");
        if (action.equals("INTERACTION")) {
            List<Object> prompts = (List<Object>) authApiResponse.get("prompts");
            for (Object prompt : prompts) {
                if (prompt.equals("LOGIN")) {
                    request.getSession().setAttribute("authApiResponse", authApiResponse);
                    LoginUtils.redirectForLogin(request, response);
                    return;
                }
            }
        }

        // 4. We should never get here!
        Map<String, String> errorResponse = Map.of(
                "error", "unexpected_error",
                "error_description", "Contact the service owner for details"
        );
        OAuthUtils.setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper.writeValueAsString(errorResponse));
    }

    private void processAuthleteAuthorization(HttpServletRequest request, HttpServletResponse response, Map<String, Object> authApiResponse) throws IOException {
        // 1. Create a Map to send in the Authlete API request
        Map<String, Object> requestMap = new HashMap<>();

        // 2. Copy the ticket from the last API response into the map
        requestMap.put("ticket", authApiResponse.get("ticket"));

        // 3. Verify that the user is actually logged in
        UserAccount authenticatedUser = LoginUtils.getAuthenticatedUser(request.getSession());
        if (authenticatedUser == null) {
            requestMap.put("reason", "NOT_LOGGED_IN");
            OAuthUtils.handleAuthleteApiCall(getServletContext(), response, "/auth/authorization/fail", requestMap);
            return;
        }

        // 4. Issue the code
        requestMap.put("subject", authenticatedUser.getUsername());
        OAuthUtils.handleAuthleteApiCall(getServletContext(), response, "/auth/authorization/issue", requestMap);
    }
}