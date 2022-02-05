<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="imageFolder" class="com.authlete.sample.ecommerce.ImageFolder" />
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
    <c:choose>
        <c:when test="${sessionScope.salesforceId != null}">
            <p>Welcome, ${sessionScope.salesforceId.displayName}</p>
            <p><a href="oauth?unlink=salesforce">Unlink my Salesforce Account</a></p>
        </c:when>
        <c:otherwise>
            <p><a href="oauth?link=salesforce">Link my Salesforce Account (test)</a></p>
        </c:otherwise>
    </c:choose>
    <p><a href="#">Link my Loyalty Account</a></p>
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