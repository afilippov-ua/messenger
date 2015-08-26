<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Login</title>
</head>

<body align="center">

<table align="center">

    <tr>
        <%------------------- USER DATA / LOGIN / LOGOUT -------------------%>
        <a href="javascript:index()"> Main page </a> |
        <a href="javascript:register()"> Register </a>

        <form action="/index" method="get" id="indexForm"></form>
        <form action="/register" method="get" id="registerForm"></form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

<h2> messenger </h2>

<form name="loginForm" action="/j_spring_security_check" method="post">

    <table align="center">

        <tr>
            <%------------ ERROR MESSAGES------------%>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg">${msg}</div>
            </c:if>
            <%---------------------------------------%>
        </tr>

        <tr>
            <table align="center">
                <tr>
                    <td class="str1">

                        <label for="username">email</label>
                    </td>
                    <td>
                        <input type="email" name="username" size="25" title="Enter your email"
                               placeholder="filippov@javamonkeys.com" value="filippov@javamonkeys.com" required>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">password</label>
                    </td>
                    <td>
                        <input type="password" name="password" size="25" title="Enter your password"
                               placeholder="password" value="12345" required>
                    </td>
                </tr>


            </table>
        </tr>

        <tr>
            <button type="submit" name="submit" value="submit"> Login</button>
        </tr>

    </table>

</form>

</body>

<script src="/app/resources/scripts/authentication.js"></script>

</html>
