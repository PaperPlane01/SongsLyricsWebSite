function ArtistNameValidator() {
    this.result = ArtistNameValidationResults.SUCCESS;

    this.validateArtistName = function (artistName) {
        if (artistName.trim().length === 0) {
            this.result = ArtistNameValidationResults.EMPTY_NAME;
            return;
        }

        if (artistName.length > 50) {
            this.result = ArtistNameValidationResults.TOO_LONG;
            return;
        }

        this.result = ArtistNameValidationResults.SUCCESS;

    };
}

function ArtistNameValidatorView() {
    this.displayValidationResult = function (validationResult) {
        if (validationResult === ArtistNameValidationResults.SUCCESS) {
            $("#artist-name-message").css('display', 'none');
            return;
        }

        if (validationResult === ArtistNameValidationResults.EMPTY_NAME) {
            $("#artist-name-message").css('display', 'block');
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.artistname.empty", PageGlobals.currentLocale);
            this._showErrorMessage(message);
        }

        if (validationResult === ArtistNameValidationResults.TOO_LONG) {
            $("#artist-name-message").css('display', 'block');
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.artistname.toolong", PageGlobals.currentLocale);
            this._showErrorMessage(message);
        }
    };

    this._showErrorMessage = function(message) {
        $("#artist-name-message").html("<span style = \"color:red\">" + message + "</span>")
    }
}


let ArtistNameValidationResults =  {
    SUCCESS : 2,
    EMPTY_NAME : 4,
    TOO_LONG : 8
};

function SongNameValidator() {
    this.result = SongNameValidationResults.SUCCESS;

    this.validateSongName = function (songName) {
        if (songName.trim().length === 0) {
            this.result = SongNameValidationResults.EMPTY_SONG_NAME;
            return;
        }

        if (songName.length > 100) {
            this.result = SongNameValidationResults.TOO_LONG;
            return;
        }

        this.result = SongGenresValidationResults.SUCCESS;
    }
}

