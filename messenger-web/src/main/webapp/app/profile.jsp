<html>
<head>
    <meta charset="utf-8">
    <title>msg: Profile</title>
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
                <li><a href="/">Main page</a></li>
                <li><a href="/contacts">Contacts</a></li>
                <li class="active"><a href="/profile">Profile</a></li>
                <li><a href="javascript:logout()">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <form class="form-signin" action="" method="post" role="form">
        <h3 class="form-signin-heading">Profile</h3>

        <table class="table">
            <tr>
                <td>
                    <p><img src="resources/no_image.jpg"></p>
                </td>
            </tr>
            <tr>
                <td>
                    email:
                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="email" required
                           autofocus>
                </td>
            </tr>
            <tr>
                <td>
                    password:
                    <input type="password" name="password" id="inputPassword" class="form-control"
                           placeholder="password"
                           required>
                </td>
            </tr>
            <tr>
                <td>
                    username:
                    <input type="text" name="username" id="inputUsername" class="form-control" placeholder="username"
                           required>
                </td>
            </tr>
        </table>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>
    </form>
</div>
</body>
</html>
