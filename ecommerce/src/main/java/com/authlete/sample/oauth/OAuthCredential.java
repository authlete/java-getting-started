package com.authlete.sample.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OAuthCredential {
  @JsonProperty("service_name")
  private String serviceName;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("client_secret")
  private String clientSecret;

  @JsonProperty("service_name")
  public String getServiceName() {
    return serviceName;
  }

  @JsonProperty("service_name")
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
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
}
