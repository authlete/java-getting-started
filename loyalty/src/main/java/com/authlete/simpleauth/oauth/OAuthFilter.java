package com.authlete.simpleauth.oauth;

import com.authlete.simpleauth.UserRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@WebFilter(filterName="oauthFilter")
public class OAuthFilter implements Filter  {
    private static final String BEARER_SPACE = "Bearer ";
    private static final Logger logger = LogManager.getLogger();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        // 1. Is there an Authorization header with an access token?
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER_SPACE)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Extract the bearer token from the header value and send it to the introspection endpoint.
        String token = authHeader.substring(BEARER_SPACE.length());
        logger.info("Found access token {} - validating it with Authlete", token);
        Map<String, Object> authApiResponse = OAuthUtils.handleAuthleteApiCall(req.getServletContext(), response,
                "/auth/introspection", Collections.singletonMap("token", token));

        if (authApiResponse == null) {
            // 3. There was an error calling the Authlete API. OAuthUtils.handleAuthleteApiCall has handled it.
            // Nothing more to do here.
            logger.error("Disallowing API request with access token {}", token);
            return;
        }

        // 4. Attach the username to the request and pass the request down the chain
        String action = (String)authApiResponse.get("action");
        if (action.equals("OK")) {
            String username = (String) authApiResponse.get("subject");
            logger.info("Allowing API request to {} for user {}", request.getRequestURI() , username);
            chain.doFilter(new UserRequestWrapper(username, request), response);
            return;
        }

        // 5. We should never get here!
        Map<String, String> errorResponse = Map.of(
                "error", "unexpected_error",
                "error_description", "Contact the service owner for details"
        );
        OAuthUtils.setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper.writeValueAsString(errorResponse));
    }

    @Override
    public void destroy() {
    }
}
