package kz.javalab.songslyricswebsite.constant;

/**
 * Contains constants related to responses sent by server to the client.
 */
public class ResponseConstants {

    private ResponseConstants() {
    }

    public class ContentTypes {
        public static final String JSON = "application/json";
    }

    public class Status {
        public static final String STATUS = "status";
        public static final String SUCCESS = "SUCCESS";
        public static final String FAILURE = "FAILURE";
    }

    public class Messages {
        public static final String LABELS = "labels";
        public static final String MESSAGE = "message";
        public static final String REASON = "reason";
        public static final String DATA = "data";
        public static final String INVALID_COMMENT_CONTENT = "labels.errors.invalidcomment";
        public static final String ERROR_WHILE_ADDING_COMMENT = "labels.errors.errorwhileaddingcomment";
        public static final String SONG_HAS_BEEN_ADDED = "labels.songhasbeenadded";
        public static final String INVALID_ARTIST_NAME = "labels.errors.artistname.invalid";
        public static final String INVALID_SONG_NAME = "labels.errors.songname.invalid";
        public static final String INVALID_FEATURED_ARTISTS = "labels.errors.featuredartists.invalid";
        public static final String INVALID_YOUTUBE_VIDEO_ID = "labels.errors.youtubevideoid.invalid";
        public static final String INVALID_SONG_GENRES = "labels.errors.songgenres.invalid";
        public static final String TOO_LONG_LYRICS = "labels.errors.youtubevideoid.invalid";
        public static final String FEATURED_ARTIST_NAME_IS_TOO_LONG = "labels.errors.featuredartists.artistnametoolong";
        public static final String INVALID_SONG_LYRICS = "labels.errors.songlyrics.invalid";
        public static final String SUCH_SONG_ALREADY_EXISTS = "labels.suchsongalreadyexists";
        public static final String ERROR_WHILE_ADDING_SONG = "labels.errors.errorwhileadding";
        public static final String NO_SUCH_SONG = "labels.errors.nosuchsong.explained";
        public static final String NO_SUCH_USER = "labels.errors.nosuchuser.explained";
        public static final String ERROR_WHILE_ALTERING_SONG = "labels.errors.errorwhilealtering";
        public static final String LOGIN_SUCCESS = "labels.loginsuccess";
        public static final String LOGIN_FAILED = "labels.loginfailed";
        public static final String WRONG_PASSWORD = "labels.wrongpassword";
        public static final String WRONG_USERNAME = "labels.wrongusername";
        public static final String SONG_RATING_RECEIVED_FROM_NOT_LOGGED_IN_USER = "labels.errors.songratingreceivedfromnonloggedinuser";
        public static final String INVALID_RATING_VALUE = "labels.errors.invalidratingvalue";
        public static final String SUCH_USER_ALREADY_EXISTS = "labels.suchuseralreadyexists";
        public static final String REGISTRATION_FAILED = "labels.registrationfailed";
        public static final String INVALID_USERNAME = "labels.errors.invalidusername.general";
        public static final String INVALID_PASSWORD = "labels.errors.invalidpassword.general";
        public static final String PASSWORDS_ARE_NOT_EQUAL = "labels.errors.passwordsarentequal";
        public static final String NO_PERMISSION = "labels.errors.nopermission";
        public static final String ERROR_WHILE_BLOCKING = "labels.errors.errorwhileblocking";
        public static final String SUCCESSFUL_BLOCKING = "labels.successfulblocking";
        public static final String ERROR_WHILE_DELETING_COMMENT = "labels.errors.errorwhiledeletingcomment";
        public static final String SUCCESSFUL_COMMENT_DELETING = "labels.successfulcommentdeleting";
        public static final String COMMENT_RECEIVED_FROM_NOT_LOGGED_IN_USER = "labels.errors.commentreceivedfromnonloggedinuser";
        public static final String USER_IS_BLOCKED = "labels.errors.userisblocked";
        public static final String SUCCESSFUL_UNBLOCKING = "labels.successfuluserunblocking";
        public static final String ERROR_WHILE_UNBLOCKING = "labels.errors.errorwhileunblockinguser";
        public static final String USER_NOT_LOGGED_IN = "labels.errors.usernologgedin";
        public static final String SUCCESSFUL_PASSWORD_CHANGING = "labels.successfulpasswordchanging";
        public static final String ERROR_WHILE_CHANGING_PASSWORD = "labels.errors.errorwhilechangingpassword";
        public static final String SUCCESSFUL_SONG_APPROVING = "labels.songhasbeenapproved";
        public static final String ERROR_WHILE_APPROVING_SONG = "labels.errors.errorwhileapprovingsong";
        public static final String SUCCESSFUL_SONG_MODIFYING = "labels.successfulsongmodifying";
        public static final String FAILED_TO_LOAD_DATA = "labels.errors.failedtoloaddata";
        public static final String ERROR_WHILE_RATING_SONG = "labels.errors.errorwhileratingsong";
        public static final String REGISTRATION_SUCCESS = "labels.registrationsuccess";
        public static final String SERVER_PROBLEMS = "labels.serverproblems";
    }

    public class Pages {
        public static final String NO_PERMISSION_PAGE = "path.page.nopermission";
        public static final String INDEX_PAGE = "path.page.index";
        public static final String EDIT_SONG_PAGE = "path.page.editsong";
        public static final String NO_SUCH_SONG_PAGE = "path.page.nosuchsong";
        public static final String NEW_SONG_PAGE = "path.page.newsong";
        public static final String NOT_APPROVED_SONGS_PAGE = "path.page.notapprovedsongs";
        public static final String MY_PROFILE_PAGE = "path.page.myprofile";
        public static final String PROFILE_PAGE = "path.page.profile";
        public static final String NO_SUCH_USER_PAGE = "path.page.nosuchuser";
        public static final String SONG_PAGE = "path.page.song";
        public static final String DATA_LOADING_ERROR_PAGE = "path.page.dataloagingerror";
        public static final String PAGE_NOT_FOUND = "path.page.pagenotfound";
    }

}
