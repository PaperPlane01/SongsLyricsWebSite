package kz.javalab.songslyricswebsite.constant;

/**
 * Created by PaperPlane on 18.09.2017.
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
        public static final String YOUTUBE_VIDEO_ID = "youTubeLink";
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
    }

    public class SessionAttributes {
        public static final String USER = "user";
        public static final String LANGUAGE = "language";
    }

    public class Commands {
        public static final String ARTISTS = "artists";
        public static final String ARTISTS_LETTERS = "artistsletters";
        public static final String SONGS = "songs";
        public static final String RECENTLY_ADDED_SONGS = "recentlyaddedsongs";
        public static final String NOT_APPROVED_SONGS = "notapprovedsongs";
        public static final String EDIT_SONG = "editsong";
        public static final String TOP_TEN_RATED_SONGS = "toptenratedsongs";
        public static final String ADD_COMMENT = "addcomment";
        public static final String ADD_SONG = "addsong";
        public static final String APPLY_SONG_CHANGES = "applysongchanges";
        public static final String GET_LABEL = "getlabel";
        public static final String LOGIN = "login";
        public static final String RATE_SONG = "ratesong";
        public static final String REGISTER = "register";
        public static final String APPROVE_SONG = "approvesong";
        public static final String CHANGE_LANGUAGE = "changelanguage";
        public static final String LOG_OUT = "logout";
        public static final String NEW_SONG = "newsong";
        public static final String PROFILE = "profile";
        public static final String SONG = "song";
        public static final String BLOCK_USER = "blockuser";
        public static final String DELETE_COMMENT = "deletecomment";
        public static final String UNBLOCK_USER = "unblockuser";
        public static final String CHANGE_PASSWORD = "changepassword";
    }
}
