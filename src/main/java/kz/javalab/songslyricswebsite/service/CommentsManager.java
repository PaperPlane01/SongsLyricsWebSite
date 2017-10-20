package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.connectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.CommentsDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.UsersDataAccessObject;
import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * This class is responsible for managing comments.
 */
public class CommentsManager {

    /**
     * <Code>RequestWrapper</Code> which contains data sent by user.
     */
    private RequestWrapper requestWrapper;

    private static Logger logger = Logger.getLogger(CommentsManager.class);

    /**
     * Constructs <Code>CommentsManager</Code> instance.
     */
    public CommentsManager() {
    }

    /**
     * Constructs <Code>CommentsManager</Code> instance with pre-defined re
     * @param requestWrapper <Code>RequestWrapper</Code> instance.
     */
    public CommentsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    /**
     * Returns requestWrapper.
     * @return requestWrapper.
     */
    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    /**
     * Sets new requestWrapper.
     * @param requestWrapper New <Code>RequestWrapper</Code> instance which is to be set.
     */
    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    /**
     * Returns comments of song with the specified ID.
     * @param songID ID of the song.
     * @return Comments of song with the specified ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Comment> getCommentsOfSong(int songID) throws DataAccessException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Comment> comments = commentsDataAccessObject.getCommentsOfSong(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return comments;
    }

    /**
     * Checks if the specified song has comments.
     * @param songID ID of the song to be checked.
     * @return <Code>True</Code> if song has comments, <Code>False</Code> if not.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public boolean checkIfSongHasComments(int songID) throws DataAccessException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = commentsDataAccessObject.checkIfSongHasComments(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }

    /**
     * Inserts comment into database.
     * @throws CommentAddingException Thrown if some error occurred when attempted to insert data.
     * @throws InvalidCommentContentException Thrown if content of the comment is invalid.
     * @throws UserNotLoggedInException Thrown if comment received from not logged in user.
     * @throws UserIsBlockedException Thrown if comment received from blocked user.
     */
    public void addCommentToDatabase() throws CommentAddingException, InvalidCommentContentException, UserNotLoggedInException, UserIsBlockedException, DataAccessException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        User currentUser = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }

        if (usersDataAccessObject.checkIfUserIsBlocked(currentUser.getID(), connection)) {
            throw new UserIsBlockedException();
        }

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
            logger.error(LoggingConstants.EXCEPTION_WHILE_ADDING_COMMENT, e);
            try {
                connection.rollback();
                throw new CommentAddingException();
            } catch (SQLException e1) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                throw new CommentAddingException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * Validates comment.
     * @param comment Comment to be validated.
     * @return <Code>True</Code> if comment is valid, <Code>False</Code> if not.
     */
    private boolean validateComment(Comment comment) {
        return comment.getContent().length() <= 1000 && comment.getContent().length() >= 1;
    }

    /**
     * Deletes comment.
     * @throws CommentDeletingException Thrown if some error occurred when attempted to update data.
     * @throws NoPermissionException Thrown if attempt of deleting the comment has been made by user
     *                               who does not have a permission to do it.
     */
    public void deleteComment() throws CommentDeletingException, NoPermissionException {
        User user = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

        if (user == null) {
            throw new NoPermissionException();
        }

        if (user.getUserType() != UserType.MODERATOR) {
            throw new NoPermissionException();
        }

        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        int commentID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.COMMENT_ID));

        try {
            connection.setAutoCommit(false);
            commentsDataAccessObject.markCommentAsDeleted(commentID, connection);
            connection.commit();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_DELETING_COMMENT, e);
            try {
                connection.rollback();
                throw new CommentDeletingException();
            } catch (SQLException e1) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                throw new CommentDeletingException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * Returns number of comments of user with the specified ID.
     * @param userID ID of the user.
     * @return Number of comments of user with the specified ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public int getNumberOfCommentsOfUser(int userID) throws DataAccessException {
        CommentsDataAccessObject commentsDataAccessObject = new CommentsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        int numberOfComments = commentsDataAccessObject.getNumberOfCommentsOfUser(userID, connection);

        ConnectionPool.getInstance().returnConnection(connection);

        return numberOfComments;
    }
}
