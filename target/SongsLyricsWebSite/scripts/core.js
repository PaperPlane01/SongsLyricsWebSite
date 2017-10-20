$(document).ready(function () {
    PageGlobals.currentLocale = $("html").attr('lang');
    $('.selectpicker').selectpicker();

    let loginManager = new LoginManager();
    let logoutManager = new LogoutManager();
    let quickSongAccessManager = new QuickSongAccessManager();
    let languageManager = new LanguageManager();
    let signUpValidator = new SignUpValidator();
    let signUpValidatorView = new SignUpValidatorView();
    let signUpManager = new SignUpManager();

    quickSongAccessManager.loadArtistsLetters();

    $("#login-button").on('click', function () {
        let username = $("#username").val();
        let password = $("#password").val();

        if (username === "") {
            let message = $("#empty-user-name").html();
            $('#login-message').html("<span style = \"color:red\">" + message + "</span>")
            return;
        }
        if (password === "") {
            let message = $("#empty-password").html();
            $('#login-message').html("<span style = \"color:red\">" + message + "</span>")
            return;
        }

        loginManager.doLogin(username, password);
    });

    $("#logout").on('click', function () {
        logoutManager.doLogout();
    });

    $("#artist-letter-select").on('change', function () {
        let selectedLetter = $(this).find("option:selected").val();
        quickSongAccessManager.loadArtistsByLetter(selectedLetter);
        quickSongAccessManager.enableArtistSelection();
    });

    $("#artist-name-select").on('change', function () {
        let selectedArtist = $(this).find("option:selected").val();
        quickSongAccessManager.loadSongsOfArtist(selectedArtist);
        quickSongAccessManager.enableSongSelection();
    });

    $("#song-name-select").on('change', function () {
        quickSongAccessManager.enableGoToSongButton();
    });

    $("#go-to-song-button").on('click', function () {
        let selectedSong = $("#song-name-select").find("option:selected").val();
        let selectedSongID = quickSongAccessManager.getSelectedSongID(selectedSong);

        if (!$(this).is('disabled')) {
            let newHref = window.location.origin + window.location.pathname;
            if (window.location.pathname.indexOf("/controller") === -1) {
                newHref += "/controller";
                newHref += "?command=song&songID=" + selectedSongID;
            } else {
                newHref += "?command=song&songID=" + selectedSongID;
            }

            window.location.href = newHref;
        }
    });

    $("#english-language-select").on('click', function () {
        let locale = "en_US";
        languageManager.changeLanguage(locale);
    });

    $("#russian-language-select").on('click', function () {
        let locale = "ru_RU";
        languageManager.changeLanguage(locale);
    });

    $("#username-for-signup").on('input', function () {
        let userName = $("#username-for-signup").val();
        signUpValidator.validateUserName(userName);
        signUpValidatorView.displayUserNameValidationResult(signUpValidator.userNameValidationResult);
    });

    $("#username-for-signup").on('focusout', function () {
        let userName = $("#username-for-signup").val();
        signUpValidator.validateUserName(userName);
        signUpValidatorView.displayUserNameValidationResult(signUpValidator.userNameValidationResult);
    });

    $("#password-for-sign-up").on('input', function () {
        let password = $("#password-for-sign-up").val();
        signUpValidator.validatePassword(password);
        signUpValidatorView.displayPasswordValidationResult(signUpValidator.passwordValidationResult);
    });

    $("#password-for-sign-up").on('focusout', function () {
        let password = $("#password-for-sign-up").val();
        signUpValidator.validatePassword(password);
        signUpValidatorView.displayPasswordValidationResult(signUpValidator.passwordValidationResult);
    });

    $("#repeat-password-for-sign-up").on('input', function () {
        let password = $("#password-for-sign-up").val();
        let secondPassword = $("#repeat-password-for-sign-up").val();
        signUpValidator.validateSecondPassword(password, secondPassword);
        signUpValidatorView.displaySecondPasswordValidationResult(signUpValidator.secondPasswordValidationResult);
    });

    $("#repeat-password-for-sign-up").on('focusout', function () {
        let password = $("#password-for-sign-up").val();
        let secondPassword = $("#repeat-password-for-sign-up").val();
        signUpValidator.validateSecondPassword(password, secondPassword);
        signUpValidatorView.displaySecondPasswordValidationResult(signUpValidator.secondPasswordValidationResult);
    });

    $("#sign-up-button").on('click', function () {
        let userName = $("#username-for-signup").val();
        let password = $("#password-for-sign-up").val();
        let secondPassword = $("#repeat-password-for-sign-up").val();

        if (signUpValidator.validateInputs(userName, password, secondPassword)) {
            signUpValidatorView.hideErrorMessage();
            signUpManager.register(userName, password, secondPassword);
        } else {
            signUpValidatorView.showErrorMessage();
        }
    });
});

