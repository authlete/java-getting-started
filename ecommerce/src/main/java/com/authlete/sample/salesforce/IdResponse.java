
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
    "id",
    "asserted_user",
    "user_id",
    "organization_id",
    "username",
    "nick_name",
    "display_name",
    "email",
    "email_verified",
    "first_name",
    "last_name",
    "timezone",
    "photos",
    "addr_street",
    "addr_city",
    "addr_state",
    "addr_country",
    "addr_zip",
    "mobile_phone",
    "mobile_phone_verified",
    "is_lightning_login_user",
    "status",
    "urls",
    "active",
    "user_type",
    "language",
    "locale",
    "utcOffset",
    "last_modified_date",
    "is_app_installed"
})
@Generated("jsonschema2pojo")
public class IdResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("asserted_user")
    private Boolean assertedUser;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("organization_id")
    private String organizationId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("photos")
    private Photos photos;
    @JsonProperty("addr_street")
    private Object addrStreet;
    @JsonProperty("addr_city")
    private Object addrCity;
    @JsonProperty("addr_state")
    private Object addrState;
    @JsonProperty("addr_country")
    private String addrCountry;
    @JsonProperty("addr_zip")
    private Object addrZip;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    @JsonProperty("mobile_phone_verified")
    private Boolean mobilePhoneVerified;
    @JsonProperty("is_lightning_login_user")
    private Boolean isLightningLoginUser;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("user_type")
    private String userType;
    @JsonProperty("language")
    private String language;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("utcOffset")
    private Integer utcOffset;
    @JsonProperty("last_modified_date")
    private String lastModifiedDate;
    @JsonProperty("is_app_installed")
    private Boolean isAppInstalled;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("asserted_user")
    public Boolean getAssertedUser() {
        return assertedUser;
    }

    @JsonProperty("asserted_user")
    public void setAssertedUser(Boolean assertedUser) {
        this.assertedUser = assertedUser;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    @JsonProperty("organization_id")
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("nick_name")
    public String getNickName() {
        return nickName;
    }

    @JsonProperty("nick_name")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("email_verified")
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @JsonProperty("email_verified")
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("photos")
    public Photos getPhotos() {
        return photos;
    }

    @JsonProperty("photos")
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    @JsonProperty("addr_street")
    public Object getAddrStreet() {
        return addrStreet;
    }

    @JsonProperty("addr_street")
    public void setAddrStreet(Object addrStreet) {
        this.addrStreet = addrStreet;
    }

    @JsonProperty("addr_city")
    public Object getAddrCity() {
        return addrCity;
    }

    @JsonProperty("addr_city")
    public void setAddrCity(Object addrCity) {
        this.addrCity = addrCity;
    }

    @JsonProperty("addr_state")
    public Object getAddrState() {
        return addrState;
    }

    @JsonProperty("addr_state")
    public void setAddrState(Object addrState) {
        this.addrState = addrState;
    }

    @JsonProperty("addr_country")
    public String getAddrCountry() {
        return addrCountry;
    }

    @JsonProperty("addr_country")
    public void setAddrCountry(String addrCountry) {
        this.addrCountry = addrCountry;
    }

    @JsonProperty("addr_zip")
    public Object getAddrZip() {
        return addrZip;
    }

    @JsonProperty("addr_zip")
    public void setAddrZip(Object addrZip) {
        this.addrZip = addrZip;
    }

    @JsonProperty("mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    @JsonProperty("mobile_phone")
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @JsonProperty("mobile_phone_verified")
    public Boolean getMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    @JsonProperty("mobile_phone_verified")
    public void setMobilePhoneVerified(Boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    @JsonProperty("is_lightning_login_user")
    public Boolean getIsLightningLoginUser() {
        return isLightningLoginUser;
    }

    @JsonProperty("is_lightning_login_user")
    public void setIsLightningLoginUser(Boolean isLightningLoginUser) {
        this.isLightningLoginUser = isLightningLoginUser;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("urls")
    public Urls getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("user_type")
    public String getUserType() {
        return userType;
    }

    @JsonProperty("user_type")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("utcOffset")
    public Integer getUtcOffset() {
        return utcOffset;
    }

    @JsonProperty("utcOffset")
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    @JsonProperty("last_modified_date")
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    @JsonProperty("last_modified_date")
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonProperty("is_app_installed")
    public Boolean getIsAppInstalled() {
        return isAppInstalled;
    }

    @JsonProperty("is_app_installed")
    public void setIsAppInstalled(Boolean isAppInstalled) {
        this.isAppInstalled = isAppInstalled;
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
