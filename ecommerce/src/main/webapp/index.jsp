<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <div class="col">
            <img class="center-cropped" src="images/photo-1507756354714-ae9c8f09ea69.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1512390225428-a9d51c817f94.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1517281053814-2e001df6182b.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1522770179533-24471fcdba45.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1526170286768-b3c80b34b036.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1541343832952-d26cc08e0dd7.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1542487640-2b2261b22f78.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1546687699-05723cfd2a9b.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1550332343-51082aa9c14c.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1550787756-d43bd9f8f8ba.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1563459190561-d60934c2e44f.jpeg" />
        </div>
        <div class="col">
            <img class="center-cropped" src="images/photo-1565622871630-8e453c4b6ed9.jpeg" />
        </div>
    </div>
</div>
<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>