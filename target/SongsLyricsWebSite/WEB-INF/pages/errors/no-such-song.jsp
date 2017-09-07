<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.nosuchsong"/>
        </fmt:bundle>
    </title>
    <jsp:include page="../css.jsp"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <jsp:include page="../menu.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="../quick-acсess.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h3>
                <fmt:bundle basename="labels">
                    <fmt:message key="labels.errors.nosuchsong.explained"/>
                </fmt:bundle>
            </h3>
        </div>
    </div>
</div>
<jsp:include page="../scripts.jsp"/>
</body>
</html>
