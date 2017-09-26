package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 09.09.2017.
 */
public class CommentDeletingException extends Exception {

    public CommentDeletingException() {
    }

    public CommentDeletingException(String message) {
        super(message);
    }

    public CommentDeletingException(String message, Throwable cause) {
        super(message, cause);
    }
}
