<%@ page import="java.util.Map" %>
<%@ page import="com.authlete.sample.oauth.OAuthService" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="imageFolder" class="com.authlete.sample.ecommerce.ImageFolder" />
<jsp:useBean id="oauthUtils" class="com.authlete.sample.oauth.OAuthUtils" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>eCommerce Application</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/ecommerce.css"></head>
<body>
<div class="container">
    <h1>eCommerce Application</h1>
<%
    for (Map.Entry<String, OAuthService> service : oauthUtils.getOAuthServices(application).entrySet()) {
        String serviceName = service.getKey();
        String name = (String)session.getAttribute(serviceName);
        if (name != null) {
%>
            <p>Welcome, <%= name %></p>
            <p><a href="oauth?unlink=<%= serviceName %>">Unlink my <%= serviceName %> Account</a></p>
<%
        } else {
%>
            <p><a href="oauth?link=<%= serviceName %>">Link my <%= serviceName %> Account</a></p>
<%
        }
    }
%>
    <div class="row">
<%
    for (String imagePath : imageFolder.getImagePaths()) {
%>
        <div class="col">
            <img class="center-cropped" src="<%= imagePath %>" />
        </div>
<%
    }
%>
    </div>
</div>
<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>