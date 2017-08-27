<%@ page import="java.net.URI" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of songs</title>
    <script src="/scripts/jquery-3.2.1.js"></script>
    <jsp:include page="scripts-and-css.jsp"/>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="menu.jsp"/>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="list-of-songs">
                    <ul>
                        <c:forEach items="${requestScope.songsIDsAndTitles}" var="entry">
                            <li><a href="/controller?command=song&songID=${entry.key}"}><c:out value="${entry.value}"></c:out></a> </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
