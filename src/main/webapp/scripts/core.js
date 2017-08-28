$(document).ready(function () {
    PageGlobals.currentLocale = $("html").attr('lang');
    $('.selectpicker').selectpicker();

    let loginManager = new LoginManager();
    let logoutManager = new LogoutManager();
    let quickSongAccessManager = new QuickSongAccessManager();
    let languageManager = new LanguageManager();
    let pageElementsManager = new PageElementsManager();

    quickSongAccessManager.loadArtistsLetters();

    $("#login-button").on('click', function () {
        let username = $("#username").val();
        let password = $("#password").val();

        if (username == "") {
            alert("Username is required!");
            return;
        }
        if (password == "") {
            alert("Password is required!");
            return;
        }

        loginManager.doLogin(username, password);
    });

    $(".logout").on('click', function () {
        logoutManager.doLogout();
    });

    $("#artist-letter-select").on('change', function () {
        let selectedLetter = $(this).find("option:selected").val();
        quickSongAccessManager.loadArtistsByLetter(selectedLetter);
        pageElementsManager.enableArtistSelection();
    });

    $("#artist-name-select").on('change', function () {
        let selectedArtist = $(this).find("option:selected").val();
        quickSongAccessManager.loadSongsOfArtist(selectedArtist);
        pageElementsManager.enableSongSelection();
    });

    $("#song-name-select").on('change', function () {
        let selectedSong = $(this).find("option:selected").val();
        let selectedSongID = PageGlobals.getSelectedSongID(selectedSong);
        $("#go-to-song-button").attr('href', '/controller?command=song&songID=' + selectedSongID);

        pageElementsManager.enableGoToSongButton();
    });

    $("#english-language-select").on('click', function () {
        let locale = "en_US";
        languageManager.changeLanguage(locale);
    });

    $("#russian-language-select").on('click', function () {
        let locale = "ru_RU";
        languageManager.changeLanguage(locale);
    });

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
                        $("#message").css('display', 'block');
                        self.handleLoginResult(responseData);
                    }
                }
            )
        };

        this.handleLoginResult = function (responseData) {
            let message = responseData.message;

            if (responseData.status === 'SUCCESS'){
                $('#message').html("<span style = \"color:green\">" + message + "</span>");
                location.reload();
            } else if (responseData.status === 'FAILURE'){
                console.log('failure!');
                let reason = responseData.reason;
                $('#message').html("<span style = \"color:red\">" + message + ": " + reason + "</span>")
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

    function QuickSongAccessManager  () {
        let self = this;

        this.loadArtistsLetters = function () {
            $.post(
                {
                    url : 'controller',
                    data : {
                        command : 'artistsletters'
                    },

                    success : function (artistsLetters) {
                        self.displayArtistsLetters(artistsLetters);
                    }
                }
            )
        };

        this.displayArtistsLetters = function (artistsLetters) {
            let element = document.getElementById("artist-letter-select");

            let label = PageGlobals.labelsManager.getLabelContent("labels.chooseartistletter", PageGlobals.locale);

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

                    success : function (artists) {
                        self.displayArtists(artists);
                    }
                }
            )
        };

        this.displayArtists = function (artists) {
            let element = document.getElementById("artist-name-select");

            let label = PageGlobals.labelsManager.getLabelContent("labels.chooseartistname", PageGlobals.locale);

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

                    success : function (responseData) {
                        self.displaySongs(responseData);
                    }
                }
            )
        };

        this.displaySongs = function (songs) {
            let element = document.getElementById("song-name-select");

            PageGlobals.songs.length = 0;

            let label = PageGlobals.labelsManager.getLabelContent("labels.choosesong", PageGlobals.locale);

            element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

            $.each(songs, function (index, song) {
                $("#song-name-select").append("<option id=\"" + song.ID + "\">" + song.name + "</option>");
                PageGlobals.songs.push(song);
            });

            $('.selectpicker').selectpicker('refresh');
        };
    }

    function LanguageManager () {
        this.changeLanguage = function (locale) {
            $.post(
                {
                    url : 'controller',
                    data : {
                        command : "changelanguage",
                        language : locale
                    },

                    success : function () {
                        location.reload();
                    }
                }
            )
        }
    }

    function PageElementsManager  () {
        this.enableArtistSelection = function () {
            $('#artist-name-select').removeAttr('disabled');
            $('.selectpicker').selectpicker('refresh');
        };

        this.enableSongSelection = function () {
            $('#song-name-select').removeAttr('disabled');
            $('.selectpicker').selectpicker('refresh');
        };

        this.enableGoToSongButton = function () {
            $('#go-to-song-button').removeAttr('disabled');
            $('.selectpicker').selectpicker('refresh');
        }
    }


});

PageGlobals = {
    songs : [],
    currentLocale : "",
    labelsManager : new LabelsManager(),

    getSelectedSongID : function (selectedSongName) {
        var songID;

        for (var index = 0; index < this.songs.length; index++) {
            if (this.songs[index].name === selectedSongName) {
                songID = this.songs[index].ID;
            }
        }

        return songID;
    },

};

function LabelsManager() {
    this.keys = [];
    this.labels = [];

    let self = this;

    this.getLabelContent = function (key, locale) {
        let label;

        if (self.isKeyReceived(key)) {
            label = self.getStoredLabelByKey(key);
        } else {
            self.receiveLabel(key, locale);
            console.log("receiving label");
            label = self.getStoredLabelByKey(key);
        }

        return label.content;
    };

    this.getStoredLabelByKey = function (key) {
        let label;

        for (var index = 0; index < self.labels.length; index++) {
            if (self.labels[index].key === key) {
                label = self.labels[index];
                console.log('label found!');
                break;
            }
        }

        return label;
    };

    this.receiveLabel = function (key, locale) {
        self.keys.push(key);

        let label;

        $.ajax(
            {
                url : 'controller',
                async : false,
                data : {
                    command : 'getlabel',
                    labelKey : key,
                    locale : locale
                },

                success : function (responseData) {
                    label = new Label(key, responseData);
                    self.labels.push(label);
                }
            }
        )
    };

    this.isKeyReceived = function (key) {
        let result = false;

        for (var index = 0; index < self.keys.length; index++) {
            if (key === self.keys[index]) {
                result = true;
                break;
            }
        }

        return result;
    };

    function Label(key, content) {
        this.key = key;
        this.content = content;
    }
}



