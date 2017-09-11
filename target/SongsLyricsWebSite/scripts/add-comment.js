$(document).ready(function () {
    let songID = $("#song-id").html();
    let commentsManager = new CommentsManager();

    $("#add-comment-button").on('click', function () {
        let content = $("#comment-content").val();
        commentsManager.sendComment(content, songID);
    });
});

function CommentsManager() {
    this.sendComment = function (content, songID) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'addcomment',
                    songID : songID,
                    content : content
                },

                success : function (response) {
                    if (response.status === "SUCCESS") {
                        location.reload();
                    }

                    if (response.status === "FAILURE") {
                        alert(response.message);
                    }
                }
            }
        )
    }
}