package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.CommentsDataAccessObject;
import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.CommentAddingException;
import kz.javalab.songslyricswebsite.exception.CommentAlteringException;
import kz.javalab.songslyricswebsite.exception.InvalidCommentContentException;
import sun.misc.Request;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by PaperPlane on 08.09.2017.
 */
public class CommentsManager {

    private RequestWrapper requestWrapper;

    public CommentsManager() {
    }

    public CommentsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public List<Comment> getCommentsOfSong(int songID) {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Comment> comments = commentsDataAccessObject.getCommentsOfSong(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return comments;
    }

    public boolean checkIfSongHasComments(int songID) {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = commentsDataAccessObject.checkIfSongHasComments(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }

    public void addCommentToDatabase() throws CommentAddingException, InvalidCommentContentException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        User currentUser = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);
        int songID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SONG_ID));
        String content = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.CONTENT);

        Comment comment = new Comment();
        comment.setAuthor(currentUser);
        comment.setSongID(songID);
        comment.setContent(content);

        if (!validateComment(comment)) {
            throw new InvalidCommentContentException();
        }

        try {
            connection.setAutoCommit(false);
            commentsDataAccessObject.addCommentToDatabase(comment, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                throw new CommentAddingException();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new CommentAddingException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    private boolean validateComment(Comment comment) {
        return comment.getContent().length() <= 1000 && comment.getContent().length() >= 1;
    }

    public void markCommentAsDeleted(int commentID) throws CommentAlteringException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            commentsDataAccessObject.markCommentAsDeleted(commentID, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                throw new CommentAlteringException();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new CommentAlteringException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
