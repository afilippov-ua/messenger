<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=" utf-8">
    <title>Register</title>
</head>
<body align="center">

<form name="register" method="POST" action="/register">

    <table align="center">

        <tr>
            <%------------ ERROR MESSAGES------------%>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <%---------------------------------------%>
        </tr>

        <tr>
            <table align="center">

                <tr>
                    <td>

                        <label for="username">email</label>
                    </td>
                    <td>
                        <input type="email" name="username" size="25" title="email"
                               placeholder="filippov@javamonkeys.com" value="filippov@javamonkeys.com" required>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="password">password</label>
                    </td>
                    <td>
                        <input type="password" name="password" size="25"
                               placeholder="password" value="12345" required>
                    </td>
                </tr>

            </table>
        </tr>

        <tr>
            <input type="submit" value="Confirm">
        </tr>

    </table>

</form>

</body>
</html>
