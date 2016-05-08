<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>msg: Login</title>
    <input type="hidden" id="app-path" value="${appPath}"/>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap/signin.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="resources/scripts/authentication.js"></script>
</head>

<body>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">Messenger</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/login">Login</a></li>
                <li><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>
<c:if test="${not empty msg}">
    <div class="alert alert-success" role="alert">${msg}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<form class="form-signin" accept-charset="UTF-8" action="/j_spring_security_check" method="post" role="form">
    <h3 class="form-signin-heading">Login</h3>
    <span name="error" class="alert-danger"></span>
    <div class="form-group">
        <input type="email" name="username" id="email" class="form-control" placeholder="email" required autofocus>
    </div>
    <span name="error" class="alert-danger"></span>
    <div class="form-group">
        <input type="password" name="password" id="password" class="form-control" placeholder="password" required>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="button">Login</button>
</form>


</body>
</html>
