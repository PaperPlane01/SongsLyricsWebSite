<%@ page import="java.net.URI" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of songs</title>
    <jsp:include page="css.jsp"/>
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="list-of-songs">
                <ul>
                    <c:forEach items="${requestScope.songsIDsAndTitles}" var="entry">
                        <li><a href="/controller?command=song&songID=${entry.key}"}><c:out value="${entry.value}"/></a> </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="messages" style="display: none">
    <jsp:include page="messages/sign-up-validation-messages.jsp"/>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
