<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<div class="quick-song-access-menu">
    <select class="selectpicker" id="artist-letter-select" data-live-search = "true">
        <option id="choose-letter" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.chooseartistletter"/>
            </fmt:bundle>
        </option>
    </select>

    <select class="selectpicker" id="artist-name-select" data-live-search = "true" disabled>
        <option id="choose-artist-name" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.chooseartistname"/>
            </fmt:bundle>
        </option>
    </select>

    <select class="selectpicker" id="song-name-select" data-live-search = "true" disabled>
        <option id="choose-song" data-hidden="true">
            <fmt:bundle basename="labels">
                <fmt:message key="labels.choosesong"/>
            </fmt:bundle> </option>
    </select>

    <a class="btn btn-default" id="go-to-song-button" href="" disabled>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.gotosong"/>
        </fmt:bundle>
    </a>
</div>
