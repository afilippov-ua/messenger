<html>
<head>
    <meta charset="utf-8">
    <title>msg: Profile</title>
    <input type="hidden" id="user-id" value="${userId}"/>
    <input type="hidden" id="app-path" value="${appPath}"/>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap/signin.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="resources/scripts/settings.js"></script>
    <script src="resources/scripts/authentication.js"></script>
    <script src="resources/scripts/profile.js"></script>
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
                <li><a href="/">Main page</a></li>
                <li><a href="/contacts">Contacts</a></li>
                <li class="active"><a href="/profile">Profile</a></li>
                <li><a href="javascript:logout()">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container form-signin">
    <div class="panel-group">
        <h3 class="form-signin-heading">Profile</h3>

        <div class="panel-body">
            <div class="panel-group">
                <span name="error" class="alert-danger"></span>
                <input id="input-file" style="display:none" type="file">
                <img class="img-thumbnail container-fluid" src="/app/resources/images/avatars/${userId}_original.jpg">
            </div>
            <span name="error" class="alert-danger"></span>
            <div class="panel-group">
                <label for="email" class="control-label">email:</label>
                <input id="email" type="email" class="form-control" placeholder="email" required autofocus>
            </div>
            <span name="error" class="alert-danger"></span>
            <div class="panel-group">
                <label for="new-password" class="control-label">new password:</label>
                <input id="new-password" type="password" class="form-control" placeholder="new password" required>
            </div>
            <span name="error" class="alert-danger"></span>
            <div class="panel-group">
                <label for="username" class="control-label">username:</label>
                <input id="username" type="text" class="form-control" placeholder="username" required>
            </div>
            <div class="panel-group">
                <button class="btn btn-lg btn-primary btn-block" type="button">Save changes</button>
                <div id="panel-info" class="alert alert-success" hidden role="alert"></div>
            </div>
        </div>

    </div>
</div>

</body>
</html>
