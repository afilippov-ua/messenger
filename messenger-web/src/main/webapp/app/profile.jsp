<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Profile</title>
    <script src="/app/resources/scripts/authentication.js"></script>
</head>

<body align="center">

<table align="center">
    <tr>
        <%------------------- USER DATA / LOGIN / LOGOUT -------------------%>
        User : ${pageContext.request.userPrincipal.name}
        | <a href="javascript:index()"> Main page </a>
        | <a href="javascript:contacts()"> Manage contacts </a>
        | <a href="javascript:logout()"> Logout </a>

        <form action="/index" method="get" id="indexForm"></form>
        <form action="/contacts" method="get" id="contactsForm"></form>
        <form action="/j_spring_security_logout" method="post" id="logoutForm"></form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

<h2>Profile</h2>

<table align="center">

    <tr>
        <p><img src="http://cdn.shopoweb.com/1/themes/standard/img/no_image.jpg"></p>
    </tr>

    <tr>
        <table align="center">

            <tr>
                <td>
                    <label for="email">email</label>
                </td>
                <td>
                    <input name="email" type="email" value=${pageContext.request.userPrincipal.name}>
                </td>
            </tr>

        </table>
    </tr>

    <tr>
        <button type="button">Save</button>
    </tr>

</table>

</body>

</html>