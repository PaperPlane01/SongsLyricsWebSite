package kz.javalab.songslyricswebsite.dataaccessobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.09.2017.
 */
public class SongsRatingsDataAccessObject {

    public void rateSong(int userID, int songID, int rating, Connection connection) {
        String rateSongQuery = "INSERT INTO songs_ratings\n" +
                "(user_id, song_id, rating)\n" +
                "VALUES (?, ?, ?)";

        int userIDParameter = 1;
        int songIDParameter = 2;
        int ratingParameter = 3;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(rateSongQuery);

            preparedStatement.setInt(userIDParameter, userID);
            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(ratingParameter, rating);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserRatedSong (int userID, int songID, Connection connection) {
        boolean result = false;

        String checkIfUserRatedSongQuery = "SELECT vote_id FROM songs_ratings\n" +
                "WHERE user_id = ? and song_id = ?\n";

        int userIDParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkIfUserRatedSongQuery);

            preparedStatement.setInt(userIDParameter, userID);
            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public int getUserRatingOfSong(int userID, int songID, Connection connection) {
        String userRatingOfSongQuery = "SELECT rating FROM songs_ratings\n" +
                "WHERE user_id = ? AND song_id = ?";

        int rating = 0;

        int userIDParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(userRatingOfSongQuery);

            preparedStatement.setInt(userIDParameter, userID);
            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rating = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsRatingsTable.RATING);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

    public void alterSongRating(int userID, int songID, int newRating, Connection connection) {
        String alterSongRatingQuery = "UPDATE songs_ratings\n" +
                "SET rating = ?\n" +
                "WHERE vote_id = ?";

        int ratingParameter = 1;
        int voteIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(alterSongRatingQuery);

            int voteID = getVoteID(userID, songID, connection);

            preparedStatement.setInt(ratingParameter, newRating);
            preparedStatement.setInt(voteIDParameter, voteID);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getAverageRatingOfSong(int songID, Connection connection) {
        double averageRating = 0;

        String getAverageRatingQuery = "SELECT avg(song_rating)\n" +
                "FROM songs_rating\n" +
                "WHERE song_id = ?";

        int songIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAverageRatingQuery);

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                averageRating = resultSet.getDouble("avg(rating)");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageRating;
    }

    private int getVoteID(int userID, int songID, Connection connection) {
        int voteID = 0;

        String voteIDQuery = "SELECT vote_id FROM songs_ratings\n" +
                "WHERE user_id = ? AND song_id = ?";

        int userIDParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(voteIDQuery);

            preparedStatement.setInt(userIDParameter, userID);
            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                voteID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsRatingsTable.VOTE_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voteID;
    }
}
