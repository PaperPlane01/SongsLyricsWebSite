<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 20.08.2017
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.registrationfailed"/>
        </fmt:bundle>
    </title>
</head>
<body>
<h3>
    <fmt:bundle basename="labels">
        <fmt:message key="labels.registrationfailed"/>
    </fmt:bundle>
</h3>

<p>
    <fmt:bundle basename="labels">
        <fmt:message key="labels.suchuseralreadyexists"/>
    </fmt:bundle>
</p>
</body>
</html>
