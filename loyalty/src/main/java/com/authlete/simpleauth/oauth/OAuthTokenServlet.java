package com.authlete.simpleauth.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;


@WebServlet("/oauth/token")
public class OAuthTokenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Call the /auth/token endpoint, passing the request body
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> authApiResponse =
                OAuthUtils.handleAuthleteApiCall(getServletContext(), response, "/auth/token",
                        Collections.singletonMap("parameters", body));

        // 2. handleAuthleteApiCall() returns null if it already returned a response to the client
        if (authApiResponse == null) {
            return;
        }

        // 3. Perform the action
        String action = (String)authApiResponse.get("action");
        if (action.equals("OK")) {
            String responseContent = (String)authApiResponse.get("responseContent");
            OAuthUtils.setResponseBody(response, HttpServletResponse.SC_OK, responseContent);
            return;
        }

        // 4. We should never get here!
        Map<String, String> errorResponse = Map.of(
                "error", "unexpected_error",
                "error_description", "Contact the service owner for details"
        );
        OAuthUtils.setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper.writeValueAsString(errorResponse));
    }
}
