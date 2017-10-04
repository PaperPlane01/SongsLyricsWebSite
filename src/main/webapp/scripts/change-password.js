$(document).ready(function () {
    let signUpValidator = new SignUpValidator();
    let passwordValidatorView = new PasswordValidatorView();
    let passwordManager = new PasswordManager();

    $("#new-password").on('input', function () {
        let password = $("#new-password").val();
        signUpValidator.validatePassword(password);
        passwordValidatorView.displayNewPasswordValidationResult(signUpValidator.passwordValidationResult);
    });

    $("#new-password").on('focusout', function () {
        let password = $("#new-password").val();
        signUpValidator.validatePassword(password);
        passwordValidatorView.displayNewPasswordValidationResult(signUpValidator.passwordValidationResult);
    });

    $("#new-password-repeated-message").on('input', function () {
        let repeatedPassword = $("#new-password-repeated").val();
        let password = $("#new-password").val();
        signUpValidator.validateSecondPassword(password, repeatedPassword);
        passwordValidatorView.displayRepeatedPasswordValidationResult(signUpValidator.secondPasswordValidationResult);
    });

    $("#new-password-repeated-message").on('focusout', function () {
        let repeatedPassword = $("#new-password-repeated").val();
        let password = $("#new-password").val();
        signUpValidator.validateSecondPassword(password, repeatedPassword);
        passwordValidatorView.displayRepeatedPasswordValidationResult(signUpValidator.secondPasswordValidationResult);
    });

    $("#change-password-button").on('click', function () {
        let currentPassword = $("#current-password").val();
        let newPassword = $("#new-password").val();
        let repeatedPassword = $("#new-password-repeated").val();

        if (currentPassword.length === 0) {
            let message = $("#empty-password").html();
            $("#current-password-message").css('display', 'block');
            $("#current-password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        $("#current-password-message").css('display', 'none');
        signUpValidator.validatePassword(newPassword);

        if (signUpValidator.passwordValidationResult !== SignUpValidationResults.PASSWORD_SUCCESS) {
            passwordValidatorView.displayNewPasswordValidationResult(signUpValidator.passwordValidationResult);
            return;
        }

        signUpValidator.validateSecondPassword(newPassword, repeatedPassword);

        if (signUpValidator.secondPasswordValidationResult !== SignUpValidationResults.SECOND_PASSWORD_SUCCESS) {
            passwordValidatorView.displayRepeatedPasswordValidationResult(signUpValidator.secondPasswordValidationResult);
            return;
        }

        passwordManager.changePassword(currentPassword, newPassword, repeatedPassword);
    })
});

function PasswordValidatorView() {

    this.displayNewPasswordValidationResult = function (result) {
        let message;

        switch (result) {
            case SignUpValidationResults.PASSWORD_SUCCESS:
                $("#new-password-message").css('display', 'none');
                break;
            case SignUpValidationResults.EMPTY_PASSWORD:
                $("#new-password-message").css('display', 'block');
                message = $("#empty-password").html();
                $("#new-password-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case SignUpValidationResults.TOO_SHORT_PASSWORD:
                $("#new-password-message").css('display', 'block');
                message = $("#too-short-password").html();
                $("#new-password-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case SignUpValidationResults.TOO_LONG_PASSWORD:
                $("#new-password-message").css('display', 'block');
                message = $("#too-long-password").html();
                $("#new-password-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case SignUpValidationResults.INVALID_PASSWORD:
                $("#new-password-message").css('display', 'block');
                message = $("#invalid-password").html();
                $("#new-password-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            default:
                break;
        }
    };

    this.displayRepeatedPasswordValidationResult = function (result) { {
        let message;

        switch (result) {
            case SignUpValidationResults.SECOND_PASSWORD_SUCCESS:
                $("#new-password-repeated-message").css('display', 'none');
                break;
            case SignUpValidationResults.PASSWORDS_ARENT_EQUAL:
                message = $("#passwords-are-not-equal").html();
                $("#new-password-repeated-message").css('display', 'block');
                $("#new-password-repeated-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            default:
                break;
        }
    }

    }
}

function PasswordManager() {
    let self = this;

    this.changePassword = function (currentPassword, newPassword, repeatedPassword) {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'change_password',
                    currentPassword : currentPassword,
                    newPassword : newPassword,
                    duplicatedNewPassword : repeatedPassword
                },

                success : function (response) {
                    self._handleResponse(response);
                }
            }
        )
    };

    this._handleResponse = function (response) {
        let message = response.message;

        switch (response.status) {
            case "SUCCESS":
                self._showSuccessMessage(message);
                break;
            case "FAILURE":
                self._showErrorMessage(message);
                break;
        }
    };

    this._showSuccessMessage = function (message) {
        $("#changing-password-message").css('display', 'block');
        $("#changing-password-message").html("<span style = \"color:green\">" + message + "</span>");
    };

    this._showErrorMessage = function (message) {
        $("#changing-password-message").css('display', 'block');
        $("#changing-password-message").html("<span style = \"color:red\">" + message + "</span>");
    }
}