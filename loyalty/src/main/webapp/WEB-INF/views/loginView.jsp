<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/loyalty.css">
</head>
<body>

<div class="container">
    <h1>Login</h1>

    <p style="color: red;">${errorString}</p>

    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/login">
        <h1 class="h3 mb-3 font-weight-normal">Please login</h1>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required="" autofocus="" value="${user.userName}">
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="" value="${user.password}">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>

    <h2>Login with:</h2>
    <ul>
        <li>tatsuo/password</li>
        <li>pat/password</li>
    </ul>
</div>
</body>
</html>