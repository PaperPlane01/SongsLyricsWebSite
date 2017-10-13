<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<html>
<head>
    <title>${requestScope.profileOwner.getUsername()}</title>
    <jsp:include page="css.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
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
        <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
           <div class="card card-body user-information">
               <div class="card-header user-information-header">
                   <b>
                       <fmt:bundle basename="labels">
                           <fmt:message key="labels.userinformation"/>
                       </fmt:bundle>
                   </b>
               </div>
               <div class="card-body">
                   <p><b><fmt:bundle basename="labels"><fmt:message key="labels.userid"/> </fmt:bundle></b>: ${requestScope.profileOwner.getID()}</p>
                   <p><b><fmt:bundle basename="labels"><fmt:message key="labels.username"/> </fmt:bundle></b>: ${requestScope.profileOwner.getUsername()}</p>
                   <p><b><fmt:bundle basename="labels"><fmt:message key="labels.userrole"/> </fmt:bundle></b>: ${requestScope.profileOwner.getUserType().toLocalizedString(sessionScope.language)}</p>
                   <p><b><fmt:bundle basename="labels"><fmt:message key="labels.numberofcomments"/> </fmt:bundle></b>: ${requestScope.numberOfComments}</p>
               </div>
           </div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
            <div class="card card-body contributed-songs">
                <div class="card-header contributed-songs-header">
                    <strong>
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.contributedsongs"/>
                        </fmt:bundle>
                    </strong>
                </div>
                <div class="card-body">
                    <ul class="list-group" id="contributed-songs-list">
                        <c:forEach var="song" items="${requestScope.contributedSongs}">
                            <li class="list-group-item">
                                <a href="${pageContext.request.contextPath}/controller?command=song&songID=${song.getID()}">${song.getTitle()}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="messages" style="display: none">
    <jsp:include page="messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
