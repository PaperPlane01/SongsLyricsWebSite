<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.javalab.songslyricswebsite.entity.user.UserType" %>
<fmt:setLocale value="${sessionScope.language}"/>
<nav class="navbar navbar-fixed-top navbar-expand-lg navbar-dark">

    <a class="navbar-brand" href="${pageContext.request.contextPath}">Song Lyrics Website</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=new_song">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.addsong"/>
                    </fmt:bundle>
                </a>
            </li>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=profile&userID=${sessionScope.user.getID()}" id="my-profile">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.myprofile"/>
                            </fmt:bundle>
                        </a>
                    </li>

                    <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=not_approved_songs">
                                <fmt:bundle basename="labels">
                                    <fmt:message key="labels.songstoapprove"/>
                                </fmt:bundle>
                            </a>
                        </li>
                    </c:if>

                    <li class="nav-item">
                        <a class="nav-link" href="#" id="logout">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.logout"/>
                            </fmt:bundle>
                        </a>
                    </li>
                </c:when>

                <c:otherwise>

                    <li>
                        <button type="button" class="btn btn-primary btn-lg" id="showing-singup-form-button" data-toggle="modal" data-target="#modal-register">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.singup"/>
                            </fmt:bundle>
                        </button>
                    </li>

                    <li>
                        <button type="button" class="btn btn-primary btn-lg" id="showing-login-form-button" data-toggle="modal" data-target="#modal-login">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.login"/>
                            </fmt:bundle>
                        </button>
                    </li>
                </c:otherwise>
            </c:choose>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.selectlanguage"/>
                    </fmt:bundle>
                </a>
                <div class="dropdown-menu dropdown-primary" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" id="english-language-select" href="#">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.englishlanguage"/>
                        </fmt:bundle>
                    </a>
                    <a class="dropdown-item" id="russian-language-select" href="#">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.russianlanguage"/>
                        </fmt:bundle>
                    </a>
                </div>
            </li>

        </ul>
    </div>

</nav>

<div class="modal fade" id="modal-login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <div class="modal-content">

            <div class="modal-header light-blue darken-3 white-text">
                <h4 class="title"><i class="fa fa-user"></i><fmt:bundle basename="labels">
                    <fmt:message key="labels.login"/>
                </fmt:bundle></h4>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="md-form form-sm">
                    <i class="fa fa-envelope prefix"></i>
                    <input type="text" id="username" class="form-control">
                    <label for="username"><fmt:bundle basename="labels"><fmt:message key="labels.enterusername"/></fmt:bundle></label>
                </div>

                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="password" class="form-control">
                    <label for="password"><fmt:bundle basename="labels"><fmt:message key="labels.enterpassword"/></fmt:bundle></label>
                </div>

                <div class="text-center mt-2">
                    <button class="btn btn-info" id="login-button"><fmt:bundle basename="labels"><fmt:message key="labels.login"/></fmt:bundle> <i class="fa fa-sign-in ml-1"></i></button>
                </div>

                <div id="login-message"></div>

            </div>
            <div class="modal-footer">
                <button type="button" id="login-form-closing-button" class="btn btn-outline-info waves-effect ml-auto" data-dismiss="modal"><fmt:bundle basename="labels">
                    <fmt:message key="labels.close"/>
                </fmt:bundle></button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-register" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cascading-modal" role="document">
        <div class="modal-content">

            <div class="modal-header light-blue darken-3 white-text">
                <h4 class="title"><i class="fa fa-user-plus"></i> <fmt:bundle basename="labels"><fmt:message key="labels.singup"/> </fmt:bundle></h4>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="username-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-envelope prefix"></i>
                    <input type="text" id="username-for-signup" class="form-control">
                    <label for="username-for-signup"><fmt:bundle basename="labels"><fmt:message key="labels.enterusername"/> </fmt:bundle></label>
                </div>

                <div id="password-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="password-for-sign-up" class="form-control">
                    <label for="password-for-sign-up"><fmt:bundle basename="labels"><fmt:message key="labels.enterpassword"/> </fmt:bundle></label>
                </div>

                <div id="second-password-message" style="display: none"></div>
                <div class="md-form form-sm">
                    <i class="fa fa-lock prefix"></i>
                    <input type="password" id="repeat-password-for-sign-up" class="form-control">
                    <label for="repeat-password-for-sign-up"><fmt:bundle basename="labels"><fmt:message key="labels.repeatpassword"/> </fmt:bundle></label>
                </div>

                <div id="sign-up-message" style="display: none"></div>

                <div class="text-center mt-2">
                    <button class="btn btn-info" id="sign-up-button"><fmt:bundle basename="labels"><fmt:message key="labels.singup"/> </fmt:bundle> <i class="fa fa-sign-in ml-1"></i></button>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" id="sign-up-form-closing-button" class="btn btn-outline-info waves-effect ml-auto" data-dismiss="modal"><fmt:bundle basename="labels"><fmt:message key="labels.close"/> </fmt:bundle></button>
            </div>
        </div>
    </div>
</div>





