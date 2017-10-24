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
 * This class contains methods for receiving, inserting and updating data of "Featurings" table.
 */
public class FeaturingsDataAccessObject extends AbstractDataAccessObject {

    private static Logger logger = Logger.getLogger(FeaturingsDataAccessObject.class.getName());

    /**
     * Constructs <Code>FeaturingsDataAccessObject</Code> instance.
     */
    public FeaturingsDataAccessObject() {
        super();
    }

    /**
     * Retrieves ID of featuring with specified parameters.
     * @param artistID ID of featured artist.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return ID of featuring with specified parameters.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public int getFeaturingID(int artistID, int songID, Connection connection) throws DataAccessException {
        String checkFeaturingQuery = "SELECT featuring_id\n" +
                "FROM featurings\n" +
                "WHERE artist_id = ?\n" +
                "AND song_id = ?";
        int artistIDParameter = 1;
        int songIDParameter = 2;

        int featuringID = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkFeaturingQuery)) {
            preparedStatement.setInt(artistIDParameter, artistID);
            preparedStatement.setInt(songIDParameter, songID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    featuringID = resultSet.getInt(DatabaseConstants.ColumnLabels.FeaturingsTable.FEATURING_ID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_FEATURING_ID, e);
            throw new DataAccessException();
        }

        return  featuringID;
    }

    /**
     * Adds featuring to database.
     * @param artistID ID of the featured artist.
     * @param songID ID of the song featured by artist.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into database.
     */
    public void addNewFeaturing(int artistID, int songID, Connection connection) throws SQLException {
        String addFeaturingQuery = "INSERT INTO featurings\n" +
                "(artist_id, song_id)\n" +
                "VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(addFeaturingQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, artistID, songID);
    }

    /**
     * Marks the specified featuring as deleted.
     * @param featuringID ID of the featuring which is to be marked as deleted.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void markFeatuirngAsDeleted(int featuringID, Connection connection) throws SQLException {
        String markFeaturingAsDeletedQuery = "UPDATE featurings\n" +
                "SET is_deleted = ?\n" +
                "WHERE featuring_id = ?";

        int isDeletedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(markFeaturingAsDeletedQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isDeletedValue, featuringID);

    }

    /**
     * Returns list of featured artists of song with specified ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return List of featured artists of song with specified ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfFeaturedArtists(int songID, Connection connection) throws DataAccessException {
        List<Integer> featuredArtistsIDs = new ArrayList<>();

        String listOfIDsQuery = "SELECT artist_id FROM featurings\n" +
                "WHERE song_id = ?";
        int songIDParameter = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(listOfIDsQuery)) {

            preparedStatement.setInt(songIDParameter, songID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    featuredArtistsIDs.add(resultSet.getInt(DatabaseConstants.ColumnLabels.FeaturingsTable.ARTIST_ID));
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_IDS_OF_FEATURED_ARTISTS, e);
            throw new DataAccessException();
        }

        return featuredArtistsIDs;
    }

    /**
     * Returns IDs of songs featured by the specific artist.
     * @param artistID ID of artist.
     * @param connection Connection to be used.
     * @return IDs of songs featured by the specific artist.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfSongsFeaturedByArtist(int artistID, Connection connection) throws DataAccessException {
        List<Integer> songsIDs = new ArrayList<>();

        String listOfSongsIDsQuery = "SELECT song_id FROM featurings\n" +
                "WHERE artist_id = ?";
        int artistIDParameter = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(listOfSongsIDsQuery)) {
            preparedStatement.setInt(artistIDParameter, artistID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    songsIDs.add(resultSet.getInt(DatabaseConstants.ColumnLabels.FeaturingsTable.SONG_ID));
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_IDS_OF_SONGS_FEATURED_BY_ARTIST);
            throw new DataAccessException();
        }

        return songsIDs;
    }
}