PageGlobals = {
    currentLocale : "",
};

function LoginManager  () {
    let self = this;

    this.doLogin = function (username, password) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'login',
                    username : username,
                    password : password
                },

                success : function (responseData) {
                    self.handleLoginResult(responseData);
                }
            }
        )
    };

    this.handleLoginResult = function (responseData) {
        let message = responseData.message;

        $("#login-message").css('display', 'block');

        if (responseData.status === 'SUCCESS'){
            $('#login-message').html("<span style = \"color:green\">" + message + "</span>");
            location.reload();
        } else if (responseData.status === 'FAILURE'){
            let reason = responseData.reason;
            $('#login-message').html("<span style = \"color:red\">" + message + ": " + reason + "</span>")
        }
    }
}

function LogoutManager () {
    this.doLogout = function () {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'logout'
                },

                success : function () {
                    location.reload();
                }
            }
        )
    }
}

function SignUpValidator() {
    this.result = 0;
    this.userNameValidationResult = 0;
    this.passwordValidationResult = 0;
    this.secondPasswordValidationResult = 0;

    let self = this;

    this.validateUserName = function (userName) {
        if (userName.length === 0) {
            this.userNameValidationResult = SignUpValidationResults.EMPTY_USERNAME;
            return;
        }

        if (userName.length < 3) {
            this.userNameValidationResult = SignUpValidationResults.TOO_SHORT_USERNAME;
            return;
        }

        if (userName.length > 25) {
            this.userNameValidationResult = SignUpValidationResults.TOO_LONG_USERNAME;
            return;
        }

        if (!this._isUserNameValid(userName)) {
            this.userNameValidationResult = SignUpValidationResults.INVALID_USERNAME;
            return;
        }

        this.userNameValidationResult = SignUpValidationResults.USERNAME_SUCCESS;
        console.log("success!");

    };

    this.validatePassword = function (password) {
        if (password.length === 0) {
            this.passwordValidationResult = SignUpValidationResults.EMPTY_PASSWORD;
            return;
        }

        if (password.length < 5) {
            this.passwordValidationResult = SignUpValidationResults.TOO_SHORT_PASSWORD;
            return;
        }

        if (password.length > 20) {
            this.passwordValidationResult = SignUpValidationResults.TOO_LONG_PASSWORD;
            return;
        }

        if (!this._isPasswordValid(password)) {
            this.passwordValidationResult = SignUpValidationResults.INVALID_PASSWORD;
            return;
        }

        this.passwordValidationResult = SignUpValidationResults.PASSWORD_SUCCESS;
    };

    this.validateSecondPassword = function (password, secondPassword) {
        if (password !== secondPassword) {
            this.secondPasswordValidationResult = SignUpValidationResults.PASSWORDS_ARENT_EQUAL;
        } else {
            this.secondPasswordValidationResult = SignUpValidationResults.SECOND_PASSWORD_SUCCESS;
        }
    };

    this._isUserNameValid = function (userName) {
        let regExp = new RegExp("[a-zA-Zа-яА-Я_.#$%^&]*");
        return regExp.test(userName);
    };

    this._isPasswordValid = function (password) {
        let regExp = new RegExp("[a-zA-Zа-яА-Я_.#$%^&]*");
        return regExp.test(password);
    };

    this.validateInputs = function (userName, password, secondPassword) {
        self.validateUserName(userName);
        self.validatePassword(password);
        self.validateSecondPassword(password, secondPassword);
        return (self.userNameValidationResult === SignUpValidationResults.USERNAME_SUCCESS)
            && (self.passwordValidationResult === SignUpValidationResults.PASSWORD_SUCCESS)
            && (self.secondPasswordValidationResult === SignUpValidationResults.SECOND_PASSWORD_SUCCESS);
    }
}

