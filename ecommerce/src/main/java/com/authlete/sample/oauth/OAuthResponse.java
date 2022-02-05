package com.authlete.sample.oauth;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class OAuthResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("signature")
  private String signature;
  @JsonProperty("scope")
  private String scope;
  @JsonProperty("id_token")
  private String idToken;
  @JsonProperty("instance_url")
  private String instanceUrl;
  @JsonProperty("id")
  private String id;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("issued_at")
  private String issuedAt;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("access_token")
  public String getAccessToken() {
    return accessToken;
  }

  @JsonProperty("access_token")
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @JsonProperty("refresh_token")
  public String getRefreshToken() {
    return refreshToken;
  }

  @JsonProperty("refresh_token")
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @JsonProperty("signature")
  public String getSignature() {
    return signature;
  }

  @JsonProperty("signature")
  public void setSignature(String signature) {
    this.signature = signature;
  }

  @JsonProperty("scope")
  public String getScope() {
    return scope;
  }

  @JsonProperty("scope")
  public void setScope(String scope) {
    this.scope = scope;
  }

  @JsonProperty("id_token")
  public String getIdToken() {
    return idToken;
  }

  @JsonProperty("id_token")
  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  @JsonProperty("instance_url")
  public String getInstanceUrl() {
    return instanceUrl;
  }

  @JsonProperty("instance_url")
  public void setInstanceUrl(String instanceUrl) {
    this.instanceUrl = instanceUrl;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("token_type")
  public String getTokenType() {
    return tokenType;
  }

  @JsonProperty("token_type")
  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  @JsonProperty("issued_at")
  public String getIssuedAt() {
    return issuedAt;
  }

  @JsonProperty("issued_at")
  public void setIssuedAt(String issuedAt) {
    this.issuedAt = issuedAt;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
