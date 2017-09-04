<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.profileOwner.getUsername()}</title>
    <jsp:include page="css.jsp"/>
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="quick-acсess.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <h3>
                This profile belongs to ${requestScope.profileOwner.getUsername()}
                Who is ${requestScope.profileOwner.getUserType()}
            </h3>
        </div>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
