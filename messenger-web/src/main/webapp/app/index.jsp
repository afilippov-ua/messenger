<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>msg: Main</title>
    <input type="hidden" id="user-id" value="${userId}"/>
    <input type="hidden" id="app-path" value="${appPath}"/>
    <link href="resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="resources/scripts/jquery-1.11.3.js"></script>
    <script src="resources/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="resources/scripts/settings.js"></script>
    <script src="resources/scripts/authentication.js"></script>
    <script src="resources/scripts/messenger.js"></script>
    <script src="resources/scripts/contacts.js"></script>
    <script src="resources/scripts/restContacts.js"></script>
    <script src="resources/scripts/restMessages.js"></script>
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
                <li><a href="javascript:logout()">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">

    <div class="row">
        <div class="col-xs-3">
            <div class="panel-messenger panel-primary">
                <div class="panel-heading">Contacts</div>
                <div class="panel-body panel-contacts" style="overflow-y: scroll">
                    <ul id="contacts" class="nav nav-pills nav-stacked">
                        <%--contacts--%>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-xs-9">
            <div class="panel-messenger panel-primary">
                <div class="panel-heading">Messages</div>
                <div id="messages" class="panel-body panel-contacts" style="overflow-y: scroll">
                            <%--messages--%>
                </div>
                <div class="panel-footer">
                    <div class="col-lg-pull-0">
                        <div class="input-group input-group-lg">
                            <input id="message-text" type="text" maxlength="255" class="form-control">
                            <span class="input-group-btn">
                                <button class="btn btn-default" onclick="sendMessage()" type="button">Send</button>
                            </span>
                        </div>
                        <div id="warningInfo" class="alert alert-warning" hidden role="alert">sending error... please,
                            try again later...
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>