function SignUpValidatorView() {
    this.displayUserNameValidationResult = function (result) {
        if (result === SignUpValidationResults.USERNAME_SUCCESS) {
            $("#username-message").css('display', 'none');
            return;
        }

        if (result === SignUpValidationResults.EMPTY_USERNAME) {
            $("#username-message").css('display', 'block');
            let message = $("#empty-user-name").html();
            $("#username-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.TOO_SHORT_USERNAME) {
            $("#username-message").css('display', 'block');
            let message = $("#too-short-user-name").html();
            $("username-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.TOO_LONG_USERNAME) {
            $("#username-message").css('display', 'block');
            let message = $("#too-long-username").html();
            $("#username-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.INVALID_USERNAME) {
            $("#username-message").css('display', 'block');
            let message = $("#invalid-username").html();
            $("#username-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }
    };

    this.displayPasswordValidationResult = function (result) {
        if (result === SignUpValidationResults.PASSWORD_SUCCESS) {
            $("#password-message").css('display', 'none');
            return;
        }

        if (result === SignUpValidationResults.EMPTY_PASSWORD) {
            $("#password-message").css('display', 'block');
            let message = $("#empty-password").html();
            $("#password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.TOO_SHORT_PASSWORD) {
            $("#password-message").css('display', 'block');
            let message = $("#too-short-password").html();
            $("#password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.TOO_LONG_PASSWORD) {
            $("#password-message").css('display', 'block');
            let message = $("#too-long-password").html();
            $("#password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }

        if (result === SignUpValidationResults.INVALID_PASSWORD) {
            $("#password-message").css('display', 'block');
            let message = $("#invalid-password").html();
            $("#password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }
    };

    this.displaySecondPasswordValidationResult = function (result) {
        if (result === SignUpValidationResults.SECOND_PASSWORD_SUCCESS) {
            $("#second-password-message").css('display', 'none');
            return;
        }

        if (result === SignUpValidationResults.PASSWORDS_ARENT_EQUAL) {
            $("#second-password-message").css('display', 'block');
            let message = $("#passwords-are-not-equal").html();
            $("#second-password-message").html("<span style = \"color:red\">" + message + "</span>");
            return;
        }
    };

    this.showErrorMessage = function () {
        $("#sign-up-message").css('display', 'block');
        let message = $("#invalid-fields").html();
        $("#sign-up-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    this.hideErrorMessage = function () {
        $("#sign-up-message").css('display', 'none');
    }
}

let SignUpValidationResults = {
    SUCCESS : 1,
    EMPTY_USERNAME : 2,
    TOO_SHORT_USERNAME : 3,
    TOO_LONG_USERNAME : 4,
    INVALID_USERNAME : 5,
    EMPTY_PASSWORD : 6,
    TOO_SHORT_PASSWORD : 7,
    TOO_LONG_PASSWORD : 8,
    INVALID_PASSWORD : 9,
    PASSWORDS_ARENT_EQUAL : 10,
    USERNAME_SUCCESS : 11,
    PASSWORD_SUCCESS: 12,
    SECOND_PASSWORD_SUCCESS : 13,
};

function SignUpManager() {
    let self = this;

    this.register = function (userName, password, secondPassword) {
        $.post(
            {
                url : 'controller',
                data: {
                    command : 'register',
                    username : userName,
                    password : password,
                    secondPassword : secondPassword
                },

                success : function (responseData) {
                    self._handleSignUpResults(responseData);
                }

            }
        )
    };

    this._handleSignUpResults = function (responseData) {
        let message = responseData.message;

        if (responseData.status === "SUCCESS") {
            $("#sign-up-message").html("<span style = \"color:green\">" + message + "</span>");
        }

        if (responseData.status === "FAILURE") {
            $('#sign-up-message').html("<span style = \"color:red\">" + message + "</span>")
        }
    }
}

function QuickSongAccessManager  () {
    this.songs = [];

    let self = this;

    this.loadArtistsLetters = function () {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'artists_letters'
                },

                success : function (response) {
                    switch (response.status) {
                        case "SUCCESS":
                            self._hideErrorMessage();
                            self.displayArtistsLetters(response.data);
                            break;
                        case "FAILURE":
                            self._displayErrorMessage(response.message);
                            break;
                    }
                }
            }
        )
    };

    this.displayArtistsLetters = function (artistsLetters) {
        let element = document.getElementById("artist-letter-select");

        let label = $("#choose-artist-letter-message").html();

        element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

        $.each(artistsLetters, function (index, letter) {
            $('#artist-letter-select').append("<option>" + letter + "</option>");
        });

        $('.selectpicker').selectpicker('refresh');

    };

    this.loadArtistsByLetter = function (letter) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : "artists",
                    letter : letter
                },

                success : function (response) {
                    switch (response.status) {
                        case "SUCCESS":
                            self._hideErrorMessage();
                            self.displayArtists(response.data);
                            break;
                        case "FAILURE":
                            self._displayErrorMessage(response.message);
                            break
                    }
                }
            }
        )
    };

    this.displayArtists = function (artists) {
        let element = document.getElementById("artist-name-select");

        let label = $("#choose-artist-name-message").html();

        element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

        $.each(artists, function (index, artist) {
            $("#artist-name-select").append("<option>" + artist.name + "</option>");
        });

        $('.selectpicker').selectpicker('refresh');

    };

    this.loadSongsOfArtist = function (artistName) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : "songs",
                    artistName : artistName
                },

                success : function (response) {
                    switch (response.status) {
                        case "SUCCESS":
                            self._hideErrorMessage();
                            self.displaySongs(response.data);
                            break;
                        case "FAILURE":
                            self._displayErrorMessage(response.message);
                            break;
                    }
                }
            }
        )
    };

    this.displaySongs = function (songs) {
        let element = document.getElementById("song-name-select");

        let self = this;

        this.songs.length = 0;

        let label = $("#choose-song-message").html();

        element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

        $.each(songs, function (index, song) {
            $("#song-name-select").append("<option id=\"" + song.id + "\">" + song.name + "</option>");
            self.songs.push(song);
        });

        $('.selectpicker').selectpicker('refresh');

    };

    this.getSelectedSongID = function (songName) {
        let songID;

        for (let index = 0; index < this.songs.length; index++) {
            if (this.songs[index].name === songName) {
                songID = this.songs[index].id;
                break;
            }
        }

        return songID;
    };

    this.enableArtistSelection = function () {
        $('#artist-name-select').removeAttr('disabled');
    };

    this.enableSongSelection = function () {
        $('#song-name-select').removeAttr('disabled');
    };

    this.enableGoToSongButton = function () {
        $('#go-to-song-button').removeAttr('disabled');
    };

    this._displayErrorMessage = function (message) {
        $("#quick-song-access-message").css('display', 'block');
        $("#quick-song-access-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    this._hideErrorMessage = function () {
        $("#quick-song-access-message").css('display', 'none');
    }
}

function LanguageManager () {
    this.changeLanguage = function (locale) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : "change_language",
                    language : locale
                },

                success : function () {
                    location.reload();
                }
            }
        )
    }
}