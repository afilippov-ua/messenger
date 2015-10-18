<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Register</title>
    <script src="/app/resources/scripts/registration.js"></script>
    <script src="/app/resources/scripts/jquery-1.11.3.js"></script>
    <script src="/app/resources/scripts/authentication.js"></script>
</head>
<body align="center">

<table align="center">

    <tr>
        <a href="javascript:login()"> Login </a>
        <form action="/login" method="get" id="loginForm"></form>
    </tr>

    <tr>
        <div id="info"></div>
    </tr>

    <tr>
        <table align="center">

            <tr>
                <td>

                    <label for="username">email</label>
                </td>
                <td>
                    <input id="email" type="email" size="25" title="email"
                           placeholder="filippov@mail.com" value="filippov@mail.com" required>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="password">password</label>
                </td>
                <td>
                    <input id="password" type="password" size="25"
                           placeholder="password" value="12345" required>
                </td>
            </tr>

        </table>
    </tr>

    <tr>
        <button onclick="javascript:registration()">Confirm</button>
    </tr>

</table>

</body>
</html>
