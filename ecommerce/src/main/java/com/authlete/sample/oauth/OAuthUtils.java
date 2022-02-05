package com.authlete.sample.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class OAuthUtils {
  private static final Object lock = new Object();
  private static volatile Map<String, OAuthService> oauthServices;

  public static Map<String, OAuthService> getOAuthServices(ServletContext context) throws IOException {
    // Double check synchronization. See https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
    Map<String, OAuthService> localRef = oauthServices;
    if (localRef == null) {
      synchronized (lock) {
        localRef = oauthServices;
        if (localRef == null) {
          InputStream inputStream = context.getResourceAsStream("/WEB-INF/oauthServices.json");
          ObjectMapper mapper = new ObjectMapper();
          // Use a LinkedHashMap so the entries stay in the order that they are read
          oauthServices = localRef = mapper.readValue(inputStream, new TypeReference<LinkedHashMap<String, OAuthService>>() {});
        }
      }
    }
    return localRef;
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
