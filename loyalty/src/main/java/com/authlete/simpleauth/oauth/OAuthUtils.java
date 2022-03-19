package com.authlete.simpleauth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class OAuthUtils {
    private static final String AUTHLETE_CREDENTIAL_JSON = "/WEB-INF/authleteCredential.json";
    private static final String AUTHLETE_BASE = "https://api.authlete.com/api";
    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private static AuthleteCredential getAuthleteCredential(ServletContext context) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = context.getResourceAsStream(AUTHLETE_CREDENTIAL_JSON);
        if (inputStream == null) {
            throw new FileNotFoundException(AUTHLETE_CREDENTIAL_JSON);
        }
        return mapper.readValue(inputStream, AuthleteCredential.class);
    }

    public static synchronized Client getClient(ServletContext context) throws IOException {
        Client client = (Client)context.getAttribute("authleteClient");
        if (client == null) {
            AuthleteCredential authleteCredential = getAuthleteCredential(context);
            client = ClientBuilder.newClient(new ClientConfig())
                    .register(HttpAuthenticationFeature.basic(authleteCredential.getApiKey(), authleteCredential.getApiSecret()));
            context.setAttribute("authleteClient", client);
        }

        return client;
    }

    static void setResponseBody(HttpServletResponse response, int statusCode, String responseContent) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.getWriter().print(responseContent);
    }

    private static void setAuthenticateHeader(HttpServletResponse response, int statusCode, String responseContent) {
        response.setStatus(statusCode);
        response.setHeader("WWW-Authenticate", responseContent);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
    }

    public static String prettyPrint(Map<String, Object> requestMap) {
        try {
            return mapper.writeValueAsString(requestMap);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static Map<String, Object> handleAuthleteApiCall(ServletContext context, HttpServletResponse response,
                                                            String api, Map<String, Object> requestMap) throws IOException {
        logger.debug("Calling API {} with params {}", api, OAuthUtils.prettyPrint(requestMap));

        Map<String, Object> responseMap = getClient(context).target(AUTHLETE_BASE + api)
                .request()
                .post(Entity.entity(requestMap, MediaType.APPLICATION_JSON_TYPE), new GenericType<>() {
                });

        logger.debug("Received API response {}", OAuthUtils.prettyPrint(responseMap));

        String action = (String)responseMap.get("action");
        String responseContent = (String)responseMap.get("responseContent");

        switch (action) {
            case "INTERNAL_SERVER_ERROR":
                setResponseBody(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, responseContent);
                return null;
            case "BAD_REQUEST":
            case "INVALID_CLIENT":
                setResponseBody(response, HttpServletResponse.SC_BAD_REQUEST, responseContent);
                return null;
            case "UNAUTHORIZED":
                setAuthenticateHeader(response, HttpServletResponse.SC_UNAUTHORIZED, responseContent);
                return null;
            case "FORBIDDEN":
                setAuthenticateHeader(response, HttpServletResponse.SC_FORBIDDEN, responseContent);
                return null;
            case "LOCATION":
                response.setStatus(HttpServletResponse.SC_FOUND);
                response.setHeader("Location", responseContent);
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Pragma", "no-cache");
                return null;
            default:
                break;
        }

        return responseMap;
    }
}
