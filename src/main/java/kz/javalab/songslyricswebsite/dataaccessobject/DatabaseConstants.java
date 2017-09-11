package kz.javalab.songslyricswebsite.dataaccessobject;


/**
 * Created by PaperPlane on 18.08.2017.
 */
public class DatabaseConstants {

    public static class ColumnLabels {
        public static class ArtistsTable {
            public static final String ARTIST_ID = "artist_id";
            public static final String ARTIST_NAME = "artist_name";
            public static final String ARTIST_LETTER = "artist_letter";
        }

        public static class CommentsTable {
            public static final String COMMENT_ID = "comment_id";
            public static final String SONG_ID = SongsTable.SONG_ID;
            public static final String USER_ID = UsersTable.USER_ID;
            public static final String COMMENT_CONTENT = "comment_content";
            public static final String TIME = "time";
        }

        public static class FeaturingsTable {
            public static final String FEATURING_ID = "featuring_id";
            public static final String ARTIST_ID = ArtistsTable.ARTIST_ID;
            public static final String SONG_ID = SongsTable.SONG_ID;
        }

        public static class GenresTable {
            public static final String GENRE_ID = "genre_id";
            public static final String GENRE_NAME = "genre_name";
        }

        public static class GenresOfSongsTable {
            public static final String MATCH_ID = "match_id";
            public static final String SONG_ID = SongsTable.SONG_ID;
            public static final String GENRE_ID = GenresTable.GENRE_ID;
        }

        public static class LinesTable {
            public static final String LINE_ID = "line_id";
            public static final String SONG_ID = SongsTable.SONG_ID;
            public static final String CONTENT = "content";
            public static final String SONG_PART = "song_part";
        }

        public static class SongsTable {
            public static final String SONG_ID = "song_id";
            public static final String ARTIST_ID = ArtistsTable.ARTIST_ID;
            public static final String SONG_NAME = "song_name";
            public static final String IS_APPROVED = "is_approved";
            public static final String HAS_FEATURING = "has_featuring";
            public static final String YOUTUBE_LINK = "youtube_link";
        }

        public static class SongsRatingsTable {
            public static final String VOTE_ID = "vote_id";
            public static final String USER_ID = UsersTable.USER_ID;
            public static final String SONG_ID = SongsTable.SONG_ID;
            public static final String RATING = "rating";
        }

        public static class UsersTable {
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "user_name";
            public static final String HASHED_PASSWORD = "hashed_password";
            public static final String USER_ROLE = "user_role";
        }
    }
}
