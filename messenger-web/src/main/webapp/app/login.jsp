<html>
<head>
    <meta charset="utf-8">
    <title>msg: Login</title>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap/signin.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
</head>

<body>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">Messenger</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Login</a></li>
                <li><a href="http://localhost:8555/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <form class="form-signin" action="/j_spring_security_check" method="post" role="form">
        <h3 class="form-signin-heading">Login</h3>
        <input type="email" name="username" id="inputEmail" class="form-control" placeholder="email" required autofocus>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>

</div> <!-- /container -->

</body>
</html>
