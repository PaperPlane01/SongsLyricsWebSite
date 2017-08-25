<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 10.07.2017
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:bundle basename="labels"/>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
  <head>
    <title>Songs Lyrics Website</title>
    <title>${requestScope.songTitle}</title>
    <jsp:include page="/WEB-INF/pages/scripts-and-css.jsp"/>
  </head>
  <body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-3 col-lg-3">
        <jsp:include page="/WEB-INF/pages/menu.jsp"/>
      </div>
    </div>
    </div>
    <div class="row">
      <div class="col-xs-12">
        <jsp:include page="/WEB-INF/pages/quick-acсess.jsp"/>
      </div>
    </div>
  </body>
</html>
