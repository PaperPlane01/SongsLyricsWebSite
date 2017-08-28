<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
