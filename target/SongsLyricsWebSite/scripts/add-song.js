//This feature is swarming of bugs and needs to be updated ASAP!
$(document).ready(function () {
    let labelsManager = new LabelsManager();

    $("#add-bridge-button").on('click', function () {
        addTag(Tags.BRIDGE);
    });

    $("#add-chorus-button").on('click', function () {
        addTag(Tags.CHORUS);
    });

    $("#add-hook-button").on('click', function () {
        addTag(Tags.HOOK);
    });

    $("#add-intro-button").on('click', function () {
        addTag(Tags.INTRO);
    });

    $("#add-other-part").on('click', function () {
        addTag(Tags.OTHER);
    });

    $("#add-outro-button").on('click', function () {
        addTag(Tags.OUTRO);
    });

    $("#add-verse-button").on('click', function () {
        addTag(Tags.VERSE);
    });

    let artistName = "";
    let featuredArtists = "";
    let songName = "";
    let songGenres = "";
    let youTubeVideoID = "";
    let songLyrics = "";


    $("#artist-name").on('input', function () {
        artistName = $("#artist-name").val();

        console.log("artist name is being changed");

        if (artistName.trim().length == 0) {
            showArtistNameValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#artist-name-message").css('display', 'none');

            if (!validateArtistName(artistName)) {
                showArtistNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#artist-name-message").css('display', 'none');
            }
        }

    });

    $("#artist-name").on('focusout', function () {
        artistName = $("#artist-name").val();

        if (artistName.trim().length == 0) {
            showArtistNameValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#artist-name-message").css('display', 'none');

            if (!validateArtistName(artistName)) {
                showArtistNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#artist-name-message").css('display', 'none');
            }
        }
    });

    $("#featured-artists").on('input', function () {
        featuredArtists = $("#featured-artists").val();

        if (!validateFeaturedArtists(featuredArtists)) {
            showFeaturedArtistsValidationMessage();
        } else {
            $("#featured-artists-message").css('display', 'block');
        }
    });

    $("#featured-artists").on('focusout', function () {
        featuredArtists = $("#featured-artists").val();

        if (!validateFeaturedArtists(featuredArtists)) {
            showFeaturedArtistsValidationMessage();
        } else {
            $("#featured-artists-message").css('display', 'block');
        }
    });

    $("#song-name").on('input', function () {
        songName = $("#song-name").val();

        if (songName.trim().length == 0) {
            showSongNameValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#song-name-message").css('display', 'none');

            if (!validateSongName(songName)) {
                showSongNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#song-name-message").css('display', 'none');
            }
        }
    });

    $("#song-name").on('focusout', function () {
        songName = $("#song-name").val();

        if (songName.trim().length == 0) {
            showSongNameValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#song-name-message").css('display', 'none');

            if (!validateSongName(songName)) {
                showSongNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#song-name-message").css('display', 'none');
            }
        }
    });

    $("#song-genres").on('input', function () {
        songGenres = $("#song-genres").val();

        if (!validateGenres(songGenres)) {
            showGenresValidationMessage();
        } else {
            $("#song-genres-message").css('display', 'none');
        }
    });

    $("#song-genres").on("focusout", function () {
        songGenres = $("#song-genres").val();

        if (!validateGenres(songGenres)) {
            showGenresValidationMessage();
        } else {
            $("#song-genres-message").css('display', 'none');
        }
    });

    $("#youtube-video-id").on('input', function () {
        youTubeVideoID = $("#youtube-video-id").val();

        if (!validateYouTubeVideoID(youTubeVideoID)) {
            showYouTubeVideoIDValidationMessage();
        } else {
            $("#song-youtube-video-id-message").css('display', 'none');
        }
    });

    $("#youtube-video-id").on('focusout', function () {
        youTubeVideoID = $("#youtube-video-id").val();

        if (!validateYouTubeVideoID(youTubeVideoID)) {
            showYouTubeVideoIDValidationMessage();
        } else {
            $("#song-youtube-video-id-message").css('display', 'none');
        }
    });

    $("#song-lyrics").on('input', function () {
        songLyrics = $("#song-lyrics").val();

        if (songLyrics.trim().length == 0) {
            showSongLyricsValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#song-lyrics-message").css('display', 'none');
            if (!validateSongLyrics(songLyrics)) {
                showSongLyricsValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#song-lyrics-message").css('display', 'none');
            }
        }
    });

    $("#song-lyrics").on('focusout', function () {
        songLyrics = $("#song-lyrics").val();

        if (songLyrics.trim().length == 0) {
            showSongLyricsValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#song-lyrics-message").css('display', 'none');
            if (!validateSongLyrics(songLyrics)) {
                showSongLyricsValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#song-lyrics-message").css('display', 'none');
            }
        }
    });

    $("#song-lyrics").on('change', function () {
        songLyrics = $("#song-lyrics").val();

        if (songLyrics.trim().length == 0) {
            showSongLyricsValidationMessage(ValidationMessageParameters.EMPTY_FIELD);
        } else {
            $("#song-lyrics-message").css('display', 'none');
            if (!validateSongLyrics(songLyrics)) {
                showSongLyricsValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
            } else {
                $("#song-lyrics-message").css('display', 'none');
            }
        }
    });

    $("#add-song").on('click', function () {
        if (validateArtistName(artistName)) {
            $("#artist-name-message").css('display', 'none');
            if (validateFeaturedArtists(featuredArtists)) {
                $("#featured-artists-message").css('display', 'none');
                if (validateSongName(songName)) {
                    $("#song-name-message").css('display', 'none');
                    if (validateGenres(songGenres)) {
                        $("#song-genres-message").css('display', 'none');
                        if (validateYouTubeVideoID(youTubeVideoID)) {
                            $("#song-youtube-video-id-message").css('display', 'none');
                            if (validateSongLyrics(songLyrics)) {
                                sendSong(songName, artistName, featuredArtists, songGenres, songLyrics, youTubeVideoID);
                            } else {
                                showSongLyricsValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
                            }
                        } else {
                            showYouTubeVideoIDValidationMessage();
                        }
                    } else {
                        showGenresValidationMessage();
                    }
                } else {
                    showSongNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
                }
            } else {
                showFeaturedArtistsValidationMessage();
            }
        } else {
            showArtistNameValidationMessage(ValidationMessageParameters.VALIDATION_ERROR);
        }
    });

    let showArtistNameValidationMessage = function (messageParameter) {

        let message = "";

        switch (messageParameter){
            case ValidationMessageParameters.EMPTY_FIELD:
                message = labelsManager.getLabelContent("labels.errors.emptyartistname", PageGlobals.currentLocale);
                $("#artist-name-message").css('display', 'block');
                $("#artist-name-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case ValidationMessageParameters.VALIDATION_ERROR:
                message = labelsManager.getLabelContent("labels.errors.artistnamevalidation", PageGlobals.currentLocale);
                $("#artist-name-message").css('display', 'block');
                $("#artist-name-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            default:
                break;
        }
    };

    let showFeaturedArtistsValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.featuredartistsvalidation", PageGlobals.currentLocale);
        $("#featured-artists-message").css('display', 'block');
        $("#featured-artists-message").html("<span style = \"color:red\">" + message + "</span>");

    };

    let showSongNameValidationMessage = function (messageParameter) {

        let message = "";

        switch (messageParameter){
            case ValidationMessageParameters.EMPTY_FIELD:
                message = labelsManager.getLabelContent("labels.errors.emptysongname", PageGlobals.currentLocale);
                $("#song-name-message").css('display', 'block');
                $("#song-name-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case ValidationMessageParameters.VALIDATION_ERROR:
                message = labelsManager.getLabelContent("labels.errors.songnamevalidation", PageGlobals.currentLocale);
                $("#song-name-message").css('display', 'block');
                $("#song-name-message").html("<span style = \"color:red\">" + message + "</span>")
                break;
            default:
                break;
        }

    };

    let showGenresValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.songgenresvalidation", PageGlobals.currentLocale);
        $("#song-genres-message").css('display', 'block');
        $("#song-genres-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let showSongLyricsValidationMessage = function (messageParameter) {
        let message = "";

        switch (messageParameter) {
            case ValidationMessageParameters.EMPTY_FIELD:
                message = labelsManager.getLabelContent("labels.errors.emptylyrics", PageGlobals.currentLocale);
                $("#song-lyrics-message").css('display', 'block');
                $("#song-lyrics-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            case ValidationMessageParameters.VALIDATION_ERROR:
                message = labelsManager.getLabelContent("labels.errors.songlyricsvalidation", PageGlobals.currentLocale);
                $("#song-lyrics-message").css('display', 'block');
                $("#song-lyrics-message").html("<span style = \"color:red\">" + message + "</span>");
                break;
            default:
                break;
        }
    };
    
    let showYouTubeVideoIDValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.songyoutubevideoidvalidation", PageGlobals.currentLocale);
        $("#song-youtube-video-id-message").css('display', 'block');
        $("#song-youtube-video-id-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let addTag = function (tag) {
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

        console.log('tag added', tag)
    };

    let validateArtistName = function (artistName) {
        let result = false;

        if (artistName.length < 50) {
            result = true;
        }

        return result;
    };

    let validateFeaturedArtists = function (featuredAtists) {

        let result = false;

        if (featuredArtists.length < 250) {
            result = true;
        }

        return result;
    };

    let validateSongName = function (songName) {
        let result = false;

        if (songName.length < 100) {
            result = true;
        }

        return result;
    };
    
    let validateGenres = function (genres) {
        
        let result = false;
        
        if (genres.length < 130) {
            result = true;
        }
        
        return result;
    };
    
    let validateYouTubeVideoID = function (youTubeVideoID) {
        
        let result = false;
        
        if (youTubeVideoID.length < 15) {
            result = true;
        }
        
        return result;
    };
    
    let validateSongLyrics = function (songLyrics) {
        if (songLyrics.length > 5000) {
            console.log(songLyrics.length);
            return false;
        }

        let lines = songLyrics.split("\n");

        let result = false;

        let numberOfClosingTags = 0;
        let numberOfOpeningTags = 0;

        for (let index = 0; index < lines.length; index++) {
            let line = lines[index];

            if ((line.charAt(0) === "[") && !isTag(line)) {
                return false;
            }

            switch (line) {
                case Tags.VERSE:
                    numberOfOpeningTags++;
                    break;
                case Tags.OUTRO:
                    numberOfOpeningTags++;
                    break;
                case Tags.OTHER:
                    numberOfOpeningTags++;
                    break;
                case Tags.INTRO:
                    numberOfOpeningTags++;
                    break;
                case Tags.HOOK:
                    numberOfOpeningTags++;
                    break;
                case Tags.CHORUS:
                    numberOfOpeningTags++;
                    break;
                case Tags.BRIDGE:
                    numberOfOpeningTags++;
                    break;
                case Tags.CLOSING_TAG:
                    numberOfClosingTags++;
                    break;
            }
        }

        if (numberOfClosingTags !== numberOfOpeningTags) {
            result = false;
        } else {
            result = true;
        }

        return result;
    };

    let sendSong = function (songName, songArtist, songFeaturedArtists, songGenres, songLyrics, youTubeLink) {
      $.post(
          {
              url : 'controller',
              data : {
                  command : 'addsong',
                  songName : songName,
                  songArtist : songArtist,
                  songFeaturedArtists : songFeaturedArtists,
                  songGenres : songGenres,
                  songLyrics : songLyrics,
                  youTubeLink : youTubeLink
              },

              success : function (response) {
                  handleResponse(response);
              }
          }
      )
    };

    let handleResponse = function (response) {
        if (response.status == "FAILURE") {
            showFailureMessage(response.message);
        }

        if (response.status == "SUCCESS") {
            $(".song-adding-panel").html("<h3>" + response.message + "</h3>");
        }
    };

    let showFailureMessage = function (message) {
        $("#message").css('display', ' block');
        $("#message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let Tags = {
        CHORUS : "[CHORUS]",
        VERSE : "[VERSE]",
        INTRO : "[INTRO]",
        HOOK : "[HOOK]",
        OUTRO : "[OUTRO]",
        BRIDGE : "[BRIDGE]",
        OTHER : "[OTHER]",
        CLOSING_TAG : "[/]",
    };

    let isTag = function (value) {
        let result = false;

        Object.keys(Tags).forEach(function (key) {
            if (Tags[key] == value) {
                result = true;
            }
        });

        return result;
    };

    let ValidationMessageParameters = {
        EMPTY_FIELD  : 4,
        VALIDATION_ERROR : 8
    };
});