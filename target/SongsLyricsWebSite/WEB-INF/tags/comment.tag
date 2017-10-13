<%@ taglib prefix="tagFiles" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="authorID" required="true" %>
<%@ attribute name="authorName" required="true" %>
<%@ attribute name="content" required="true" %>
<%@ attribute name="date" required="true" %>
<%@ attribute name="isAuthorBlocked" required="true" type="java.lang.String" %>
<%@ attribute name="currentUserRole" required="true" type="java.lang.String" %>
<div class="comment" id="${id}">
    <div class="card card-body">
        <div class="card-header">
            <strong>
                <a href="${pageContext.request.contextPath}/controller?command=profile&userID=${authorID}" class="author-name">${authorName}</a><span class="text-muted comment-date">${date}</span>
                <span class="comment-id">&#x2116;${id}</span>
            </strong>
        </div>
        <div class="card-body">
            <p class="card-text comment-content">${content}</p>
        </div>
        <c:if test="${currentUserRole == 'moderator'}">
           <div class="card-footer">
               <div class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                       <fmt:bundle basename="labels">
                           <fmt:message key="labels.adminoptions"/>
                       </fmt:bundle>
                   </a>

                   <div class="dropdown-menu dropdown-primary" aria-labelledby="navbarDropdownMenuLink">
                       <c:choose>
                           <c:when test="${isAuthorBlocked == 'true'}">
                               <a class="dropdown-item unblock-user" href="#">
                                   <div class="comment-author-id" style="display: none">${authorID}</div>
                                   <fmt:bundle basename="labels">
                                       <fmt:message key="labels.unblockuser"/>
                                   </fmt:bundle>
                               </a>
                           </c:when>

                           <c:otherwise>
                               <a class="dropdown-item block-user" href="#">
                                   <div class="comment-author-id" style="display: none">${authorID}</div>
                                   <fmt:bundle basename="labels">
                                       <fmt:message key="labels.blockuser"/>
                                   </fmt:bundle>
                               </a>
                           </c:otherwise>
                       </c:choose>

                       <a class="dropdown-item delete-comment" href="#">
                           <div class="deleted-comment-id" style="display: none">${id}</div>
                           <fmt:bundle basename="labels">
                               <fmt:message key="labels.deletecomment"/>
                           </fmt:bundle>
                       </a>
                   </div>
               </div>
           </div>
        </c:if>
    </div>
</div>