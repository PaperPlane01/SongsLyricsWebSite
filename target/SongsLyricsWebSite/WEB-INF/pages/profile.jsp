<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.profileOwner.getUsername()}</title>
    <jsp:include page="scripts-and-css.jsp"/>
</head>
<body>
<div class="row">
    <div class="coll-sm-6 col-md-4 col-lg-3">
        <jsp:include page="menu.jsp"/>
    </div>
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
</body>
</html>
