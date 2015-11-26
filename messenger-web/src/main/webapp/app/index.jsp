<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>msg: Main</title>
    <input type="hidden" id="userId" value="${userId}"/>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="resources/scripts/authentication.js"></script>
    <script src="resources/scripts/messenger.js"></script>
    <script src="resources/scripts/contacts.js"></script>
</head>
<body>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">Messenger</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Main page</a></li>
                <li><a href="/contacts">Contacts</a></li>
                <li><a href="/profile">Profile</a></li>
                <li><a href="logout()">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="panel-messenger panel-default">

    <div class="panel-body">

        <table align="center" class="table">
            <tr>
                <td width="300">
                    <div class="panel-messenger panel-primary">
                        <div class="panel-heading">Contacts</div>
                        <div class="panel-body panel-contacts">
                            <ul id="contacts" class="nav nav-pills nav-stacked" style="max-width: 300px;">
                                <%--contacts--%>
                            </ul>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="panel-messenger panel-primary">
                        <div class="panel-heading">Messages</div>
                        <div id="messages" class="panel-body panel-contacts">
                            <%--messages--%>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <div class="col-lg-pull-0">
                        <div class="input-group input-group-lg">
                            <input id="messageText" type="text" onkeypress="sendMessageOnEnter(event)" class="form-control">
                            <span class="input-group-btn">
                                <button class="btn btn-default" onclick="sendMessage()" type="button">Send</button>
                            </span>
                        </div>
                        <div id="warningInfo" class="alert alert-warning" hidden role="alert">senging error... please, try again later...</div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>