<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="labels"/>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
  <head>
    <title>Song Lyrics Website</title>
    <jsp:include page="/WEB-INF/pages/css.jsp"/>
  </head>
  <body>
  <jsp:include page="/WEB-INF/pages/menu.jsp"/>
  <div class="container">
    <div class="row">
      <div class="col-xs-12">
        <jsp:include page="/WEB-INF/pages/quick-acсess.jsp"/>
      </div>
    </div>
  </div>
  <jsp:include page="/WEB-INF/pages/scripts.jsp"/>
  </body>
</html>
