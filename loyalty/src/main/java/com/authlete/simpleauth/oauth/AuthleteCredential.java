package com.authlete.simpleauth.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthleteCredential {
  @JsonProperty("api_key")
  private String apiKey;

  @JsonProperty("api_secret")
  private String apiSecret;

  @JsonProperty("api_key")
  public String getApiKey() {
    return apiKey;
  }

  @JsonProperty("api_key")
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @JsonProperty("api_secret")
  public String getApiSecret() {
    return apiSecret;
  }

  @JsonProperty("api_secret")
  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }
}
