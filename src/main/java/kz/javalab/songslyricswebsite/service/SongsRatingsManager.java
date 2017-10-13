package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsRatingsDataAccessObject;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is responsible for managing ratings of songs.
 */
public class SongsRatingsManager {

    /**
     * <Code>RequestWrapper</Code> which contains data sent by user.
     */
    private RequestWrapper requestWrapper;

    private Logger logger = Logger.getLogger(SongsRatingsManager.class.getName());

    /**
     * Constructs <Code>SongsRatingsManager</Code> instance.
     */
    public SongsRatingsManager() {
    }

    /**
     * Constructs <Code>SongsRatingsManager</Code> instance with pre-defined requestWrapper.
     * @param requestWrapper <Code>RequestWrapper</Code> instance.
     */
    public SongsRatingsManager(RequestWrapper requestWrapper) {
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
     * Inserts new rating of song.
     * @throws InvalidRatingValueException Thrown if value of the rating is invalid.
     * @throws UserNotLoggedInException Thrown if rating value received from not logged in user.
     * @throws NoSuchSongException Thrown if there is no song with received ID.
     * @throws SongRatingException Thrown if some error occurred when attempted to insert data.
     */
    public void rateSong() throws InvalidRatingValueException, UserNotLoggedInException, NoSuchSongException, SongRatingException, DataAccessException {
        User user = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

        if (user == null) {
            throw new UserNotLoggedInException();
        }

        try {
            int songID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SONG_ID));

            SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

            Connection connection = ConnectionPool.getInstance().getConnection();

            if (!songsDataAccessObject.checkIfSongExists(songID, connection)) {
                throw new NoSuchSongException();
            }

            try {
                int rating = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.RATING));

                if (!validateRatingValue(rating)) {
                    throw new InvalidRatingValueException();
                }

                connection.setAutoCommit(false);

                if (!checkIfUserHasRatedSong(user.getID(), songID, connection)) {
                    SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
                    songsRatingsDataAccessObject.rateSong(user.getID(), songID, rating, connection);
                } else {
                    updateSongRating(user.getID(), songID, rating, connection);
                }

                connection.commit();
            } catch (NumberFormatException e) {
                throw new InvalidRatingValueException();
            } catch (SQLException e) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_RATING_THE_SONG, e);
                try {
                    connection.rollback();
                    throw new SongRatingException();
                } catch (SQLException e1) {
                    logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                    throw new SongRatingException();
                }
            } finally {
                ConnectionPool.getInstance().returnConnection(connection);
            }
        } catch (NumberFormatException e) {
            throw new NoSuchSongException();
        }
    }

    /**
     * Validates rating value.
     * @param rating Rating value to be validated.
     * @return <Code>True</Code> if rating value is valid, <Code>False</Code> if not.
     */
    private boolean validateRatingValue(int rating) {
        return rating <= 5 && rating >= 1;
    }

    /**
     * Returns user's rating of the song.
     * @param userID ID of the song.
     * @param songID ID of the user.
     * @return User's rating of the song.
     */
    public int getUserRatingOfSong(int userID, int songID) throws DataAccessException {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        int userRatingOfSong = songsRatingsDataAccessObject.getUserRatingOfSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userRatingOfSong;
    }

    /**
     * Checks if user rated the song.
     * @param userID ID of the user.
     * @param songID ID of the song.
     * @return <Code>True</Code> if user has rated the song, <Code>False</Code> if not.
     */
    public boolean checkIfUserRatedSong(int userID, int songID) throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = checkIfUserHasRatedSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }

    /**
     * Checks if user rated the song.
     * @param userID ID of the user.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if user has rated the song, <Code>False</Code> if not.
     */
    private boolean checkIfUserHasRatedSong(int userID, int songID, Connection connection) throws DataAccessException {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        return songsRatingsDataAccessObject.checkIfUserRatedSong(userID, songID, connection);
    }

    /**
     * Updates song's rating.
     * @param userID ID of the user.
     * @param songID ID of the song.
     * @param newRating New rating value.
     * @param connection Connection to be used.
     */
    private void updateSongRating(int userID, int songID, int newRating, Connection connection) throws DataAccessException {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();

        try {
            connection.setAutoCommit(false);
            songsRatingsDataAccessObject.updateSongRating(userID, songID, newRating, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
