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

    public static String prettyPrint(Map<String, Object> requestMap) {
        try {
            return mapper.writeValueAsString(requestMap);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
