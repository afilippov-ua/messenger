<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>manage contacts</title>
    <input type="hidden" id="userId" value="${userId}"/>
    <script src="/app/resources/scripts/authentication.js"></script>
    <script src="/app/resources/scripts/contactManager.js"></script>
    <script src="/app/resources/scripts/jquery-1.11.3.js"></script>
    <link href="/app/resources/css/messenger.css" rel="stylesheet">
</head>
<body align="center">

<table about="headers" align="center">

    <tr>
        <%------------------- USER DATA / LOGIN / LOGOUT -------------------%>
        User : ${pageContext.request.userPrincipal.name}
        | <a href="javascript:index()"> Main page </a>
        | <a href="javascript:profile()"> Profile </a>
        | <a href="javascript:contacts()"> Manage contacts </a>
        | <a href="javascript:logout()"> Logout </a>

        <form action="/index" method="get" id="indexForm"></form>
        <form action="/contacts" method="get" id="contactsForm"></form>
        <form action="/profile" method="get" id="profileForm"></form>
        <form action="/j_spring_security_logout" method="post" id="logoutForm"></form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

<h2>Manage contacts</h2>

<table  align="center">

    <tr>
        <td align="center">
            <button onclick="javascript:deleteContact()">delete contact</button>
        </td>
            <td align="center">
            <button onclick="javascript:addContact()">add contact</button>
        </td>
    </tr>

    <tr>
        <!-- CONTACTS -->
        <td width="100">
            <select id="contacts" size="20" onChange="javascript:loadMessages(true, true)">
                <%-- contact list --%>
            </select>
        </td>

        <td width="400" valign="top">
            <table style="width: 100%">

                <tr>
                    <td>
                        email: <input id="email" placeholder="email" style="width: 300px; margin-bottom: 3px"><br>
                        name: <input id="name" placeholder="contact name" style="width: 300px">
                    </td>
                </tr>

            </table>
        </td>

    </tr>

</table>

</body>

</html>
