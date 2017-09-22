<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<div id="empty-user-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.emptyusername"/>
    </fmt:bundle>
</div>

<div id="too-short-user-name">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.tooshortusername"/>
    </fmt:bundle>
</div>

<div id="too-long-username">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.toolongusername"/>
    </fmt:bundle>
</div>

<div id="invalid-username">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.invalidusername"/>
    </fmt:bundle>
</div>

<div id="empty-password">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.emptypassword"/>
    </fmt:bundle>
</div>

<div id="too-short-password">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.tooshortpassword"/>
    </fmt:bundle>
</div>

<div id="too-long-password">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.toolongpassword"/>
    </fmt:bundle>
</div>

<div id="invalid-password">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.invalidpassword"/>
    </fmt:bundle>
</div>

<div id="passwords-are-not-equal">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.passwordsarentequal"/>
    </fmt:bundle>
</div>

<div id="invalid-fields">
    <fmt:bundle basename="labels">
        <fmt:message key="labels.errors.invalidfields"/>
    </fmt:bundle>
</div>