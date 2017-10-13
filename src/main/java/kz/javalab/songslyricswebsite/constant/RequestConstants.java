package kz.javalab.songslyricswebsite.constant;

/**
 * This class contains constants related to requests sent by the client.
 */
public class RequestConstants {

    private RequestConstants() {

    }

    public class RequestParameters {
        public static final String LETTER = "letter";
        public static final String ARTIST_NAME = "artistName";
        public static final String SONG_ID = "songID";
        public static final String CONTENT = "content";
        public static final String SONG_NAME = "songName";
        public static final String SONG_ARTIST = "songArtist";
        public static final String FEATURED_ARTISTS = "featuredArtists";
        public static final String SONG_GENRES = "songGenres";
        public static final String SONG_LYRICS = "songLyrics";
        public static final String YOUTUBE_VIDEO_ID = "youTubeVideoID";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SECOND_PASSWORD = "secondPassword";
        public static final String RATING = "rating";
        public static final String LANGUAGE = "language";
        public static final String USER_ID = "userID";
        public static final String COMMAND = "command";
        public static final String COMMENT_ID = "commentID";
        public static final String CURRENT_PASSWORD = "currentPassword";
        public static final String NEW_PASSWORD = "newPassword";
        public static final String DUPLICATED_NEW_PASSWORD = "duplicatedNewPassword";
    }

    public class RequestAttributes {
        public static final String SONG_ID = "songID";
        public static final String SONG_TITLE = "songTitle";
        public static final String LIST_OF_LYRICS_PARTS = "listOfLyricsParts";
        public static final String IS_APPROVED = "isApproved";
        public static final String COMMENTS = "comments";
        public static final String YOUTUBE_VIDEO_ID = "youTubeVideoID";
        public static final String USER_HAS_RATED_SONG = "userHasRatedSong";
        public static final String USER_RATING = "userRating";
        public static final String SONG_NAME = "songName";
        public static final String SONG_ARTIST = "songArtist";
        public static final String FEATURED_ARTISTS = "featuredArtists";
        public static final String SONG_GENRES = "songGenres";
        public static final String SONG_LYRICS = "songLyrics";
        public static final String PROFILE_OWNER = "profileOwner";
        public static final String NOT_APPROVED_SONGS = "notApprovedSongs";
        public static final String CONTRIBUTED_SONGS = "contributedSongs";
        public static final String NUMBER_OF_COMMENTS = "numberOfComments";
        public static final String SONG = "song";
    }

    public class SessionAttributes {
        public static final String USER = "user";
        public static final String LANGUAGE = "language";
    }

    public class Commands {
        public static final String ARTISTS = "artists";
        public static final String ARTISTS_LETTERS = "artists_letters";
        public static final String SONGS = "songs";
        public static final String RECENTLY_ADDED_SONGS = "recently_added_songs";
        public static final String NOT_APPROVED_SONGS = "not_approved_songs";
        public static final String EDIT_SONG = "edit_song";
        public static final String TOP_TEN_RATED_SONGS = "top_ten_rated_songs";
        public static final String ADD_COMMENT = "add_comment";
        public static final String ADD_SONG = "add_song";
        public static final String APPLY_SONG_CHANGES = "apply_song_changes";
        public static final String LOGIN = "login";
        public static final String RATE_SONG = "rate_song";
        public static final String REGISTER = "register";
        public static final String APPROVE_SONG = "approve_song";
        public static final String CHANGE_LANGUAGE = "change_language";
        public static final String LOG_OUT = "logout";
        public static final String NEW_SONG = "new_song";
        public static final String PROFILE = "profile";
        public static final String SONG = "song";
        public static final String BLOCK_USER = "block_user";
        public static final String DELETE_COMMENT = "delete_comment";
        public static final String UNBLOCK_USER = "unblock_user";
        public static final String CHANGE_PASSWORD = "change_password";
    }
}
