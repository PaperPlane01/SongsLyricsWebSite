<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 22.09.2017
  Time: 4:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<div id="empty-artist-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.artistname.empty"/>
    </fmt:bundle>
</div>

<div id="too-long-artist-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.artistname.toolong"/>
    </fmt:bundle>
</div>

<div id="empty-song-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songname.empty"/>
    </fmt:bundle>
</div>

<div id="too-long-song-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songname.toolong"/>
    </fmt:bundle>
</div>

<div id="too-long-featured-artists">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.featuredartists.toolong"/>
    </fmt:bundle>
</div>

<div id="too-long-song-genres">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songgenres.toolong"/>
    </fmt:bundle>
</div>

<div id="too-long-youtube-video-id">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.youtubevideoid.toolong"/>
    </fmt:bundle>
</div>

<div id="empty-lyrics">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songlyrics.empty"/>
    </fmt:bundle>
</div>

<div id="too-long-lyrics">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songlyrics.toolong"/>
    </fmt:bundle>
</div>

<div id="unsupported-tags">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songlyrics.unsupportedtags"/>
    </fmt:bundle>
</div>

<div id="unclosed-tags">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songlyrics.unclosedtags"/>
    </fmt:bundle>
</div>

<div id="nested-tags">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.songlyrics.nestedtags"/>
    </fmt:bundle>
</div>