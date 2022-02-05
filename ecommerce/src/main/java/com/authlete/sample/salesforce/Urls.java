
package com.authlete.sample.salesforce;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "enterprise",
    "metadata",
    "partner",
    "rest",
    "sobjects",
    "search",
    "query",
    "recent",
    "tooling_soap",
    "tooling_rest",
    "profile",
    "feeds",
    "groups",
    "users",
    "feed_items",
    "feed_elements",
    "custom_domain"
})
@Generated("jsonschema2pojo")
public class Urls {

    @JsonProperty("enterprise")
    private String enterprise;
    @JsonProperty("metadata")
    private String metadata;
    @JsonProperty("partner")
    private String partner;
    @JsonProperty("rest")
    private String rest;
    @JsonProperty("sobjects")
    private String sobjects;
    @JsonProperty("search")
    private String search;
    @JsonProperty("query")
    private String query;
    @JsonProperty("recent")
    private String recent;
    @JsonProperty("tooling_soap")
    private String toolingSoap;
    @JsonProperty("tooling_rest")
    private String toolingRest;
    @JsonProperty("profile")
    private String profile;
    @JsonProperty("feeds")
    private String feeds;
    @JsonProperty("groups")
    private String groups;
    @JsonProperty("users")
    private String users;
    @JsonProperty("feed_items")
    private String feedItems;
    @JsonProperty("feed_elements")
    private String feedElements;
    @JsonProperty("custom_domain")
    private String customDomain;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("enterprise")
    public String getEnterprise() {
        return enterprise;
    }

    @JsonProperty("enterprise")
    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    @JsonProperty("metadata")
    public String getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("partner")
    public String getPartner() {
        return partner;
    }

    @JsonProperty("partner")
    public void setPartner(String partner) {
        this.partner = partner;
    }

    @JsonProperty("rest")
    public String getRest() {
        return rest;
    }

    @JsonProperty("rest")
    public void setRest(String rest) {
        this.rest = rest;
    }

    @JsonProperty("sobjects")
    public String getSobjects() {
        return sobjects;
    }

    @JsonProperty("sobjects")
    public void setSobjects(String sobjects) {
        this.sobjects = sobjects;
    }

    @JsonProperty("search")
    public String getSearch() {
        return search;
    }

    @JsonProperty("search")
    public void setSearch(String search) {
        this.search = search;
    }

    @JsonProperty("query")
    public String getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(String query) {
        this.query = query;
    }

    @JsonProperty("recent")
    public String getRecent() {
        return recent;
    }

    @JsonProperty("recent")
    public void setRecent(String recent) {
        this.recent = recent;
    }

    @JsonProperty("tooling_soap")
    public String getToolingSoap() {
        return toolingSoap;
    }

    @JsonProperty("tooling_soap")
    public void setToolingSoap(String toolingSoap) {
        this.toolingSoap = toolingSoap;
    }

    @JsonProperty("tooling_rest")
    public String getToolingRest() {
        return toolingRest;
    }

    @JsonProperty("tooling_rest")
    public void setToolingRest(String toolingRest) {
        this.toolingRest = toolingRest;
    }

    @JsonProperty("profile")
    public String getProfile() {
        return profile;
    }

    @JsonProperty("profile")
    public void setProfile(String profile) {
        this.profile = profile;
    }

    @JsonProperty("feeds")
    public String getFeeds() {
        return feeds;
    }

    @JsonProperty("feeds")
    public void setFeeds(String feeds) {
        this.feeds = feeds;
    }

    @JsonProperty("groups")
    public String getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(String groups) {
        this.groups = groups;
    }

    @JsonProperty("users")
    public String getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(String users) {
        this.users = users;
    }

    @JsonProperty("feed_items")
    public String getFeedItems() {
        return feedItems;
    }

    @JsonProperty("feed_items")
    public void setFeedItems(String feedItems) {
        this.feedItems = feedItems;
    }

    @JsonProperty("feed_elements")
    public String getFeedElements() {
        return feedElements;
    }

    @JsonProperty("feed_elements")
    public void setFeedElements(String feedElements) {
        this.feedElements = feedElements;
    }

    @JsonProperty("custom_domain")
    public String getCustomDomain() {
        return customDomain;
    }

    @JsonProperty("custom_domain")
    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
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
