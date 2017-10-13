<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="labels"/>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
  <head>
    <title>Song Lyrics Website</title>
    <jsp:include page="/WEB-INF/pages/css.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spinning-loader.css">
  </head>
  <body>
  <jsp:include page="/WEB-INF/pages/menu.jsp"/>
  <div class="container">
      <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
              <jsp:include page="/WEB-INF/pages/quick-acсess.jsp"/>
          </div>
      </div>
      <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
              <div id="greetings-message">
                  <div class="card card-body">
                      <div class="card-header">
                          <strong>
                              <fmt:bundle basename="labels">
                                  <fmt:message key="labels.mainpage.greetings"/>
                              </fmt:bundle>
                          </strong>
                      </div>
                      <div class="card-body">
                          <p class="card-text">
                              <fmt:bundle basename="labels">
                                  <fmt:message key="labels.mainpage.decription"/> <a href="${pageContext.request.contextPath}/controller?command=newsong"><fmt:message key="labels.mainpage.addsong"/></a><fmt:message key="labels.mainpage.dot"/>
                              </fmt:bundle>
                          </p>
                      </div>
                  </div>
              </div>
          </div>
      </div>

      <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
              <div id="top-ten-rated-songs">
                  <div class="card card-body">
                      <div class="card-header">
                          <strong>
                              <fmt:bundle basename="labels">
                                  <fmt:message key="labels.mainpage.toptenratedsongs"/>
                              </fmt:bundle>
                          </strong>
                      </div>
                      <div class="card-body">
                          <div class="loader" id="top-ten-rated-songs-loader"></div>
                          <div id="top-ten-rated-songs-message" style="display: none"></div>
                          <ul class="list-group" id="top-ten-rated-songs-list">

                          </ul>
                      </div>
                  </div>
              </div>
          </div>

          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
              <div id="recently-added-songs">
                  <div class="card card-body">
                      <div class="card-header">
                          <strong>
                              <fmt:bundle basename="labels">
                                  <fmt:message key="labels.mainpage.recenltyaddedsongs"/>
                              </fmt:bundle>
                          </strong>
                      </div>
                      <div class="card-body">
                          <div class="loader" id="recently-added-songs-loader"></div>
                          <div id="recently-added-songs-message" style="display: none"></div>
                          <ul class="list-group" id="recently-added-songs-list">

                          </ul>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  </div>
  <div id="messages" style="display: none">
      <jsp:include page="/WEB-INF/pages/messages/login-and-sign-up-messages.jsp"/>
  </div>
  <jsp:include page="/WEB-INF/pages/scripts.jsp"/>
  <script src="${pageContext.request.contextPath}/scripts/songs-loader.js"></script>
  </body>
</html>
