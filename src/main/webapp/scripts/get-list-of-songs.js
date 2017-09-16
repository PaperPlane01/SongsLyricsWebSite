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
                    command : 'toptenratedsongs'
                },

                success : function (responseData) {
                    self._displayTopTenRatedSongs(responseData);
                }
            }
        )
    };

    this._displayTopTenRatedSongs = function (songs) {
        $.each(songs, function (index, song) {
            let displayedIndex = index + 1;
            $("#top-ten-rated-songs-list").append(
                "<li class=\"list-group-item song-link\">" + displayedIndex  + ". " + "<a href=\"/controller?command=song&songID=" + song.ID + "\">" + song.title + "</a></li>"
            )
        })
    };

    this.loadRecentlyAddedSongs = function () {
        $.post(
            {
                url : 'controller',

                data : {
                    command : 'recenltyaddedsongs'
                },

                success : function (responseData) {
                    self._displayRecentlyAddedSongs(responseData);
                }
            }
        )
    };

    this._displayRecentlyAddedSongs = function (songs) {
        $.each(songs, function (index, song) {
            let displayedIndex = index + 1;
            $("#recently-added-songs-list").append(
                "<li class=\"list-group-item song-link\">" + displayedIndex  + ". " + "<a href=\"/controller?command=song&songID=" + song.ID + "\">" + song.title + "</a></li>"
            )
        })
    }
}