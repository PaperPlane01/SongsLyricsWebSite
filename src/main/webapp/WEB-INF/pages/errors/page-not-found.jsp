<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.pagenotfound.general"/>
        </fmt:bundle>
    </title>
    <jsp:include page="/WEB-INF/pages/css.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/pages/menu.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <jsp:include page="/WEB-INF/pages/quick-acсess.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.errors.pagenotfound"/>
            </fmt:bundle>
        </div>
    </div>
</div>
<div id="messages" style="display: none">
    <jsp:include page="/WEB-INF/pages/messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="/WEB-INF/pages/scripts.jsp"/>
</body>
</html>
