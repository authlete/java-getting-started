<%@ page import="java.util.Map" %>
<%@ page import="com.authlete.sample.oauth.OAuthService" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="OAuthUtils" class="com.authlete.sample.oauth.OAuthUtils" />
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
    for (Map.Entry<String, OAuthService> service : OAuthUtils.getOAuthServices(application).entrySet()) {
        String serviceName = service.getKey();
        String message = OAuthUtils.renderServiceText(request, serviceName);
        if (message != null) {
%>
            <p><%= message %></p>
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
    for (int i = 0; i < 12; i++) {
        String photoName = "images/photo" + String.format("%02d", i + 1) + ".jpeg";
%>
        <div class="col">
            <img class="center-cropped" src="<%= photoName %>" />
        </div>
<%
    }
%>
    </div>
</div>
<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>