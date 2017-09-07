<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
