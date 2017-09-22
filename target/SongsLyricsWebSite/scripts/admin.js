$(document).ready(function () {
    let userBlockingManager = new UserBlockingManager();
    let commentDeletingManager = new CommentDeletingManager();

    $(".block-user").on('click', function () {
        let userID = $(this).children(".comment-author-id").html();
        userBlockingManager.blockUser(userID);
    });
    
    $(".delete-comment").on('click', function () {
        let commentID = $(this).children(".deleted-comment-id").html();
        commentDeletingManager.deleteComment(commentID);
    })
});

function UserBlockingManager() {
    let self = this;

    this.blockUser = function (userID) {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'blockuser',
                    userID : userID
                },

                success : function (responseData) {
                    self._handleBlockingResults(responseData)
                }
            }
        )
    };

    this._handleBlockingResults = function (responseData) {
        if (responseData.status === "SUCCESS") {
            let message = responseData.message;
            self._showSuccessMessage(message);
        }

        if (responseData.status === "FAILURE") {
            let message = responseData.message;
            self._showErrorMessage(message);
        }
    };

    this._showSuccessMessage = function (message) {
        $("#admin-messages").css('display', 'block');
        $("#admin-messages").html("<span style = \"color:green\">" + message + "</span>")
    };

    this._showErrorMessage = function (message) {
        $("#admin-messages").css('display', 'block');
        $("#admin-messages").html("<span style = \"color:red\">" + message + "</span>")
    }
}

function CommentDeletingManager() {
    let self = this;

    this.deleteComment = function (commentID) {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'deletecomment',
                    commentID : commentID
                },

                success : function (responseData) {
                    self._handleCommentDeletingResults(responseData, commentID)
                }
            }
        )
    };

    this._handleCommentDeletingResults = function (responseData, commentID) {
        if (responseData.status === "SUCCESS") {
            let message = responseData.message;
            self._showSuccessMessage(message);
            $("#comments").children("#" + commentID).css('display', 'none');
        }

        if (responseData.status === "FAILURE") {
            let message = responseData.message;
            self._showErrorMessage(message);
        }
    };

    this._showSuccessMessage = function (message) {
        $("#admin-messages").css('display', 'block');
        $("#admin-messages").html("<span style = \"color:green\">" + message + "</span>")
    };

    this._showErrorMessage = function (message) {
        $("#admin-messages").css('display', 'block');
        $("#admin-messages").html("<span style = \"color:red\">" + message + "</span>")
    }
}