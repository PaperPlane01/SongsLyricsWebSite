package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.CommentsDataAccessObject;
import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.exception.CommentAddingException;
import kz.javalab.songslyricswebsite.exception.CommentAlteringException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by PaperPlane on 08.09.2017.
 */
public class CommentsManager {

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

    public void addCommentToDatabase(Comment comment) throws CommentAddingException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

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
