package com.authlete.sample.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OAuthService {
  @JsonProperty("auth_uri")
  private String authUri;

  @JsonProperty("token_uri")
  private String tokenUri;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("client_secret")
  private String clientSecret;

  @JsonProperty("redirect_uri")
  private String redirectUri;

  @JsonProperty("query_params")
  private Map<String, String> queryParams;

  @JsonProperty("auth_uri")
  public String getAuthUri() {
    return authUri;
  }

  @JsonProperty("auth_uri")
  public void setAuthUri(String authUri) {
    this.authUri = authUri;
  }

  @JsonProperty("token_uri")
  public String getTokenUri() {
    return tokenUri;
  }

  @JsonProperty("token_uri")
  public void setTokenUri(String tokenUri) {
    this.tokenUri = tokenUri;
  }

  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("client_id")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @JsonProperty("client_secret")
  public String getClientSecret() {
    return clientSecret;
  }

  @JsonProperty("client_secret")
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  @JsonProperty("redirect_uri")
  public String getRedirectUri() {
    return redirectUri;
  }

  @JsonProperty("redirect_uri")
  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  @JsonProperty("query_params")
  public Map <String, String> getQueryParams() {
    return queryParams;
  }

  @JsonProperty("query_params")
  public void setQueryParams(Map <String, String> queryParams) {
    this.queryParams = queryParams;
  }
}
