<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.editsong"/>
        </fmt:bundle>
    </title>
    <jsp:include page="css.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-song.css"/>
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
            <div id="song-editing-panel">

                <form>
                    <div class="form-group">
                        <label for="song-id">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songid"/>
                            </fmt:bundle>
                        </label>
                        <input type="text" class="form-control" id="song-id" value="${requestScope.songID}" readonly>
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="artist-name">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songartist"/>
                            </fmt:bundle>:
                        </label>
                        <span class="message" id="artist-name-message" style="display: none"></span>
                        <input type="text" class="form-control" id="artist-name" value="${requestScope.songArtist.getName()}">
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="featured-artists">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songfeaturedartists"/> <fmt:message key="labels.separatewithsemicolon"/>
                            </fmt:bundle>:
                        </label>
                        <span class="message" id="featured-artists-message" style="display: none"></span>
                        <input type="text" class="form-control" id="featured-artists" value="${requestScope.featuredArtists}">
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="song-name">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songname"/>
                            </fmt:bundle>:
                        </label>
                        <span class="message" id="song-name-message" style="display: none"></span>
                        <input type="text" class="form-control" id="song-name" value="${requestScope.songName}"/>
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="song-genres">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songgenres"/> <fmt:message key="labels.separatewithsemicolon"/>
                            </fmt:bundle>
                        </label>
                        <span class="message" id="song-genres-message" style="display: none"></span>
                        <input type="text" class="form-control" id="song-genres" value="${requestScope.songGenres}">
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="youtube-video-id">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.youtubevideoid"/>
                            </fmt:bundle>
                        </label>
                        <span class="message" id="song-youtube-video-id-message" style="display:none"></span>
                        <input type="text" class="form-control" id="youtube-video-id" value="${requestScope.youTubeVideoID}">
                    </div>
                </form>

                <form class="form-inline">
                    <div class="form-group">

                        <a class="btn btn-default" id="add-intro-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addintro"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-chorus-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addchorus"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-verse-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addverse"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-hook-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addhook"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-bridge-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addbridge"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-outro-button" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addoutro"/>
                            </fmt:bundle>
                        </a>

                        <a class="btn btn-default" id="add-other-part" href="#">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.addotherpart"/>
                            </fmt:bundle>
                        </a>
                    </div>
                </form>

                <div class="form-group">
                    <label for="song-lyrics">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.songlyrics"/>
                        </fmt:bundle>:
                    </label>
                    <span class="message" id="song-lyrics-message" style="display: none"></span>
                    <textarea class="form-control" id="song-lyrics">${requestScope.songLyrics}</textarea>
                </div>

                <div class="form-group">
                    <a class="btn btn-default" id="apply-changes-button">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.applychanges"/>
                        </fmt:bundle>
                    </a>
                </div>

                <span class="message" id="message" style="display: none"></span>
            </div>
        </div>
    </div>
    <div id="messages" style="display: none">
        <jsp:include page="messages/login-and-sign-up-messages.jsp"/>
        <jsp:include page="messages/song-validation-messages.jsp"/>
    </div>
    <jsp:include page="scripts.jsp"/>
    <script src="${pageContext.request.contextPath}/scripts/song-validation.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/edit-song.js"></script>
</div>
</body>
</html>
