$(document).ready(function () {
    PageGlobals.currentLocale = $("html").attr('lang');

    $("#web-site-name").on('click', function () {
        window.location.href = window.location.origin;
    });

    $('.selectpicker').selectpicker();
    loadArtistLetters();

    $("#login-button").on('click', function () {

        var username = $("#username").val();
        var password = $("#password").val();

        if (username == "") {
            alert("Username is required!");
            return;
        }

        if (password == "") {
            alert("Password is required!");
            return;
        }

        doLogin(username, password);
    });

    $(".logout").on('click', function () {
        doLogout();
    });

    $("#artist-letter-select").on('change', function () {
        var selectedLetter = $(this).find("option:selected").val();
        loadArtists(selectedLetter);
        enableArtistSelection();
    });

    $("#artist-name-select").on('change', function () {
        var selectedArtist = $(this).find("option:selected").val();
        loadSongsOfArtist(selectedArtist);
        enableSongSelection();
    });

    $("#song-name-select").on('change', function () {
        var selectedSongName = $(this).find("option:selected").val();
        enableGoToSongButton();
        var songID = getSelectedSongID(selectedSongName);
        $("#go-to-song-button").attr("href", "/controller?command=song&songID=" + songID);
    });

    $("#english-language-select").on('click', function () {
        changeLanguage("en_US");
    });

    $("#russian-language-select").on('click', function () {
        changeLanguage("ru_RU");
    });
});