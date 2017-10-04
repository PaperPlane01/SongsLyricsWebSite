$(document).ready(function () {
    let songID = $("#song-id").html();
    let songRatingManager = new SongRatingManager();

    $("#rate-song-button").on('click', function () {
        let rating = $("#rating-of-song").find("option:selected").val();
        songRatingManager.rateSong(songID, rating);
    })
});

function SongRatingManager() {
    this.rateSong = function (songID, rating) {
        $.post(
            {
                url : 'controller',
                data : {
                    command : 'rate_song',
                    songID : songID,
                    rating : rating
                },

                success : function (responseData) {
                    if (responseData.status === "SUCCESS") {
                        $("#song-rating-message").css('display', 'none');
                        location.reload();
                    }

                    if (responseData.status === "FAILURE") {
                        $("#song-rating-message").css('display', 'block');
                        $("#song-rating-message").html('<span style = "color:red">' + responseData.message + '</span>')
                    }
                }
            }
        )
    }
}