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
        <li><a href="/">Main page</a></li>
        <li class="active"><a href="/contacts">Contacts</a></li>
        <li><a href="/profile">Profile</a></li>
        <li><a href="javascript:logout()">Logout</a></li>
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
                <%-- User contacts --%>
              </ul>
            </div>
          </div>
        </td>

        <td>
          <div class="panel panel-primary">
            <div class="panel-heading">Search</div>
            <div class="panel-body">

              <%-- Find --%>
              <div class="row">
                <div class="col-lg-6">
                  <div class="input-group">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button" onclick="getContactsByName()">find by name:</button>
                    </span>
                    <input id="find-name" type="text" class="form-control" placeholder="name" onkeypress="getContactsByNameEvent(event)">
                  </div>
                </div>
              </div>
              <br>
              <div class="row">
                <div class="col-lg-6">
                  <div class="input-group">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button" onclick="getContactsByEmail()">find by email:</button>
                    </span>
                    <input id="find-email" type="text" class="form-control" placeholder="email" onkeypress="getContactsByEmailEvent(event)">
                  </div>
                </div>
              </div>

            </div>

            <%-- Table --%>
            <table id="table-contacts" class="table table-bordered tab" onclick="tableOnClick()">
              <thead>
              <tr>
                <th>id</th>
                <th>name</th>
                <th>email</th>
                <th></th>
              </tr>
              </thead>
              <tbody id="table-contacts-body">
                <%-- Found contacts--%>
              </tbody>
            </table>

          </div>
        </td>
      </tr>
    </table>
  </div>
</div>