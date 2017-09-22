package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 16.09.2017.
 */
public class GenresOfSongsDataAccessObject extends AbstractDataAccessObject {

    /**
     * Adds "song" - "genre" match to the database.
     * @param songID ID of the song.
     * @param genreID ID of the genre.
     * @param connection Connection to be used.
     */
    public void addGenreOfSong(int songID, int genreID, Connection connection) throws SQLException {
        String addSongGenreMatchQuery = "INSERT INTO genres_of_songs\n" +
                "(song_id, genre_id)\n" +
                "VALUES\n" +
                "(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(addSongGenreMatchQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, songID, genreID);

    }

    /**
     * Marks "song" - "genre" match as deleted.
     * @param matchID ID of "song" - "genre" match.
     * @param connection Connection to be used.
     */
    public void markSongGenreMatchAsDeleted(int matchID, Connection connection) throws SQLException {
        String markAsDeletedQuery = "UPDATE genres_of_songs\n" +
                "SET match_deleted = ?\n" +
                "WHERE match_id = ?";

        int matchDeletedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(markAsDeletedQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, matchDeletedValue, matchID);

    }

    /**
     * Returns "song" - "genre" match ID.
     * @param songID ID of the song.
     * @param genreID ID of the genre.
     * @param connection Connection to be used.
     * @return "song" - "genre" match ID.
     */
    public int getSongGenreMatchID(int songID, int genreID, Connection connection) {
        int songGenreMatchID = 0;

        String getMatchID = "SELECT match_id FROM genres_of_songs\n" +
                "WHERE song_id = ? AND genre_id = ?";
        int songIDParameter = 1;
        int genreIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getMatchID);

            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(genreIDParameter, genreID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                songGenreMatchID = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresOfSongsTable.MATCH_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songGenreMatchID;
    }

    /**
     * Checks if there is a "song" - "genre" match with specific genre ID and song ID.
     * @param genreID ID of the genre.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such "song" - "genre" match exists, <Code>False</Code> if not.
     */
    public boolean checkIfSongGenreMatchExists(int genreID, int songID, Connection connection) {
        boolean result = false;

        String checkSongGenreMatchQuery = "SELECT match_id FROM genres_of_songs\n" +
                "WHERE song_id = ? and genre_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkSongGenreMatchQuery);

            result = checkEntityExistence(preparedStatement, songID, genreID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
