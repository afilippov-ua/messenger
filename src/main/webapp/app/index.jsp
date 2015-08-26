<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>messenger</title>
    <input type="hidden" id="userId" value="${userId}"/>
    <link href="resources/css/messenger.css" rel="stylesheet">
    <script src="/app/resources/scripts/messenger.js"></script>
    <script src="/app/resources/scripts/authentication.js"></script>
</head>

<body align="center">

<table about="headers" align="center">

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
            | <a href="javascript:profile()"> Profile </a>
            | <a href="javascript:contacts()"> Manage contacts </a>
            | <a href="javascript:logout()"> Logout </a>
        </c:if>

        <form action="/login" method="get" id="loginForm"></form>
        <form action="/register" method="get" id="registerForm"></form>
        <form action="/j_spring_security_logout" method="post" id="logoutForm"></form>
        <form action="/profile" method="get" id="profileForm"></form>
        <form action="/contacts" method="get" id="contactsForm"></form>
        <%------------------------------------------------------------------%>
    </tr>

</table>

<h2>messenger</h2>

<c:if test="${pageContext.request.userPrincipal.name == null}">
    <h3>Please log in or register for starting conversation...</h3>
</c:if>


<%------------------------- MESSENGER -------------------------%>
<c:if test="${pageContext.request.userPrincipal.name != null}">

    <br>

    <table class="main_table" align="center">

        <tr>

            <!-- CONTACTS -->
            <td width="100">
                <select id="contacts" class="contacts" size="20" onChange="javascript:loadMessages(true, true)">
                        <%-- contact list --%>
                </select>
            </td>

            <!-- MESSAGES -->
            <td valign="top">

                <table style="width: 100%; height: 100%">

                    <tr style="height: 80%">
                        <td>
                            <div id="messages" class="messages">
                                <!-- messages -->
                            </div>
                        </td>
                    </tr>

                    <tr style="height: 20%">
                        <td>
                            <textarea id="newMessage" class="input_textarea" placeholder="enter your message..."
                                      onkeypress="sendMessageOnEnter(event)"></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td align="right" style="font-family: sans-serif; font-size: small; color: darkgray">Send
                            message Ctrl+Enter
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

</html>
