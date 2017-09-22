<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.welcome"/>
        </fmt:bundle> ${sessionScope.user.getUsername()}
    </title>
   <jsp:include page="css.jsp"/>
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="quick-acсess.jsp"/>
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
<div id="messages" style="display: none">
    <jsp:include page="messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
