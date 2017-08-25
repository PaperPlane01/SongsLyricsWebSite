<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="kz.javalab.songslyricswebsite.model.user.UserType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<div class="navbar-default navbar-fixed-top" role="navigation">

    <div class="navbar navbar-header">
        <span class="navbar-brand" id="web-site-name">Songs Lyrics Website</span>
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toogle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>

    <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">

            <li><a href="#">
                <fmt:bundle basename="labels">
                    <fmt:message key="labels.search"/>
                </fmt:bundle>
            </a></li>

            <li><a href="/controller?command=newsong">
                <fmt:bundle basename="labels">
                    <fmt:message key="labels.addsong"/>
                </fmt:bundle>
            </a></li>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li><a href="/controller?command=myprofile" class="my-profile">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.myprofile"/>
                        </fmt:bundle>
                    </a> </li>

                    <c:if test="${sessionScope.user.getUserType() == UserType.MODERATOR}">
                        <li><a href="/controller?command=notapprovedsongs">
                            <fmt:bundle basename="labels">
                                <fmt:message key="labels.songstoapprove"/>
                            </fmt:bundle>
                        </a> </li>
                    </c:if>

                    <li><a href="#" class="logout">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.logout"/>
                        </fmt:bundle>
                    </a> </li>

                </c:when>

                <c:otherwise>
                    <li><a href="/controller?command=registration">
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.singup"/>
                        </fmt:bundle>
                    </a></li>

                    <li>
                        <div class="showing-login-form-button" style="display: block">
                            <button type="button" class="btn-primary" data-toggle="modal" data-target="#loginmodal">
                                <fmt:bundle basename="labels">
                                    <fmt:message key="labels.login"/>
                                </fmt:bundle>
                            </button>
                        </div>
                    </li>


                </c:otherwise>
            </c:choose>

            <li>
                <div class="dropdown">
                    <button class="btn btn-default" type="button" id="change-language-button" data-toggle="dropdown">
                        <span class="caret"></span>
                        <fmt:bundle basename="labels">
                            <fmt:message key="labels.selectlanguage"/>
                        </fmt:bundle>
                    </button>

                    <ul class="dropdown-menu">
                        <li><a href="#" id="english-language-select"><fmt:bundle basename="labels">
                            <fmt:message key="labels.englishlanguage"/>
                        </fmt:bundle></a></li>
                        <li><a href="#" id="russian-language-select"><fmt:bundle basename="labels">
                            <fmt:message key="labels.russianlanguage"/>
                        </fmt:bundle></a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>


<div class="modal fade" id="loginmodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3>
                    <fmt:bundle basename="labels">
                        <fmt:message key="labels.enterusernameandpassword"/>
                    </fmt:bundle>
                </h3>
            </div>

            <div class="modal-body">
                <form>
                    <input type="text" name="username" id="username" placeholder="<fmt:bundle basename="labels"><fmt:message key="labels.enterusername"/></fmt:bundle>"/>
                    <input type="password" name="password" id="password" placeholder="<fmt:bundle basename="labels"><fmt:message key="labels.enterpassword"/></fmt:bundle>"/>
                    <input type="button" id="login-button" value="<fmt:bundle basename="labels"><fmt:message key="labels.login"/></fmt:bundle>"/>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="loginmodal">Close</button>
                <div id="message" style="display: none;"></div>
            </div>
        </div>
    </div>
</div>





