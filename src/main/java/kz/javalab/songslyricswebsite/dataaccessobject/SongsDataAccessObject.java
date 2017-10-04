package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;

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

    /**
     * Constructs <Code>SongsDataAccessObject</Code> instance.
     */
    public SongsDataAccessObject() {
        super();
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


        PreparedStatement preparedStatement = connection.prepareStatement(alterSongNameQuery);

        updateStringValueByEntityID(preparedStatement, songID, newSongName);

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

        PreparedStatement preparedStatement = connection.prepareStatement(alterArtistIDQuery);
        executePreparedStatementWithMultipleIntegerValues(preparedStatement, newArtistID, songID);

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

        PreparedStatement preparedStatement = connection.prepareStatement(alterYouTubeLinkQuery);

        updateStringValueByEntityID(preparedStatement, songID, newYouTubeLink);

    }

    /**
     * Retrieves list of IDs of recently added songs.
     * @param numberOfSongs Number of recently added songs to be retrieved.
     * @param connection Connection to be used.
     * @return List of IDs of recently added songs.
     */
    public List<Integer> getIDsOfRecentlyAddedSongs(int numberOfSongs, Connection connection) {
        List<Integer> songIDs = new ArrayList<>();

        String getRecentlyAddedSongIDsQuery = "SELECT song_id\n" +
                "FROM songs\n" +
                "ORDER BY song_id DESC\n" +
                "LIMIT ?";

        int limitParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getRecentlyAddedSongIDsQuery);

            preparedStatement.setInt(limitParameter, numberOfSongs);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                songIDs.add(songID);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songIDs;
    }

    /**
     * Checks if song exists by song name and artist name.
     * @param song Song to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such song exists, <Code>False</Code> if not.
     */
    public boolean checkIfSongExists(Song song, Connection connection) {
        String songName = song.getName();
        String artistName = song.getArtist().getName();

        boolean result = false;

        String checkSongQuery = "SELECT song_id\n" +
                "FROM songs INNER JOIN artists\n" +
                "ON songs.artist_id = artists.artist_id\n" +
                "WHERE (song_name = ?) AND (artist_name = ?)";

        int songNameParameter = 1;
        int artistNameParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkSongQuery);
            preparedStatement.setString(songNameParameter, songName);
            preparedStatement.setString(artistNameParameter, artistName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Checks if song exists by song ID.
     * @param songID Song ID to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if song with such ID exists, <Code>False</Code> if not.
     */
    public boolean checkIfSongExists(int songID, Connection connection) {
        boolean result = false;

        String checkSongQuery = "SELECT song_id FROM songs\n" +
                "WHERE song_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkSongQuery);

            result = checkEntityExistence(preparedStatement, songID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Integer> getIDsOfSongsPerformedByArtist(int artistID, Connection connection) {
        List<Integer> songIDs = new ArrayList<>();

        String getSongsByArtistID = "SELECT song_id FROM songs\n" +
                "WHERE artist_id = ?\n" +
                "ORDER BY song_name";

        int artistIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSongsByArtistID);
            preparedStatement.setInt(artistIDParameter, artistID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                songIDs.add(songID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songIDs;
    }

    public List<Integer> getIDsOfNotApprovedSongs(Connection connection) {
        List<Integer> songIDs = new ArrayList<>();

        String listOfNotApprovedSongsQuery = "SELECT song_id FROM songs\n" +
                "WHERE is_approved = 0";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(listOfNotApprovedSongsQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                songIDs.add(songID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songIDs;

    }

    public List<Integer> getIDsOfSongsContributedByUser(int userID, Connection connection) {
        List<Integer> songIDs = new ArrayList<>();

        String getListOfSongsContributedByUserQuery = "SELECT song_id FROM songs\n" +
                "WHERE contributed_user_id = ?";

        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getListOfSongsContributedByUserQuery);

            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                songIDs.add(songID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        String addSongQuery = "INSERT INTO songs (artist_id, song_name, is_approved, youtube_link, has_featuring)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        int artistIDParameter = 1;
        int songNameParameter = 2;
        int isApprovedParameter = 3;
        int youTubeLinkParameter = 4;
        int hasFeaturingParameter = 5;

        int isApprovedValue = 0;

        if (song.isApproved()) {
            isApprovedValue = 1;
        }

        int hasFeaturingValue = 1;
        int doesntHaveFeaturingValue = 0;

        PreparedStatement preparedStatement = connection.prepareStatement(addSongQuery);;
        preparedStatement.setInt(artistIDParameter, song.getArtist().getID());
        preparedStatement.setString(songNameParameter, song.getName());
        preparedStatement.setString(youTubeLinkParameter, song.getYouTubeVideoID());
        preparedStatement.setInt(isApprovedParameter, isApprovedValue);

        if (song.hasFeaturedArtists()) {
            preparedStatement.setInt(hasFeaturingParameter, hasFeaturingValue);
        } else {
            preparedStatement.setInt(hasFeaturingParameter, doesntHaveFeaturingValue);
        }

        preparedStatement.execute();
        preparedStatement.close();

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


        PreparedStatement preparedStatement = connection.prepareStatement(approveSongQuery);

        int songIDParameter = 1;

        preparedStatement.setInt(songIDParameter, songID);

        preparedStatement.execute();

        preparedStatement.close();

    }

    /**
     * Retrieves data related to the specified song.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Data related to the specified song.
     */
    public Map<String, Object> getSongData(int songID, Connection connection) {
        String getSongDataQuery = "SELECT * FROM songs\n" +
                "WHERE song_id = ?";
        int songIDParameter = 1;

        Map<String, Object> data = new LinkedHashMap<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSongDataQuery);

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int artistID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID);
                String songName = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME);
                String youTubeVideoID = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK);
                int approvedValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED);
                boolean approved = convertIntToBoolean(approvedValue);
                int hasFeaturingsValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING);
                boolean hasFeaturings = convertIntToBoolean(hasFeaturingsValue);

                data.put(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID, songID);
                data.put(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID, artistID);
                data.put(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME, songName);
                data.put(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED, approved);
                data.put(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK, youTubeVideoID);
                data.put(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING, hasFeaturings);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private boolean convertIntToBoolean(int integer) {
        boolean booleanValue = false;

        switch (integer) {
            case 0:
                booleanValue = false;
                break;
            case 1:
                booleanValue = true;
                break;
            default:
                break;
        }

        return booleanValue;
    }

}
