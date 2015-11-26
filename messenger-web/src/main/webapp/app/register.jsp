<html>
<head>
    <meta charset="utf-8">
    <title>msg: Registration</title>
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
                <li><a href="http://localhost:8555/login">Login</a></li>
                <li class="active"><a href="#about">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<div id="successInfo" class="alert alert-success" hidden role="alert"></div>
<div id="warningInfo" class="alert alert-warning" hidden role="alert"></div>

<div class="container">
    <form class="form-signin" role="form">
        <h3 class="form-signin-heading">Registration</h3>
        <input type="email" id="email" class="form-control" placeholder="email" required autofocus>
        <input type="password" id="password" class="form-control" placeholder="password" required>
        <button class="btn btn-lg btn-primary btn-block"type="button" onclick="registration()">Register</button>
    </form>
</div>

</body>
</html>
