<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 05.08.2017
  Time: 1:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.javalab.songslyricswebsite.model.user.UserType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.language}"/>
<html lang="${sessionScope.language}">
<head>
    <title>${requestScope.songTitle}</title>
    <jsp:include page="scripts-and-css.jsp"/>
    <link rel="stylesheet" href="/css/song.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <jsp:include page="menu.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="quick-acсess.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="song">
                    <div class="song-name">
                        <c:out value="${requestScope.songTitle}"/>
                    </div>
                    <div class="song-lyrics">
                        <c:forEach items="${requestScope.listOfLyricsParts}" var="lyricsPart">
                            <div class="song-part">
                                <c:out value="${lyricsPart.getType().toString(sessionScope.language)}"/>
                                <c:forEach items="${lyricsPart.getComponents()}" var="line">
                                    <div class="line">
                                        <c:out value="${line.toString()}"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3">
                            <div class="song-video">
                                <div class="embed-responsive embed-responsive-16by9">
                                    <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/${requestScope.youTubeLink}"></iframe>
                                </div>
                            </div>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${requestScope.isApproved}">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="song-approved">
                                        <b>
                                            <fmt:bundle basename="labels">
                                                <fmt:message key="labels.approvedsong"/>
                                            </fmt:bundle>
                                        </b>
                                    </div>
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="row">
                                <div class="col-sx-12">
                                    <div class="song-not-approved">
                                        <b></b>
                                        <c:if test="${not empty sessionScope.user}">
                                            <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                                                <form method="post" action="/controller">
                                                    <input type="hidden" name="command" value="approvesong"/>
                                                    <input type="hidden" name="songID" value="${requestScope.songID}"/>
                                                    <input type="submit" value="<fmt:bundle basename="labels"><fmt:message key="labels.approvesong"/> </fmt:bundle>"/>
                                                </form>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
        </div>
    </div>
</div>
</body>
</html>
