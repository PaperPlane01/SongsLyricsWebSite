<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.failedtoloaddata.general"/>
        </fmt:bundle>
    </title>
    <jsp:include page="../css.jsp"/>
</head>
<body>
<div class="row">
    <div class="col-xs-12">
        <jsp:include page="../quick-acсess.jsp"/>
    </div>
</div>
<div class="row">
    <div id="message">
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.failedtoloaddata"/>
        </fmt:bundle>
    </div>
</div>
<div id="messages" style="display: none">
    <jsp:include page="../messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="../scripts.jsp"/>
</body>
</html>
