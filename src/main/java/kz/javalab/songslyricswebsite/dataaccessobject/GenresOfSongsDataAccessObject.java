package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for receiving, inserting and updating data of "Genres_of_songs" table.
 */
public class GenresOfSongsDataAccessObject extends AbstractDataAccessObject {

    private static Logger logger = Logger.getLogger(GenresOfSongsDataAccessObject.class.getName());

    /**
     * Constructs <Code>GenresOfSongsDataAccessObject</Code> instance.
     */
    public GenresOfSongsDataAccessObject() {
        super();
    }

    /**
     * Adds "song" - "genre" match to the database.
     * @param songID ID of the song.
     * @param genreID ID of the genre.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into the database.
     */
    public void addGenreOfSong(int songID, int genreID, Connection connection) throws SQLException {
        String addSongGenreMatchQuery = "INSERT INTO genres_of_songs\n" +
                "(song_id, genre_id)\n" +
                "VALUES\n" +
                "(?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(addSongGenreMatchQuery)) {
            executePreparedStatementWithMultipleIntegerValues(preparedStatement, songID, genreID);
        }
    }

    /**
     * Marks "song" - "genre" match as deleted.
     * @param matchID ID of "song" - "genre" match.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void markSongGenreMatchAsDeleted(int matchID, Connection connection) throws SQLException {
        String markAsDeletedQuery = "UPDATE genres_of_songs\n" +
                "SET match_deleted = ?\n" +
                "WHERE match_id = ?";

        int matchDeletedValue = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(markAsDeletedQuery)) {
            executePreparedStatementWithMultipleIntegerValues(preparedStatement, matchDeletedValue, matchID);
        }
    }

    /**
     * Returns "song" - "genre" match ID.
     * @param songID ID of the song.
     * @param genreID ID of the genre.
     * @param connection Connection to be used.
     * @return "song" - "genre" match ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public int getSongGenreMatchID(int songID, int genreID, Connection connection) throws DataAccessException {
        int songGenreMatchID = 0;

        String getMatchID = "SELECT match_id FROM genres_of_songs\n" +
                "WHERE song_id = ? AND genre_id = ?";
        int songIDParameter = 1;
        int genreIDParameter = 2;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getMatchID)) {
            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(genreIDParameter, genreID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    songGenreMatchID = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresOfSongsTable.MATCH_ID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_SONG_GENRE_MATCH_ID, e);
            throw new DataAccessException();
        }

        return songGenreMatchID;
    }

    /**
     * Retrieves list of genres of the song with specific ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return List of genres of the song with specific ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<String> getGenresOfSongBySongID(int songID, Connection connection) throws DataAccessException {
        List<String> genres = new ArrayList<>();

        String getGenresOfSongQuery = "SELECT genre_name FROM\n" +
                "(SELECT genre_name, song_id FROM genres_of_songs INNER JOIN genres\n" +
                "ON genres_of_songs.genre_id = genres.genre_id)\n" +
                "AS intermediate_table\n" +
                "INNER JOIN songs\n" +
                "ON intermediate_table.song_id = songs.song_id\n" +
                "WHERE songs.song_id = ?";
        int songIDParameter = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getGenresOfSongQuery)) {
            preparedStatement.setInt(songIDParameter, songID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String genre = resultSet.getString(DatabaseConstants.ColumnLabels.GenresTable.GENRE_NAME);
                    genres.add(genre);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_GENRES_OF_SONGS, e);
            throw new DataAccessException();
        }

        return genres;
    }

}
