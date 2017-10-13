package kz.javalab.songslyricswebsite.exception;

public class CommentAddingException extends Exception {

    public CommentAddingException() {
        super();
    }

    public CommentAddingException(String message) {
        super(message);
    }

    public CommentAddingException(String message, Throwable cause) {
        super(message, cause);
    }
}
