<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Messenger</title>
    <input type="hidden" id="userId" value="${userId}"/>
    <link href="resources/css/message.css" rel="stylesheet">
</head>

<body align="center">

<table align="center">

    <tr>
        <%------------------- USER DATA / LOGIN / LOGOUT -------------------%>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            User : ${pageContext.request.userPrincipal.name}
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="javascript:login()"> Login </a> |
            <a href="javascript:register()"> Register </a>
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            | <a href="javascript:profile()"> Profile </a> |
            <a href="javascript:logout()"> Logout </a>
        </c:if>

        <c:url value="/j_spring_security_logout" var="logoutUrl"/>
        <c:url value="/profile" var="profileUrl"/>
        <c:url value="/login" var="loginUrl"/>
        <c:url value="/register" var="registerUrl"/>

        <form action="${loginUrl}" method="get" id="loginForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>

        <form action="${registerUrl}" method="get" id="registerForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>

        <form action="${logoutUrl}" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>

        <form action="${profileUrl}" method="get" id="profileForm">
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

<h2>Hello! Welcome to new great messenger of the world!</h2>

<c:if test="${pageContext.request.userPrincipal.name == null}">
    <h3>Please log in or register for starting conversation...</h3>
</c:if>


<%------------------------- MESSENGER -------------------------%>
<c:if test="${pageContext.request.userPrincipal.name != null}">

    <br>

    <table align="center">

        <tr>

            <td>
                <select id="contacts" multiple="multiple" style="height:100%" onChange="javascript:loadMessages()">
                    <%--contact list--%>
                </select>

            </td>

            <td>
                <table align="center">

                    <tr>
                        <td>
                            <textarea id="messages" cols="100" rows="15"></textarea>
                        </td>
                    </tr>

                    <tr align="center">
                        <td>

                            <input type="text" style="width: 100%" id="newMessage" onkeypress="sendMessageOnEnter(event)">
                            <label id="error" hidden="false">error</label>

                        </td>
                    </tr>

                </table>
            </td>
        </tr>

    </table>

    <br><label id="errorLabel"></label>

</c:if>
<%-------------------------------------------------------------%>


</body>

<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<script src="/app/resources/scripts/messenger.js"></script>
<script src="/app/resources/scripts/authenticatation.js"></script>

</html>
