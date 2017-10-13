package kz.javalab.songslyricswebsite.exception;

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
