$(document).ready(function () {
   let songsManager = new SongsManager();

   songsManager.loadTopTenRatedSongs();
   songsManager.loadRecentlyAddedSongs();
});

function SongsManager() {
    let self = this;

    this.loadTopTenRatedSongs = function () {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'top_ten_rated_songs'
                },

                success : function (response) {
                    switch (response.status) {
                        case "SUCCESS":
                            self._displayTopTenRatedSongs(response.data);
                            break;
                        case "FAILURE":
                            self._displayLoadingTopTenRatedSongsErrorMessage(response.message);
                            break;
                    }
                }
            }
        )
    };

    this._displayTopTenRatedSongs = function (songs) {
        $("#top-ten-rated-songs-loader").css('display', 'none');
        $.each(songs, function (index, song) {
            let displayedIndex = index + 1;
            $("#top-ten-rated-songs-list").append(
                "<li class=\"list-group-item song-link\">" + displayedIndex  + ". " + "<a href=\"" + window.location + "controller?command=song&songID=" + song.id + "\">" + song.title + "</a></li>"
            )
        })
    };

    this._displayLoadingTopTenRatedSongsErrorMessage = function (message) {
        $("#top-ten-rated-songs-loader").css('display', 'none');
        $("#top-ten-rated-songs-message").css('display', 'block');
        $("#top-ten-rated-songs-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    this.loadRecentlyAddedSongs = function () {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'recently_added_songs'
                },

                success : function (response) {
                    switch (response.status) {
                        case "SUCCESS":
                            self._displayRecentlyAddedSongs(response.data);
                            break;
                        case "FAILURE":
                            self._displayLoadingRecentlyAddedSongsErrorMessage(response.message);
                            break;
                    }
                }
            }
        )
    };

    this._displayRecentlyAddedSongs = function (songs) {
        $("#recently-added-songs-loader").css('display', 'none');
        $.each(songs, function (index, song) {
            let displayedIndex = index + 1;
            $("#recently-added-songs-list").append(
                "<li class=\"list-group-item song-link\">" + displayedIndex  + ". " + "<a href=\"" + window.location + "controller?command=song&songID=" + song.id + "\">" + song.title + "</a></li>"
            )
        })
    };

    this._displayLoadingRecentlyAddedSongsErrorMessage = function (message) {
        $("#recently-added-songs-loader").css('display', 'none');
        $("#recently-added-songs-message").css('display', 'block');
        $("#recently-added-songs-message").html("<span style = \"color:red\">" + message + "</span>");
    };

    this._hideLoadingRecentlyAddedSongsErrorMessage = function () {
        $("#recently-added-songs-message").css('display', 'none');
    }
}