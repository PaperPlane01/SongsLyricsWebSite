package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by PaperPlane on 30.07.2017.
 * This class have methods allowing to get, insert and update data of "Songs" table.
 */
public class SongsDataAccessObject extends AbstractDataAccessObject {

    private static Logger logger = Logger.getLogger(SongsRatingsDataAccessObject.class.getName());

    /**
     * Constructs <Code>SongsDataAccessObject</Code> instance.
     */
    public SongsDataAccessObject() {
        super();
    }

    /**
     * Returns ID of last added song.
     * @param connection Connection to be used.
     * @return ID of last added song.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public int getLastSongID(Connection connection) throws DataAccessException {
        String lastSongIDQuery = "SELECT max(song_id)\n" +
                "FROM songs";
        int lastSongID = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(lastSongIDQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    lastSongID = resultSet.getInt("max(song_id)");
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_LAST_SONG_ID, e);
            throw new DataAccessException();
        }

        return lastSongID;
    }

    /**
     * Modifies name of the specific song.
     * @param songID ID of the song which is to be modified.
     * @param newSongName New song name.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to modify data.
     */
    public void updateSongName(int songID, String newSongName, Connection connection) throws SQLException {
        String alterSongNameQuery = "UPDATE songs\n" +
                "SET song_name = ?\n" +
                "WHERE song_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(alterSongNameQuery)) {
            updateStringValueByEntityID(preparedStatement, songID, newSongName);
        }
    }

    /**
     * Modifies artist ID of the specific song.
     * @param songID ID of the song which is to be modified.
     * @param newArtistID New artist ID value.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to modify data.
     */
    public void updateArtistID(int songID, int newArtistID, Connection connection) throws SQLException {
        String alterArtistIDQuery = "UPDATE songs\n" +
                "SET artist_id = ?\n" +
                "WHERE song_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(alterArtistIDQuery)) {
            executePreparedStatementWithMultipleIntegerValues(preparedStatement, newArtistID, songID);
        }
    }

    /**
     * Modifies YouTube Video ID of the specific song.
     * @param songID ID of the song which is to be modified.
     * @param newYouTubeLink New YouTube video ID value.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to modify data.
     */
    public void updateYouTubeLink(int songID, String newYouTubeLink, Connection connection) throws SQLException {
        String alterYouTubeLinkQuery = "UPDATE songs\n" +
                "SET youtube_link = ?\n" +
                "WHERE song_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(alterYouTubeLinkQuery)) {
            updateStringValueByEntityID(preparedStatement, songID, newYouTubeLink);
        }
    }

    /**
     * Retrieves list of IDs of recently added songs.
     * @param numberOfSongs Number of recently added songs to be retrieved.
     * @param connection Connection to be used.
     * @return List of IDs of recently added songs.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfRecentlyAddedSongs(int numberOfSongs, Connection connection) throws DataAccessException {
        List<Integer> songIDs = new ArrayList<>();

        String getRecentlyAddedSongIDsQuery = "SELECT song_id\n" +
                "FROM songs\n" +
                "ORDER BY song_id DESC\n" +
                "LIMIT ?";

        int limitParameter = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getRecentlyAddedSongIDsQuery)) {
            preparedStatement.setInt(limitParameter, numberOfSongs);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                    songIDs.add(songID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_LIST_OF_RECENTLY_ADDED_SONGS, e);
            throw new DataAccessException();
        }

        return songIDs;
    }

    /**
     * Checks if song exists by song name and artist name.
     * @param song Song to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such song exists, <Code>False</Code> if not.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public boolean checkIfSongExists(Song song, Connection connection) throws DataAccessException {
        String songName = song.getName();
        String artistName = song.getArtist().getName();

        boolean result = false;

        String checkSongQuery = "SELECT song_id\n" +
                "FROM songs INNER JOIN artists\n" +
                "ON songs.artist_id = artists.artist_id\n" +
                "WHERE (song_name = ?) AND (artist_name = ?)";

        int songNameParameter = 1;
        int artistNameParameter = 2;

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkSongQuery)) {
            preparedStatement.setString(songNameParameter, songName);
            preparedStatement.setString(artistNameParameter, artistName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_SONG_EXISTENCE, e);
            throw new DataAccessException();
        }

        return result;
    }

    /**
     * Checks if song exists by song ID.
     * @param songID Song ID to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if song with such ID exists, <Code>False</Code> if not.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public boolean checkIfSongExists(int songID, Connection connection) throws DataAccessException {
        boolean result = false;

        String checkSongQuery = "SELECT song_id FROM songs\n" +
                "WHERE song_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkSongQuery)) {
            result = checkEntityExistence(preparedStatement, songID);
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_SONG_EXISTENCE_BY_SONG_ID);
            throw new DataAccessException();
        }

        return result;
    }

    /**
     * Returns IDs of songs performed by artist with the specified ID.
     * @param artistID ID of the artist.
     * @param connection Connection to be used.
     * @return List of songs performed by artist with the specified ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfSongsPerformedByArtist(int artistID, Connection connection) throws DataAccessException {
        List<Integer> songIDs = new ArrayList<>();

        String getSongsByArtistID = "SELECT song_id FROM songs\n" +
                "WHERE artist_id = ?\n" +
                "ORDER BY song_name";

        int artistIDParameter = 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getSongsByArtistID)) {
            preparedStatement.setInt(artistIDParameter, artistID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                    songIDs.add(songID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_IDS_OF_SONGS_PERFORMED_BY_ARTIST, e);
            throw new DataAccessException();
        }

        return songIDs;
    }

    /**
     * Returns IDs of not approved songs.
     * @param connection Connection to be used.
     * @return IDs of not approved songs.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfNotApprovedSongs(Connection connection) throws DataAccessException {
        List<Integer> songIDs = new ArrayList<>();

        String listOfNotApprovedSongsQuery = "SELECT song_id FROM songs\n" +
                "WHERE is_approved = 0";


        try (PreparedStatement preparedStatement = connection.prepareStatement(listOfNotApprovedSongsQuery) ) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                    songIDs.add(songID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_IDS_OF_NOT_APPROVED_SONGS, e);
            throw new DataAccessException();
        }

        return songIDs;

    }

    /**
     * Returns IDs of songs contributed by user with the specified ID.
     * @param userID ID of the user.
     * @param connection Connection to be used.
     * @return IDs of songs contributed by user with the specified ID.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Integer> getIDsOfSongsContributedByUser(int userID, Connection connection) throws DataAccessException {
        List<Integer> songIDs = new ArrayList<>();

        String getListOfSongsContributedByUserQuery = "SELECT song_id FROM songs\n" +
                "WHERE contributed_user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(getListOfSongsContributedByUserQuery);) {
            try (ResultSet resultSet = getResultSetOfPreparedStatementWithMultipleIntegerValues(preparedStatement, userID)) {
                while (resultSet.next()) {
                    int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                    songIDs.add(songID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_IDS_OF_SONGS_CONTRIBUTED_BY_USER, e);
            throw new DataAccessException();
        }

        return songIDs;
    }

    /**
     * Adds data related to song into <Code>songs</Code> table.
     * @param song Song which data is to be added.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into database.
     */
    public void addDataToSongsTable(Song song, Connection connection) throws SQLException {
        String addSongQuery = "INSERT INTO songs (artist_id, song_name, is_approved, youtube_link, has_featuring, contributed_user_id)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        int artistIDParameter = 1;
        int songNameParameter = 2;
        int isApprovedParameter = 3;
        int youTubeLinkParameter = 4;
        int hasFeaturingParameter = 5;
        int contributedUserIDParameter = 6;

        int isApprovedValue = 0;

        if (song.isApproved()) {
            isApprovedValue = 1;
        }

        int hasFeaturingValue = 1;
        int doesntHaveFeaturingValue = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(addSongQuery)) {
            preparedStatement.setInt(artistIDParameter, song.getArtist().getID());
            preparedStatement.setString(songNameParameter, song.getName());
            preparedStatement.setInt(isApprovedParameter, isApprovedValue);
            preparedStatement.setString(youTubeLinkParameter, song.getYouTubeVideoID());

            if (song.hasFeaturedArtists()) {
                preparedStatement.setInt(hasFeaturingParameter, hasFeaturingValue);
            } else {
                preparedStatement.setInt(hasFeaturingParameter, doesntHaveFeaturingValue);
            }

            preparedStatement.setInt(contributedUserIDParameter, song.getContributedUser().getID());

            preparedStatement.execute();
        }
    }

    /**
     * Approves song by its ID.
     * @param songID ID of the song which is to be approved.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void markSongAsApproved(int songID, Connection connection) throws SQLException {

        String approveSongQuery = "UPDATE songs\n" +
                "SET is_approved = 1\n" +
                "WHERE song_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(approveSongQuery)) {
            int songIDParameter = 1;

            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();
        }
    }

    /**
     * Retrieves data related to the specified song.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Data related to the specified song.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public Map<String, Object> getSongData(int songID, Connection connection) throws DataAccessException {
        String getSongDataQuery = "SELECT * FROM songs\n" +
                "WHERE song_id = ?";
        int songIDParameter = 1;

        Map<String, Object> data = new LinkedHashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(getSongDataQuery)) {
            preparedStatement.setInt(songIDParameter, songID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int artistID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID);
                    String songName = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME);
                    String youTubeVideoID = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK);
                    int approvedValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED);
                    boolean approved = convertIntToBoolean(approvedValue);
                    int hasFeaturingsValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING);
                    boolean hasFeaturings = convertIntToBoolean(hasFeaturingsValue);
                    int contributedUserID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.CONTRIBUTED_USER_ID);

                    data.put(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID, songID);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID, artistID);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME, songName);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED, approved);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK, youTubeVideoID);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING, hasFeaturings);
                    data.put(DatabaseConstants.ColumnLabels.SongsTable.CONTRIBUTED_USER_ID, contributedUserID);
                }
            }
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_SONG_DATA, e);
            throw new DataAccessException();
        }

        return data;
    }
}
