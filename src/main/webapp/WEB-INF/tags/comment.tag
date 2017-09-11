<%@ taglib prefix="tagFiles" tagdir="/WEB-INF/tags" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="authorID" required="true" %>
<%@ attribute name="authorName" required="true" %>
<%@ attribute name="content" required="true" %>
<%@ attribute name="date" required="true" %>
<div class="comment" id="${id}">
    <div class="card card-body">
        <div class="card-header">
            <strong>
                <a href="/controller?command=profile&userID=${authorID}" class="author-name">${authorName}</a><span class="text-muted comment-date">${date}</span>
                <span class="comment-id">&#x2116;${id}</span>
            </strong>
        </div>
        <div class="card-body">
            <p class="card-text">${content}</p>
        </div>
    </div>
</div>