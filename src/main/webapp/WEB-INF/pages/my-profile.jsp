<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 16.08.2017
  Time: 1:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.welcome"/>
        </fmt:bundle> ${sessionScope.user.getUsername()}
    </title>
   <jsp:include page="scripts-and-css.jsp"/>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="coll-sm-6 col-md-4 col-lg-3">
            <jsp:include page="menu.jsp"/>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <jsp:include page="quick-acсess.jsp"/>
            </div>
        </div>
        <div class="col-sm-6 col-md-8 col-lg-9">
            <h3>
                <fmt:bundle basename="labels">
                    <fmt:message key="labels.welcome"/>
                </fmt:bundle> ${sessionScope.user.getUsername()}
            </h3>
            <h3>${sessionScope.user.getUserType()}</h3>
        </div>
    </div>
</div>
</body>
</html>
