<html>
<head>
    <meta charset="utf-8">
    <title>msg: Registration</title>
    <input type="hidden" id="app-path" value="${appPath}"/>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap/signin.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="resources/scripts/settings.js"></script>
    <script src="resources/scripts/authentication.js"></script>
    <script src="resources/scripts/restUsers.js"></script>
</head>

<body>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">Messenger</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/login">Login</a></li>
                <li class="active"><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<div id="panel-info" class="alert alert-success" hidden role="alert"></div>

<div class="form-signin">
    <h3 class="form-signin-heading">Registration</h3>
    <span name="error" class="alert-danger"></span>
    <div class="form-group">
        <input type="email" id="email" class="form-control" placeholder="email" required autofocus>
    </div>
    <span name="error" class="alert-danger"></span>
    <div class="form-group">
        <input type="password" id="password" class="form-control" placeholder="password" required>
    </div>
    <span name="error" class="alert-danger"></span>
    <div class="form-group">
        <input type="password" id="password-verify" class="form-control" placeholder="verify password" required>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="button">Register</button>
</div>

</body>
</html>
