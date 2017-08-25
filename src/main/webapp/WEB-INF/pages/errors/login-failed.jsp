<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 15.08.2017
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:bundle basename="labels">
        <fmt:message key="labels.loginfailed"/>
    </fmt:bundle></title>
</head>
<body>
<label>
    <fmt:bundle basename="labels">
        <fmt:message key="labels.loginfailed"/>
        <br>
        <fmt:message key="labels.reason"/>
        <fmt:message key="${requestScope.reason}"/>
    </fmt:bundle>
</label>
</body>
</html>
