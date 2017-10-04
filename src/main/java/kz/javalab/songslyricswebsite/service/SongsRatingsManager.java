package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsRatingsDataAccessObject;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.InvalidRatingValueException;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.exception.SongRatingException;
import kz.javalab.songslyricswebsite.exception.UserNotLoggedInException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for managing ratings of songs.
 */
public class SongsRatingsManager {

    /**
     * <Code>RequestWrapper</Code> which contains data sent by user.
     */
    private RequestWrapper requestWrapper;

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
    public void rateSong() throws InvalidRatingValueException, UserNotLoggedInException, NoSuchSongException, SongRatingException {
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

                SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();

                connection.setAutoCommit(false);

                songsRatingsDataAccessObject.rateSong(user.getID(), songID, rating, connection);

                connection.commit();
            } catch (NumberFormatException e) {
                throw new InvalidRatingValueException();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                    throw new SongRatingException();
                } catch (SQLException e1) {
                    e1.printStackTrace();
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
    public int getUserRatingOfSong(int userID, int songID) {
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
    public boolean checkIfUserRatedSong(int userID, int songID) {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = songsRatingsDataAccessObject.checkIfUserRatedSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }

    public void updateSongRating(int userID, int songID, int newRating) throws InvalidRatingValueException {
        if (!validateRatingValue(newRating)) {
            throw new InvalidRatingValueException();
        }

        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            songsRatingsDataAccessObject.alterSongRating(userID, songID, newRating, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
