<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 19.08.2017
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.accessdenied"/>
        </fmt:bundle>
    </title>
    <jsp:include page="../scripts-and-css.jsp"/>
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
                    <fmt:message key="labels.errors.accessdenied.explained"/>
                </fmt:bundle>
            </h3>
        </div>
    </div>
</div>
</body>
</html>