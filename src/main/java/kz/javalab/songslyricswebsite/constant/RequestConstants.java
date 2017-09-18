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
    }

    public class SessionAttributes {
        public static final String USER = "user";
        public static final String LANGUAGE = "language";
    }
}
