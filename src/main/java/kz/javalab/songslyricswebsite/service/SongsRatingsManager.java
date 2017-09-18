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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 07.09.2017.
 */
public class SongsRatingsManager {

    private RequestWrapper requestWrapper;

    public SongsRatingsManager() {
    }

    public SongsRatingsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

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

    public List<Song> getTopTenRatedSongs() {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        Connection connection = ConnectionPool.getInstance().getConnection();

        List<Song> songs = new ArrayList<>();
        Map<Integer, Double> mapWithSongsIDs = songsRatingsDataAccessObject.getTopTenRatedSongsIDsAndRatings(connection);

        for (Map.Entry entry : mapWithSongsIDs.entrySet()) {
            Integer songID = (Integer) entry.getKey();
            Double songRating = (Double) entry.getValue();

            System.out.println(songID + " " + songRating);

            boolean withLyrics = false;

            Song song = songsDataAccessObject.getSongByID(songID, withLyrics, connection);

            song.setAverageRating(songRating);

            songs.add(song);
        }

        return songs;
    }
}