function SongNameValidatorView() {

    this.displayValidationResult = function (validationResult) {
        if (validationResult === SongNameValidationResults.SUCCESS) {
            $("#song-name-message").css('display', 'none');
            return;
        }

        if (validationResult === SongNameValidationResults.EMPTY_SONG_NAME) {
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.songname.empty", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }

        if (validationResult === SongNameValidationResults.TOO_LONG) {
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.songname.toolong", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }
    };

    this._showErrorMessage = function (message) {
        $("#song-name-message").css('display', 'block');
        $("#song-name-message").html("<span style = \"color:red\">" + message + "</span>")
    }
}

let SongNameValidationResults = {
    SUCCESS : 2,
    EMPTY_SONG_NAME : 4,
    TOO_LONG : 8
};

function FeaturedArtistsValidator() {
    this.result = FeaturedArtistsValidationResults.SUCCESS;

    this.validateFeaturedArtists = function (featuredArtists) {
        if (featuredArtists.length > 250) {
            this.result = FeaturedArtistsValidationResults.TOO_LONG;
        }

        this.result = FeaturedArtistsValidationResults.SUCCESS;
    }
}

function FeaturedArtistsValidatorView() {
    this.displayValidationResult = function (validationResult) {
        if (validationResult === FeaturedArtistsValidationResults.SUCCESS) {
            $("#featured-artists-message").css('display', 'none');
        }

        if (validationResult === FeaturedArtistsValidationResults.TOO_LONG) {
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.featuredartists.toolong");
            this._showErrorMessage(message);
        }
    };

    this._showErrorMessage = function (message) {
        $("#featured-artists-message").css('display', 'block');
        $("#featured-artists-message").html("<span style = \"color:red\">" + message + "</span>")
    }
}

let FeaturedArtistsValidationResults = {
    SUCCESS : 2,
    TOO_LONG : 4
};

function SongGenresValidator() {
    this.result = SongGenresValidationResults.SUCCESS;

    this.validateSongGenres = function (songGenres) {
        if (songGenres.length > 130) {
            this.result = SongNameValidationResults.TOO_LONG;
            return;
        }

        this.result = SongNameValidationResults.SUCCESS;
    }
}

function SongGenresValidatorView() {

    this.displayValidationResult = function (validationResult) {
        if (validationResult === SongGenresValidationResults.SUCCESS) {
            $("#song-genres-message").css('display', 'none');
            return;
        }

        if (validationResult === SongGenresValidationResults.TOO_LONG) {
            let message = PageGlobals.labelsManager.getLabelContent("labels.errors.songgenres.toolong", PageGlobals.currentLocale);
            this._showErrorMessage(message);
        }
    };

    this._showErrorMessage = function (message) {
        $("#song-genres-message").css('display', 'block');
        $("#song-genres-message").html("<span style = \"color:red\">" + message + "</span>");
    }
}

let SongGenresValidationResults = {
    SUCCESS : 2,
    TOO_LONG : 4
};

function YouTubeVideoIDValidator() {
    this.result = YouTubeVideoIDValidationResults.SUCCESS;

    this.validateYouTubeVideoID = function (youTubeVideoID) {
        if (youTubeVideoID.length > 15) {
            this.result = YouTubeVideoIDValidationResults.TOO_LONG;
            return;
        }

        this.result = YouTubeVideoIDValidationResults.SUCCESS;
    }
}

function YouTubeVideoIDValidatorView() {
    this.displayValidationResult = function (validationResult) {
        if (validationResult === YouTubeVideoIDValidationResults.SUCCESS) {
            $("#song-youtube-video-id-message").css('display', 'block');
        }

        if (validationResult === YouTubeVideoIDValidationResults.TOO_LONG) {
            $("#song-youtube-video-id-message").css('display', 'block');
            $("#song-youtube-video-id-message").html("<span style = \"color:red\">" + message + "</span>");
        }
    };

    this._showErrorMessage = function (message) {

    }
}

let YouTubeVideoIDValidationResults = {
    SUCCESS : 2,
    TOO_LONG : 4
};

function LyricsValidator() {
    this.result = LyricsValidationResults.SUCCESS;

    this.validateSongLyrics = function (songLyrics) {
        let numberOfClosingTags = 0;
        let numberOfOpeningTags = 0;
        let numberOfNestedTags = 0;

        if (songLyrics.trim().length === 0) {
            this.result = LyricsValidationResults.EMPTY;
            return;
        }

        if (songLyrics.trim().length > 5000) {
            this.result = LyricsValidationResults.TOO_LONG;
            return;
        }

        let lines = songLyrics.split("\n");

        for (let index = 0; index < lines.length; index++) {
            let line = lines[index];

            let nextLine = null;

            if ((index + 1) !== lines.length) {
                nextLine = lines[index + 1];
            }

            if ((line.charAt(0) === "[") && !Tags.isTag(line)) {
                this.result = LyricsValidationResults.UNSUPPORTED_TAG;
                return;
            }

            switch (line) {
                case Tags.VERSE:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.OUTRO:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.OTHER:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.INTRO:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.HOOK:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.CHORUS:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.BRIDGE:
                    numberOfOpeningTags++;
                    if (this._checkIfNextLineIsOpeningTag(nextLine)) {
                        numberOfNestedTags++
                    }
                    break;
                case Tags.CLOSING_TAG:
                    numberOfClosingTags++;
                    break;
            }
        }

        if (numberOfNestedTags !== 0) {
            this.result = LyricsValidationResults.NESTED_TAGS;
            return;
        }

        if (numberOfClosingTags !== numberOfOpeningTags) {
            this.result = LyricsValidationResults.UNCLOSED_TAGS;
            return;
        }

        this.result = LyricsValidationResults.SUCCESS;
    };

    this._checkIfNextLineIsOpeningTag = function (nextLine) {
        let result = false;

        if (nextLine !== null) {
            if (nextLine !== Tags.CLOSING_TAG) {
                if (Tags.isTag(nextLine)) {
                    result = true;
                }
            }
        }

        return result;
    };
}

function LyricsValidatorView() {
    this.displayValidationResult = function (validationResult) {
        let message = "";

        if (validationResult === LyricsValidationResults.SUCCESS) {
            $("#song-lyrics-message").css('display', 'none');
            console.log("Now I'm supposed to hide lyrics validation message!");
            return;
        }

        if (validationResult === LyricsValidationResults.EMPTY) {
            console.log('empty');
            message = PageGlobals.labelsManager.getLabelContent("labels.errors.songlyrics.empty", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }

        if (validationResult === LyricsValidationResults.TOO_LONG) {
            message = PageGlobals.labelsManager.getLabelContent("labels.errors.songlyric.toolong", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }

        if (validationResult === LyricsValidationResults.UNSUPPORTED_TAG) {
            message = PageGlobals.labelsManager.getLabelContent("labels.errors.songlyrics.unsupportedtags", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }

        if (validationResult === LyricsValidationResults.UNCLOSED_TAGS) {
            message = PageGlobals.labelsManager.getLabelContent("labels.errors.songlyrics.unclosedtags", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }

        if (validationResult === LyricsValidationResults.NESTED_TAGS) {
            message = PageGlobals.labelsManager.getLabelContent("labels.errors.songlyrics.nestedtags", PageGlobals.currentLocale);
            this._showErrorMessage(message);
            return;
        }
    };

    this._showErrorMessage = function (message) {
        $("#song-lyrics-message").css('display', 'block');
        $("#song-lyrics-message").html("<span style = \"color:red\">" + message + "</span>");
    }
}

function LyricsEditor() {
    this.addTag = function (tag) {
        if ($.trim($("#song-lyrics").val())) {
            $('#song-lyrics').val($('#song-lyrics').val() + '\n');
        }

        $("#song-lyrics").val($('#song-lyrics').val() + tag);
        $("#song-lyrics").val($('#song-lyrics').val() + '\n');
        $("#song-lyrics").val($('#song-lyrics').val() + '\n');
        $("#song-lyrics").val($('#song-lyrics').val() + '[/]');

        $("#song-lyrics").focus();

        $('html, body').animate({
            scrollTop: $("#song-lyrics").offset().top
        }, 2000);

        console.log('tag added', tag);
    }
}

let Tags = {
    CHORUS : "[CHORUS]",
    VERSE : "[VERSE]",
    INTRO : "[INTRO]",
    HOOK : "[HOOK]",
    OUTRO : "[OUTRO]",
    BRIDGE : "[BRIDGE]",
    OTHER : "[OTHER]",
    CLOSING_TAG : "[/]",

    isTag : function (value) {
        let result = false;

        Object.keys(Tags).forEach(function (key) {
            if (Tags[key] == value) {
                result = true;
            }
        });

        return result;
    }
};

let LyricsValidationResults  =  {
    UNCLOSED_TAGS : 2,
    EMPTY : 4,
    NESTED_TAGS : 8,
    TOO_LONG : 16,
    UNSUPPORTED_TAG : 32,
    SUCCESS : 64
};