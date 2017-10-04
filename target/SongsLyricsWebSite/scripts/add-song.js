//Most bugs are fixed, though code still needs to be refactored.
$(document).ready(function () {
    let lyricsEditor = new LyricsEditor();
    let lyricsValidator = new LyricsValidator();
    let lyricsValidatorView = new LyricsValidatorView();
    let artistNameValidator = new ArtistNameValidator();
    let artistNameValidatorView = new ArtistNameValidatorView();
    let songNameValidator = new SongNameValidator();
    let songNameValidatorView = new SongNameValidatorView();
    let featuredArtistsValidator = new FeaturedArtistsValidator();
    let featuredArtistsValidatorView = new FeaturedArtistsValidatorView();
    let songGenresValidator = new SongGenresValidator();
    let songGenresValidatorView = new SongGenresValidatorView();
    let youTubeVideoIDValidator = new YouTubeVideoIDValidator();
    let youTubeVideoIDValidatorView = new YouTubeVideoIDValidatorView();

    let artistName = "";
    let featuredArtists = "";
    let songName = "";
    let songGenres = "";
    let youTubeVideoID = "";
    let songLyrics = "";

    $("#add-bridge-button").on('click', function () {
        lyricsEditor.addTag(Tags.BRIDGE);
    });

    $("#add-chorus-button").on('click', function () {
        lyricsEditor.addTag(Tags.CHORUS);
    });

    $("#add-hook-button").on('click', function () {
        lyricsEditor.addTag(Tags.HOOK);
    });

    $("#add-intro-button").on('click', function () {
        lyricsEditor.addTag(Tags.INTRO);
    });

    $("#add-other-part").on('click', function () {
        lyricsEditor.addTag(Tags.OTHER);
    });

    $("#add-outro-button").on('click', function () {
        lyricsEditor.addTag(Tags.OUTRO);
    });

    $("#add-verse-button").on('click', function () {
        lyricsEditor.addTag(Tags.VERSE);
    });


    $("#artist-name").on('input', function () {
        artistName = $("#artist-name").val();

        artistNameValidator.validateArtistName(artistName);

        artistNameValidatorView.displayValidationResult(artistNameValidator.result);
    });

    $("#artist-name").on('focusout', function () {
        artistName = $("#artist-name").val();

        artistNameValidator.validateArtistName(artistName);

        artistNameValidatorView.displayValidationResult(artistNameValidator.result);
    });

    $("#featured-artists").on('input', function () {
        featuredArtists = $("#featured-artists").val();

        featuredArtistsValidator.validateFeaturedArtists(featuredArtists);

        featuredArtistsValidatorView.displayValidationResult(featuredArtistsValidator.result);
    });

    $("#featured-artists").on('focusout', function () {
        featuredArtists = $("#featured-artists").val();

        featuredArtistsValidator.validateFeaturedArtists(featuredArtists);

        featuredArtistsValidatorView.displayValidationResult(featuredArtistsValidator.result);
    });

    $("#song-name").on('input', function () {
        songName = $("#song-name").val();

        songNameValidator.validateSongName(songName);

        songNameValidatorView.displayValidationResult(songNameValidator.result);
    });

    $("#song-name").on('focusout', function () {
        songName = $("#song-name").val();

        songNameValidator.validateSongName(songName);

        songNameValidatorView.displayValidationResult(songNameValidator.result);
    });

    $("#song-genres").on('input', function () {
        songGenres = $("#song-genres").val();

        songGenresValidator.validateSongGenres(songGenres);

        songGenresValidatorView.displayValidationResult(songGenresValidator.result);
    });

    $("#song-genres").on("focusout", function () {
        songGenres = $("#song-genres").val();

        songGenresValidator.validateSongGenres(songGenres);

        songGenresValidatorView.displayValidationResult(songGenresValidator.result);
    });

    $("#youtube-video-id").on('input', function () {
        youTubeVideoID = $("#youtube-video-id").val();

        youTubeVideoIDValidator.validateYouTubeVideoID(youTubeVideoID);

        youTubeVideoIDValidatorView.displayValidationResult(youTubeVideoIDValidator.result);
    });

    $("#youtube-video-id").on('focusout', function () {
        youTubeVideoID = $("#youtube-video-id").val();

        youTubeVideoIDValidator.validateYouTubeVideoID(youTubeVideoID);

        youTubeVideoIDValidatorView.displayValidationResult(youTubeVideoIDValidator.result);
    });


    $("#song-lyrics").on('input', function () {
        songLyrics = $("#song-lyrics").val();

        lyricsValidator.validateSongLyrics(songLyrics);

        lyricsValidatorView.displayValidationResult(lyricsValidator.result);

    });

    $("#song-lyrics").on('focusout', function () {
        songLyrics = $("#song-lyrics").val();

        lyricsValidator.validateSongLyrics(songLyrics);

        lyricsValidatorView.displayValidationResult(lyricsValidator.result);
    });

    $("#song-lyrics").on('change', function () {
        songLyrics = $("#song-lyrics").val();

        lyricsValidator.validateSongLyrics(songLyrics);

        lyricsValidatorView.displayValidationResult(lyricsValidator.result);
    });
    
    $("#add-song").on('click', function () {
        if (validateInputs()) {
            sendSong();
        }
    });
    
    let validateInputs = function () {
        if (artistNameValidator.result !== ArtistNameValidationResults.SUCCESS) {
            artistNameValidatorView.displayValidationResult(artistNameValidator.result);
            return false;
        }
        
        if (songNameValidator.result !== SongGenresValidationResults.SUCCESS) {
            songNameValidatorView.displayValidationResult(songNameValidator.result);
            return false;
        }
        
        if (songGenresValidator.result !== SongGenresValidationResults.SUCCESS) {
            songGenresValidator.displayValidationResult(songGenresValidator.result);
            return false;
        }
        
        if (youTubeVideoIDValidator.result !== YouTubeVideoIDValidationResults.SUCCESS) {
            youTubeVideoIDValidatorView.displayValidationResult(youTubeVideoIDValidator.result);
            return false;
        }
        
        if (featuredArtistsValidator.result !== FeaturedArtistsValidationResults.SUCCESS) {
            featuredArtistsValidatorView.displayValidationResult(featuredArtistsValidator.result);
            return false;
        }
        
        if (lyricsValidator.result !== LyricsValidationResults.SUCCESS) {
            lyricsValidatorView.displayValidationResult(lyricsValidator.result);
            return false;
        }
        
        return true;
    };
    
    let sendSong = function () {
        $.post(
            {
                url : "controller",
                data : {
                    command : "add_song",
                    songName : songName,
                    songArtist : artistName,
                    featuredArtists : featuredArtists,
                    songGenres : songGenres,
                    youTubeVideoID : youTubeVideoID,
                    songLyrics : songLyrics
                },
                
                success : function (response) {
                    handleResponse(response);
                }
            }
        )
    };

    let handleResponse = function (response) {
        if (response.status === "SUCCESS") {
            $("#song-adding-panel").html("<h3>" + response.message + "</h3>");
        }

        if (response.status === "FAILURE") {
            $("#message").css('display', 'block');
            $("#message").html("<span style = \"color:red\">" + response.message + "</span>");
        }
    }
});

