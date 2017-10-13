<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <fmt:bundle basename="labels">
            <fmt:message key="labels.welcome"/>
        </fmt:bundle> ${sessionScope.user.getUsername()}
    </title>
   <jsp:include page="css.jsp"/>
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
                    <strong>
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.userinformation"/>
                        </fmt:bundle>
                    </strong>
                </div>
                <div class="card-body">
                    <p><b><fmt:bundle basename="labels"><fmt:message key="labels.userid"/> </fmt:bundle></b>: ${sessionScope.user.getID()}</p>
                    <p><b><fmt:bundle basename="labels"><fmt:message key="labels.username"/> </fmt:bundle></b>: ${sessionScope.user.getUsername()}</p>
                    <p><b><fmt:bundle basename="labels"><fmt:message key="labels.userrole"/> </fmt:bundle></b>: ${sessionScope.user.getUserType().toLocalizedString(sessionScope.language)}</p>
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
        <div class="col-xs-12">
            <button type="button" class="btn btn-primary btn-lg" id="showing-changing-password-form-button" data-toggle="modal" data-target="#modal-change-password">
                <fmt:bundle basename="labels">
                    <fmt:message key="labels.changepassword"/>
                </fmt:bundle>
            </button>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-change-password" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <div class="modal-content">

            <div class="modal-header light-blue darken-3 white-text">
                <h4 class="title"><i class="fa fa-lock prefix"></i>
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.changepassword"/>
                    </fmt:bundle></h4>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="current-password-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="current-password" class="form-control">
                    <label for="current-password"><fmt:bundle basename="labels"><fmt:message key="labels.currentpassword"/></fmt:bundle></label>
                </div>

                <div id="new-password-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="new-password" class="form-control">
                    <label for="new-password"><fmt:bundle basename="labels"><fmt:message key="labels.newpassword"/></fmt:bundle></label>
                </div>

                <div id="new-password-repeated-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="new-password-repeated" class="form-control">
                    <label for="new-password-repeated"><fmt:bundle basename="labels"><fmt:message key="labels.repeatpassword"/></fmt:bundle></label>
                </div>

                <div class="text-center mt-2">
                    <button class="btn btn-info" id="change-password-button"><fmt:bundle basename="labels"><fmt:message key="labels.changepassword"/></fmt:bundle></button>
                </div>

                <div id="changing-password-message" style="display: none"></div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-info waves-effect ml-auto" data-dismiss="modal"><fmt:bundle basename="labels">
                    <fmt:message key="labels.close"/></fmt:bundle>
                </button>
            </div>
        </div>
    </div>
</div>
<div id="messages" style="display: none">
    <jsp:include page="messages/login-and-sign-up-messages.jsp"/>
</div>
<jsp:include page="scripts.jsp"/>
<script src="${pageContext.request.contextPath}/scripts/change-password.js"></script>
</body>
</html>
