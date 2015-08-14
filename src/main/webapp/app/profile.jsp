<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Profile</title>
</head>

<body align="center">

<table align="center">
    <tr>
        <%------------------- USER DATA / LOGIN / LOGOUT -------------------%>
        User : ${pageContext.request.userPrincipal.name} |
        <a href="javascript:index()"> Main page </a> |
        <a href="javascript:logout()"> Logout </a>

        <c:url value="/index" var="indexUrl"/>
        <c:url value="/j_spring_security_logout" var="logoutUrl"/>

        <form action="${indexUrl}" method="get" id="indexForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>

        <form action="${logoutUrl}" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

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

<script src="/app/resources/scripts/authenticatation.js"></script>

</html>