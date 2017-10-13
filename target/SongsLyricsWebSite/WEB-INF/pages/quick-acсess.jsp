<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<div id="quick-song-access-message" style="display:none;"></div>
<div id="choose-artist-letter-message" style="display: none">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.chooseartistletter"/>
    </fmt:bundle>
</div>
<div id="choose-artist-name-message" style="display: none">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.chooseartistname"/>
    </fmt:bundle>
</div>
<div id="choose-song-message" style="display: none">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.choosesong"/>
    </fmt:bundle>
</div>
<div class="quick-song-access-menu">
    <select class="selectpicker browser-default" id="artist-letter-select" data-live-search="true">
        <option id="choose-letter" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.chooseartistletter"/>
            </fmt:bundle>
        </option>
    </select>

    <select class="selectpicker browser-default" id="artist-name-select" data-live-search="true" disabled>
        <option id="choose-artist-name" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.chooseartistname"/>
            </fmt:bundle>
        </option>
    </select>

    <select class="selectpicker browser-default" id="song-name-select" data-live-search = "true" disabled>
        <option id="choose-song" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.choosesong"/>
            </fmt:bundle>
        </option>
    </select>

    <button class="btn btn-default" id="go-to-song-button" href="" disabled>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.gotosong"/>
        </fmt:bundle>
    </button>
</div>
