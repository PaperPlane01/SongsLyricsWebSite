<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html lang="${sessionScope.language}">
<head>
    <title>Add new song</title>
    <jsp:include page="css.jsp"/>
    <link rel="stylesheet" href="/css/addsong.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="menu.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="quick-acсess.jsp"/>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div class="song-adding-panel">
                <form>
                    <div class="form-group">
                        <label for="artist-name">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songartist"/>
                            </fmt:bundle>:
                        </label>
                        <span class="message" id="artist-name-message" style="display: none"></span>
                        <input type="text" class="form-control" id="artist-name">
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="featured-artists">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songfeaturedartists"/>
                            </fmt:bundle>:
                        </label>
                        <span class="message" id="featured-artists-message" style="display: none"></span>
                        <input type="text" class="form-control" id="featured-artists">
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
                        <input type="text" class="form-control" id="song-name"/>
                    </div>
                </form>

                <form>
                    <div class="form-group">
                        <label for="song-genres">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songgenres"/>
                            </fmt:bundle>
                        </label>
                        <span class="message" id="song-genres-message" style="display: none"></span>
                        <input type="text" class="form-control" id="song-genres">
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
                        <input type="text" class="form-control" id="youtube-video-id">
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
                    <textarea class="form-control" id="song-lyrics"></textarea>
                </div>

                <div class="form-group">
                    <a class="btn btn-default" id="add-song">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.addsong"/>
                        </fmt:bundle>
                    </a>
                </div>

                <span class="message" id="message" style="display: none"></span>
            </div>
        </div>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
<script src="/scripts/song-validation.js"></script>
<script src="/scripts/add-song.js"></script>
</body>
</html>
