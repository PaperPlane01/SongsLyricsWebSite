package kz.javalab.songslyricswebsite.dataaccessobject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class GenresDataAccessObject extends AbstractDataAccessObject {

    public GenresDataAccessObject() {
    }

    /**
     * Adds genre to database.
     * @param genreName Name of the genre to be added.
     * @param connection Connection to be used.
     */
    public void addGenreToDatabase(String genreName, Connection connection) {
        String addGenreQuery = "INSERT INTO genres\n" +
                "(genre_name)\n" +
                "VALUES\n" +
                "(?)";
        int genreNameParameter = 1;

        if (!checkIfGenreExists(genreName, connection)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addGenreQuery);

                preparedStatement.setString(genreNameParameter, genreName);

                preparedStatement.execute();

                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Adds "song" - "genre" match to the database.
     * @param songID ID of the song.
     * @param genreID ID of the genre.
     * @param connection Connection to be used.
     */
    public void addSongGenreMatch(int songID, int genreID, Connection connection) {
        String addSongGenreMatchQuery = "INSERT INTO genres_of_songs\n" +
                "(song_id, genre_id)\n" +
                "VALUES\n" +
                "(?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addSongGenreMatchQuery);

            executePreparedStatementWithMultipleIntegerValues(preparedStatement, songID, genreID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks "song" - "genre" match as deleted.
     * @param matchID ID of "song" - "genre" match.
     * @param connection Connection to be used.
     */
    public void markSongGenreMatchAsDeleted(int matchID, Connection connection) {
        String markAsDeletedQuery = "UPDATE genres_of_songs\n" +
                "SET match_deleted = ?\n" +
                "WHERE match_id = ?";

        int matchDeletedValue = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(markAsDeletedQuery);

            executePreparedStatementWithMultipleIntegerValues(preparedStatement, matchDeletedValue, matchID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /**
     * Checks if there is a genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such genre exists, <Code>False</Code> if not.
     */
    public boolean checkIfGenreExists(String genreName, Connection connection) {
        boolean result = false;

        String checkGenreQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGenreQuery);

            result = checkEntityExistenceByStringValue(preparedStatement, genreName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  result;
    }

    /**
     * Returns ID of the genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return ID of the genre with specific name.
     */
    public int getGenreID(String genreName, Connection connection) {
        int genreID = 0;

        String genreIDQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";
        int genreNameParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(genreIDQuery);

            preparedStatement.setString(genreNameParameter, genreName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                genreID = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresTable.GENRE_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreID;
    }

}
