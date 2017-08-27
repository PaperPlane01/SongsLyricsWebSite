<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${requestScope.language}"/>
<html lang="${requestScope.language}">
<head>
    <title>Add new song</title>
    <jsp:include page="scripts-and-css.jsp"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <jsp:include page="menu.jsp"></jsp:include>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label for="artist-name">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.songartist"/>
                    </fmt:bundle>:
                </label>
                <input type="text" class="form-control" id="artist-name">
            </div>

            <div class="form-group">
                <label for="featured-artists">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.songfeaturedartists"/>
                    </fmt:bundle>:
                </label>
                <input type="text" class="form-control" id="featured-artists">
            </div>

            <div class="form-group">
                <label for="song-name">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.songname"/>
                    </fmt:bundle>:
                </label>
                <input type="text" class="form-control" id="song-name"/>
            </div>

            <div class="form-group">
                <label for="song-lyrics">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.songlyrics"/>
                    </fmt:bundle>:
                </label>
                <textarea class="form-control" id="song-lyrics"></textarea>
            </div>

            <div class="form-group">
                <button class="btn btn-default" id="add-song">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.addsong"/>
                    </fmt:bundle>
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
