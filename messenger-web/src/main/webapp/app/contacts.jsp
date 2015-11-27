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
  <script src="resources/scripts/settings.js"></script>
  <script src="resources/scripts/restContacts.js"></script>
  <script src="resources/scripts/restUsers.js"></script>
</head>
<body>

<%-- Modal: Enter new contact name --%>
<div id="modal-enter-name" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Enter contact name</h4>
      </div>
      <div class="modal-body">
        <div class="input-group-lg">
          <label for="contact-new-name" class="control-label">Name:</label>
          <input id="contact-new-name" type="text" class="form-control" placeholder="enter contact name...">
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button id="modal-btn-save-contact" type="button" class="btn btn-primary">Save</button>
      </div>
    </div>
  </div>
</div>

<%-- Modal: Confirm --%>
<div id="modal-confirm" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Confirm</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete the selected contact?&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="modal-btn-confirm" type="button" class="btn btn-primary">OK</button>
            </div>
        </div>
    </div>
</div>

<%-- MENU --%>
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

<%-- BODY --%>
<div class="panel-messenger panel-default">
  <div class="panel-body">
    <table align="center" class="table">
      <tr>
        <td width="300">
          <div class="panel-messenger panel-primary">
            <div class="panel-heading">Your contacts</div>
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
                      <button class="btn btn-default" type="button" onclick="findContactsByNameOrEmail()">Find:</button>
                    </span>
                    <input id="find-contact-text" type="text" class="form-control" placeholder="name or email" onkeypress="findContactsByNameOrEmailOnEnter(event)">
                  </div>
                </div>
              </div>
            </div>

            <%-- Table --%>
            <table id="table-contacts" class="table table-bordered tab">
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
      <tr>
        <td>
          <div class="btn-group btn-group-justified" role="group" aria-label="...">
            <div class="btn-group" role="group">
              <button type="button" class="btn btn-default" onclick="editContactOnClick()">edit</button>
            </div>
            <div class="btn-group" role="group">
              <button type="button" class="btn btn-default" onclick="deleteContactOnClick()">delete</button>
            </div>
          </div>
        </td>
        <td></td>
      </tr>
    </table>
  </div>
</div>