package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class contains methods for receiving, inserting and updating data of "songs_ratings" table.
 */
public class SongsRatingsDataAccessObject extends AbstractDataAccessObject {

    /**
     * Constructs <Code>SongsRatingsDataAccessObject</Code> instance.
     */
    public SongsRatingsDataAccessObject() {
        super();
    }

    /**
     * Inserts rating value of the specific song sent by the specific user.
     * @param userID ID of user who rated the song.
     * @param songID ID of the song which has been rated.
     * @param rating Value of the rating.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into database.
     */
    public void rateSong(int userID, int songID, int rating, Connection connection) throws SQLException {
        String rateSongQuery = "INSERT INTO songs_ratings\n" +
                "(user_id, song_id, rating)\n" +
                "VALUES (?, ?, ?)";

         PreparedStatement preparedStatement = connection.prepareStatement(rateSongQuery);

         executePreparedStatementWithMultipleIntegerValues(preparedStatement, userID, songID, rating);
    }

    /**
     * Checks if the specific user has rated the specific song.
     * @param userID ID of the user.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if user has rated this song, <Code>False</Code> if not.
     */
    public boolean checkIfUserRatedSong (int userID, int songID, Connection connection) {
        boolean result = false;

        String checkIfUserRatedSongQuery = "SELECT vote_id FROM songs_ratings\n" +
                "WHERE user_id = ? and song_id = ?\n";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkIfUserRatedSongQuery);

            result = checkEntityExistence(preparedStatement, userID, songID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  result;
    }

    /**
     * Retrieves value of rating
     * @param userID
     * @param songID
     * @param connection
     * @return
     */
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

    /**
     * Modifies rating of the song.
     * @param userID ID of the user who rated the song.
     * @param songID ID of the song which has been rated.
     * @param newRating New value of rating.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to modify data.
     */
    public void alterSongRating(int userID, int songID, int newRating, Connection connection) throws SQLException {
        String alterSongRatingQuery = "UPDATE songs_ratings\n" +
                "SET rating = ?\n" +
                "WHERE vote_id = ?";

        int ratingParameter = 1;
        int voteIDParameter = 2;

        PreparedStatement preparedStatement = connection.prepareStatement(alterSongRatingQuery);

        int voteID = getVoteID(userID, songID, connection);

        preparedStatement.setInt(ratingParameter, newRating);
        preparedStatement.setInt(voteIDParameter, voteID);

        preparedStatement.execute();

        preparedStatement.close();
    }

    /**
     * Retrieves average rating of the specified song.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Average rating of the specified song.
     */
    public double getAverageRatingOfSong(int songID, Connection connection) {
        double averageRating = 0;

        String getAverageRatingQuery = "SELECT avg(rating)\n" +
                "FROM songs_ratings\n" +
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

    /**
     * Returns a map with IDs and average ratings of top ten rated songs.
     * @param connection Connection to be used.
     * @return Map with IDs and average ratings of top ten rated songs.
     */
    public Map<Integer, Double> getTopTenRatedSongsIDsAndRatings(Connection connection) {
        Map<Integer, Double> map = new LinkedHashMap<>();

        String query = "SELECT song_id, avg(rating)\n" +
                "FROM songs_ratings\n" +
                "GROUP BY song_id\n" +
                "ORDER BY avg(rating)\n" +
                "LIMIT 0, 10";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsRatingsTable.SONG_ID);
                double averageRating = resultSet.getDouble("avg(rating)");

                map.put(songID, averageRating);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     *
     * @param userID
     * @param songID
     * @param connection
     * @return
     */
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
