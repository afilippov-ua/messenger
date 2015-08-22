<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>messenger</title>
    <input type="hidden" id="userId" value="${userId}"/>
    <link href="resources/css/messenger.css" rel="stylesheet">
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
            | <a href="javascript:profile()"> Profile </a> |
            <a href="javascript:logout()"> Logout </a>
        </c:if>

        <c:url value="/j_spring_security_logout" var="logoutUrl"/>
        <c:url value="/profile" var="profileUrl"/>
        <c:url value="/login" var="loginUrl"/>
        <c:url value="/register" var="registerUrl"/>

        <form action="${loginUrl}" method="get" id="loginForm"></form>

        <form action="${registerUrl}" method="get" id="registerForm"></form>

        <form action="${logoutUrl}" method="post" id="logoutForm"></form>

        <form action="${profileUrl}" method="get" id="profileForm"></form>
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

    <!-- TABLE -->
    <table class="main_table" align="center">

        <tr>

            <!-- CONTACTS -->
            <td width="100">

                <select id="contacts" class="contacts" size="20" onChange="javascript:loadMessages(true, true)">
                    <%-- Contact list --%>
                </select>

            </td>

            <!-- MESSAGES -->
            <td valign="top">

                <table style="width: 100%; height: 100%">

                    <tr style="height: 80%">

                        <td>

                            <div id="messages" class="messages">
                                <!-- messages -->

                                <%--<div align="center">--%>
                                    <%--<div class="date" align="center">--%>
                                        <%--2015.09.10--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                                <%--<div id="message1" class="message_wrapper_interlocutor">--%>
                                <%--<div class="message_wrapper">--%>
                                        <%--<div>--%>
                                            <%--<span class="time">....................</span>--%>
                                            <%--<span class="author">author </span>--%>
                                        <%--</div>--%>
                                        <%--<span class="message_text">.................</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                                <%--<div id="message2" class="message_wrapper_me">--%>
                                    <%--<div>--%>
                                        <%--<div>--%>
                                            <%--<span class="time">......................../span>--%>
                                            <%--<span class="author">Me: </span>--%>
                                        <%--</div>--%>
                                        <%--<span class="message_text">..........................</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                            </div>

                        </td>

                    </tr>

                    <tr style="height: 20%">
                        <td>
                            <textarea id="newMessage" class="input_textarea" placeholder="enter your message..." onkeypress="sendMessageOnEnter(event)"></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td align="right" style="font-family: sans-serif; font-size: small; color: darkgray">Send message Ctrl+Enter</td>
                    </tr>

                </table>

            </td>

        </tr>

    </table>

    <br><label id="errorLabel"></label>

</c:if>
<%-------------------------------------------------------------%>

</body>

<script src="/app/resources/scripts/messenger.js"></script>
<script src="/app/resources/scripts/authentication.js"></script>

</html>
