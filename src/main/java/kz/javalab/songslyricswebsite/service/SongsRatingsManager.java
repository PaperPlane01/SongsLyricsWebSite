package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsRatingsDataAccessObject;
import kz.javalab.songslyricswebsite.exception.InvalidRatingValueException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 07.09.2017.
 */
public class SongsRatingsManager {

    public void rateSong(int userID, int songID, int rating) throws InvalidRatingValueException {
        if (!validateRatingValue(rating)) {
            throw new InvalidRatingValueException();
        }

        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            songsRatingsDataAccessObject.rateSong(userID, songID, rating, connection);
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

    private boolean validateRatingValue(int rating) {
        return rating <= 5 && rating >= 1;
    }

    public int getUserRatingOfSong(int userID, int songID) {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        int userRatingOfSong = songsRatingsDataAccessObject.getUserRatingOfSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userRatingOfSong;
    }

    public boolean checkIfUserRatedSong(int userID, int songID) {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = songsRatingsDataAccessObject.checkIfUserRatedSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }
    public void alterSongRating(int userID, int songID, int newRating) throws InvalidRatingValueException {
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

    public double getAverageRatingOfSong(int songID) {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        double averageRating = songsRatingsDataAccessObject.getAverageRatingOfSong(songID, connection);

        ConnectionPool.getInstance().returnConnection(connection);
        return averageRating;
    }
}
