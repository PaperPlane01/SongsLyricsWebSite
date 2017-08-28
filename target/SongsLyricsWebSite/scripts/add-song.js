//This feature is swarming of bugs and needs to be updated ASAP!
$(document).ready(function () {
    let labelsManager = new LabelsManager();
    let pageGlobals = new PageGlobals();

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

    $("#artist-name").on('change', function () {
        artistName = $("#artist-name").val();
    });

    $("#featured-artists").on('change', function () {
        featuredArtists = $("#featured-artists").val();
    });

    $("#song-name").on('change', function () {
        songName = $("#song-name").val();
    });

    $("#song-genres").on('change', function () {
        songGenres = $("#song-genres").val();
    });

    $("#youtube-video-id").on('change', function () {
        youTubeVideoID = $("#song-youtube-video-id-message").val();
    });

    $("#song-lyrics").on('change', function () {
        songLyrics = $("#song-lyrics").val();
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
                                showSongLyricsValidationMessage();
                            }
                        } else {
                            showYouTubeVideoIDValidationMessage();
                        }
                    } else {
                        showGenresValidationMessage();
                    }
                } else {
                    showSongNameValidationMessage();
                }
            } else {
                showFeaturedArtistsValidationMessage();
            }
        } else {
            showArtistNameValidationMessage();
        }
    });

    let showArtistNameValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.artistnamevalidation", pageGlobals.currentLocale);
        $("#artist-name-message").css('display', 'block');
        $("#artist-name-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let showFeaturedArtistsValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.featuredartistsvalidation", pageGlobals.currentLocale);
        $("#featured-artists-message").css('display', 'block');
        $("#featured-artists-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let showSongNameValidationMessage = function () {
      let message = labelsManager.getLabelContent("labels.errors.songnamevalidation", pageGlobals.currentLocale);
      $("#song-name-message").css('display', 'block');
      $("#song-name-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let showGenresValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.songgenresvalidation", pageGlobals.currentLocale);
        $("#song-name-message").css('display', 'block');
        $("#song-name-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    let showSongLyricsValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.songlyricsvalidation", pageGlobals.currentLocale);
        $("#song-lyrics-message").css('display', 'block');
        $("#song-lyrics-message").html("<span style = \"color:red\">" + message + "</span>");
    };
    
    let showYouTubeVideoIDValidationMessage = function () {
        let message = labelsManager.getLabelContent("labels.errors.songyoutubevideoidvalidation", pageGlobals.currentLocale);
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

              success : function (errorMessage) {
                  showMessage(errorMessage);
              }
          }
      )
    };

    let showMessage = function (message) {
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
    }
});