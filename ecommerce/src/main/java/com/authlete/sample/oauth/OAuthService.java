package com.authlete.sample.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OAuthService {
  @JsonProperty("service_name")
  private String serviceName;

  @JsonProperty("auth_uri")
  private String authUri;

  @JsonProperty("token_uri")
  private String tokenUri;

  @JsonProperty("redirect_uri")
  private String redirectUri;

  @JsonProperty("query_params")
  private Map<String, String> queryParams;

  @JsonProperty("api_endpoint")
  private String apiEndpoint;

  // Read via OAuthCredential
  private String clientId;

  private String clientSecret;

  @JsonProperty("service_name")
  public String getServiceName() {
    return serviceName;
  }

  @JsonProperty("service_name")
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

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

  @JsonProperty("api_endpoint")
  public String getApiEndpoint() {
    return apiEndpoint;
  }

  @JsonProperty("api_endpoint")
  public void setApiEndpoint(String apiEndpoint) {
    this.apiEndpoint = apiEndpoint;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public void setCredential(OAuthCredential credential) {
    this.clientId = credential.getClientId();
    this.clientSecret = credential.getClientSecret();
  }
}
