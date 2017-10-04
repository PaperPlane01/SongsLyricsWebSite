$(document).ready(function () {
    let songID = $("#song-id").html();
    let commentsManager = new CommentsManager();
    let commentValidator = new CommentValidator();
    let commentValidatorView = new CommentValidatorView();

    $("#comment-content").on('input', function () {
        let content = $("#comment-content").val();
        commentValidator.validateComment(content);
        commentValidatorView.displayValidationResult(commentValidator.result);
    });

    $("#comment-content").on('focusout', function () {
        let content = $("#comment-content").val();
        commentValidator.validateComment(content);
        commentValidatorView.displayValidationResult(commentValidator.result);
    });

    $("#add-comment-button").on('click', function () {
        let content = $("#comment-content").val();
        commentValidator.validateComment(content);

        if (commentValidator.result !== CommentValidationResults.SUCCESS) {
            commentValidatorView.displayValidationResult(commentValidator.result);
        } else {
            commentsManager.sendComment(content, songID);
        }
    });
});

function CommentsManager() {

    let self = this;

    this.sendComment = function (content, songID) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'add_comment',
                    songID : songID,
                    content : content
                },

                success : function (response) {
                    self._handleResponse(response)
                }
            }
        )
    };

    this._handleResponse = function (responseData) {
       if (responseData.status === "SUCCESS") {
           location.reload();
       }

       if (responseData.status === "FAILURE") {
           let message = responseData.message;
           self._showErrorMessage(message);
       }
    };

    this._showErrorMessage = function (message) {
        $("#user-messages").css('display', 'block');
        $("#user-messages").html("<span style = \"color:red\">" + message + "</span>")
    }
}

function CommentValidator() {
    this.result = 0;

    this.validateComment = function (commentContent) {
        if (commentContent.trim().length === 0) {
            this.result = CommentValidationResults.EMPTY_COMMENT;
            return;
        }

        if (commentContent.length > 1000) {
            this.result = CommentValidationResults.TOO_LONG_COMMENT;
            return;
        }

        this.result = CommentValidationResults.SUCCESS;
    }
}

function CommentValidatorView() {
    this.displayValidationResult = function (result) {
        if (result === CommentValidationResults.SUCCESS) {
            this._hideErrorMessage();
            return;
        }

        if (result === CommentValidationResults.EMPTY_COMMENT) {
            let message = $("#empty-comment-message").html();
            this._showErrorMessage(message);
            return;
        }

        if (result === CommentValidationResults.TOO_LONG_COMMENT) {
            let message = $("#too-long-comment-message").html();
            this._showErrorMessage(message);
            return;
        }
    };

    this._showErrorMessage = function (message) {
        $("#comment-validation-message").css('display', 'block');
        $("#comment-validation-message").html("<span style = \"color:red\">" + message + "</span>")
    };

    this._hideErrorMessage = function () {
        $("#comment-validation-message").css('display', 'none');
    }
}

let CommentValidationResults = {
    EMPTY_COMMENT : 1,
    TOO_LONG_COMMENT : 2,
    SUCCESS : 3
};

