function showLoginResult(responseData) {

    var message = responseData.message;

    if (responseData.status === 'SUCCESS'){
        $('#message').html("<span style = \"color:green\">" + message + "</span>");
        location.reload();
    } else if (responseData.status === 'FAILURE'){
        var reason = responseData.reason;
        $('#message').html("<span style = \"color:red\">" + message + ": " + reason + "</span>")
    }
}

function displayArtistsLetters(responseData) {
    var element = document.getElementById("artist-letter-select");

    var label = "Choose artist letter";

    if (PageGlobals.currentLocale == "ru_RU") {
        label = "Выберите букву исполнителя";
    }
    element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

    $.each(responseData, function (index, letter) {
        $('#artist-letter-select').append("<option>" + letter + "</option>");
    });

    $('.selectpicker').selectpicker('refresh');
}

function displayArtists(responseData) {
    var element = document.getElementById("artist-name-select");

    var label = "Choose artist name";

    if (PageGlobals.currentLocale == "ru_RU") {
        label = "Выберите исполнителя";
    }

    element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

    $.each(responseData, function (index, artist) {
        $("#artist-name-select").append("<option>" + artist.name + "</option>");
    });


    $('.selectpicker').selectpicker('refresh');
}

function displaySongs(responseData) {
    var element = document.getElementById("song-name-select");

    songs = [];

    var label = "Choose song";

    if (PageGlobals.currentLocale == "ru_RU") {
        label = "Выберите песню";
    }

    element.innerHTML = "<option id=\"choose-artist-name\" data-hidden=\"true\">" + label + "</option>";

    $.each(responseData, function (index, song) {
        $("#song-name-select").append("<option id=\"" + song.ID + "\">" + song.name + "</option>");
        PageGlobals.songs.push(song);
    });

    $('.selectpicker').selectpicker('refresh');
}

function enableArtistSelection() {
    $('#artist-name-select').removeAttr('disabled');
    $('.selectpicker').selectpicker('refresh');
}

function enableSongSelection() {
    $('#song-name-select').removeAttr('disabled');
    $('.selectpicker').selectpicker('refresh');
}

function disableSongSelection() {
    $('#song-name-select').attr('disabled');
    $('.selectpicker').selectpicker('refresh');
}

function enableGoToSongButton() {
    $("#go-to-song-button").removeAttr('disabled');
    $('.selectpicker').selectpicker('refresh');
}

function disableGoToSongButton() {
    $("#go-to-song-button").attr('disabled');
    $('.selectpicker').selectpicker('refresh');
}

function getCurrentSongID(songName) {
    var songID;

    for (var index = 0; index < PageGlobals.songs.length; index++) {
        if (PageGlobals.songs[index].name = songName) {
            songID = PageGlobals.songs[index].ID;
        }
    }

    return songID;
}