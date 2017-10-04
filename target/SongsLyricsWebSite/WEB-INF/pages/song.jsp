<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.javalab.songslyricswebsite.entity.user.UserType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagFiles" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
<head>
    <title>${requestScope.songTitle}</title>
    <jsp:include page="css.jsp"/>
    <link rel="stylesheet" href="/css/song.css"/>
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
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="song">
                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                            <a class="btn btn-default" href="/controller?command=edit_song&songID=${requestScope.songID}">
                                <fmt:bundle basename="labels">
                                    <fmt:message key="labels.editsong"/>
                                </fmt:bundle>
                            </a>
                        </c:if>
                    </c:if>
                    <div class="song-name">
                        <c:out value="${requestScope.songTitle}"/>
                    </div>
                    <div class="song-lyrics">
                        <c:forEach items="${requestScope.listOfLyricsParts}" var="lyricsPart">
                            <div class="song-part">
                                <c:out value="${lyricsPart.getType().toLocalizedString(sessionScope.language)}"/>
                                <c:forEach items="${lyricsPart.getComponents()}" var="line">
                                    <div class="line">
                                        <c:out value="${line.toString()}"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="song-video">
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/${requestScope.youTubeVideoID}"></iframe>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${requestScope.isApproved}">
                            <div class="song-approved">
                                <b>
                                    <fmt:bundle basename="labels">
                                        <fmt:message key="labels.approvedsong"/>
                                    </fmt:bundle>
                                </b>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="song-not-approved">
                                <b>
                                    <fmt:bundle basename="labels">
                                        <fmt:message key="labels.songisnotapproved"/>
                                    </fmt:bundle>
                                </b>
                                <c:if test="${not empty sessionScope.user}">
                                    <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                                        <form method="post" action="/controller">
                                            <input type="hidden" name="command" value="approve_song"/>
                                            <input type="hidden" name="songID" value="${requestScope.songID}"/>
                                            <input type="submit" value="<fmt:bundle basename="labels"><fmt:message key="labels.approvesong"/> </fmt:bundle>"/>
                                        </form>
                                    </c:if>
                                </c:if>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${not empty sessionScope.user}">

                        <div class="song-rating-select">
                            <select class="selectpicker" id="rating-of-song">
                                <option id="rate-song" data-hidden="true">
                                    <c:choose>
                                        <c:when test="${requestScope.userHasRatedSong}">
                                            <fmt:bundle basename="labels">
                                                <fmt:message key="labels.yourrating"/>
                                            </fmt:bundle> ${requestScope.userRatingOfSong}
                                        </c:when>

                                        <c:otherwise>
                                            <fmt:bundle basename="labels">
                                                <fmt:message key="labels.ratesong"/>
                                            </fmt:bundle>
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                            </select>
                        </div>

                        <a class="btn btn-default" id="rate-song-button">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.rate"/>
                            </fmt:bundle>
                        </a>

                        <div id="song-rating-message" style="display: none"></div>
                    </c:if>
                </div>
        </div>
    </div>
    
    <div class="row">
        <c:if test="${not empty sessionScope.user}">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div id="comment-adding-panel">
                    <form>
                        <div class="form-group">
                            <label for="comment-content" id="add-comment-label">Add commentary:</label>
                            <textarea class="form-control" id="comment-content"></textarea>
                            <a class="btn btn-default" id="add-comment-button">Comment</a>
                            <div id="comment-validation-message" style="display: none"></div>
                            <div id="user-messages" style="display: none"></div>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>

        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <c:choose>
                <c:when test="${not empty requestScope.comments}">
                    <c:set var="currentUserRole" scope="page" value="none"/>

                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                            <c:set var="currentUserRole" value="moderator"/>
                        </c:if>
                    </c:if>

                    <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                        <div id="admin-messages" style="display: none"></div>
                    </c:if>
                    <div id="comments">
                        <c:forEach var="comment" items="${requestScope.comments}">
                            <c:choose>
                                <c:when test="${comment.getAuthor().isBlocked() == true}">
                                    <c:set var="isAuthorBlocked" value="true"/>
                                </c:when>

                                <c:otherwise>
                                    <c:set var="isAuthorBlocked" value="false"/>
                                </c:otherwise>
                            </c:choose>

                            <tagFiles:comment id="${comment.getID()}"
                                              authorID="${comment.getAuthor().getID()}"
                                              authorName="${comment.getAuthor().getUsername()}"
                                              content="${comment.getContent()}"
                                              date="${comment.getTime()}"
                                              currentUserRole="${currentUserRole}"
                                              isAuthorBlocked="${isAuthorBlocked}"
                            />

                        </c:forEach>
                    </div>

                </c:when>

                <c:otherwise>
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.nocomments"/>
                    </fmt:bundle>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
<c:if test="${not empty sessionScope.user}">
    <div id="user-id" style="display: none">${sessionScope.user.getID()}</div>
    <div id="song-id" style="display: none;">${requestScope.songID}</div>
</c:if>
<div id="messages" style="display: none">
    <jsp:include page="messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="scripts.jsp"/>
<c:if test="${not empty sessionScope.user}">
    <script src="/scripts/song-rating.js"></script>
    <script src="/scripts/add-comment.js"></script>
</c:if>

<div id="localized-messages" style="display: none">
    <div id="too-long-comment-message">
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.commentistoolong"/>
        </fmt:bundle>
    </div>

    <div id="empty-comment-message">
        <fmt:bundle basename="labels">
            <fmt:message key="labels.errors.commentisempty"/>
        </fmt:bundle>
    </div>
</div>

<c:if test="${not empty sessionScope.user}">
    <div id="user-id" style="display: none">${sessionScope.user.getID()}</div>
    <div id="song-id" style="display: none;">${requestScope.songID}</div>

    <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
        <script src="/scripts/admin.js"></script>
    </c:if>
</c:if>
</body>
</html>
