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
 * This class have methods allowing to get, insert and update data related to songs stored in database.
 */
public class SongsDataAccessObject extends AbstractDataAccessObject {

    public SongsDataAccessObject() {
    }

    public void alterSongName(int songID, String newSongName, Connection connection) {
        String alterSongNameQuery = "UPDATE songs\n" +
                "SET song_name = ?\n" +
                "WHERE song_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(alterSongNameQuery);

            updateStringValueByEntityID(preparedStatement, songID, newSongName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterArtistID(int songID, int newArtistID, Connection connection) {
        String alterArtistIDQuery = "UPDATE songs\n" +
                "SET artist_id = ?\n" +
                "WHERE song_id = ?";

        int artistIDParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(alterArtistIDQuery);
            executePreparedStatementWithMultipleIntegerValues(preparedStatement, newArtistID, songID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterYouTubeLink(int songID, String newYouTubeLink, Connection connection) throws SQLException {
        String alterYouTubeLinkQuery = "UPDATE songs\n" +
                "SET youtube_link = ?\n" +
                "WHERE song_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(alterYouTubeLinkQuery);

        updateStringValueByEntityID(preparedStatement, songID, newYouTubeLink);

    }

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
     * Allows to get a song by song ID.
     * @param songID ID of the song.
     * @return Song which has a specific ID in database.
     */
    public Song getSongByID(int songID, boolean withLyrics, Connection connection) {
        Song song = getSongBySongID(songID, withLyrics, connection);

        return song;
    }


    /**
     * Returns <Close>Song</Close> instance stored in database by its id.
     * @param songID ID of the song.
     * @param withLyrics Indicates if song's lyrics should also be retrieved from databse.
     * @param connection Connection to be used.
     * @return Song retrieved from database.
     */
    private Song getSongBySongID(int songID, boolean withLyrics, Connection connection) {
        Song song = new Song();

        song.setID(songID);

        setDataFromSongsTable(song, connection);

        Artist artist = getSongArtistBySongID(songID, connection);
        song.setArtist(artist);

        if (checkIfSongHasFeaturedArtistsBySongID(songID, connection)) {
            List<Artist> featuredArtists = getListFeaturedArtistsBySongID(songID, connection);
            song.setFeaturedArtists(featuredArtists);
        }

        song.setGenres(getGenresOfSongBySongID(songID, connection));

        if (withLyrics) {
            SongLyrics songLyrics = getSongLyricsBySongID(songID, connection);
            song.setLyrics(songLyrics);
        }

        return song;
    }

    private void setDataFromSongsTable(Song song, Connection connection) {
        String query = "SELECT * FROM songs\n" +
                "WHERE song_id = ?";

        int songIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(songIDParameter, song.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String songName = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME);
                boolean approved = false;
                int approvedValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED);

                if (approvedValue == 1) {
                    approved = true;
                }

                String youTubeVideoID = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK);

                song.setName(songName);
                song.setApproved(approved);
                song.setYouTubeVideoID(youTubeVideoID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks of song has featured artists using song ID.
     * @param songID ID of the song to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if song has featured artists, <Code>False</Code> if it doesn't.
     */
    private boolean checkIfSongHasFeaturedArtistsBySongID(int songID, Connection connection) {

        boolean hasFeaturedArtists = false;

        try {
            String ifSongHasFeaturingArtistsQuery = "SELECT has_featuring\n" +
                    "FROM songs\n" +
                    "WHERE song_id = ?";
            int songIDParameter = 1;

            PreparedStatement preparedStatement = connection.prepareStatement(ifSongHasFeaturingArtistsQuery);
            preparedStatement.setInt(songIDParameter, songID);
            ResultSet resultSet = preparedStatement.executeQuery();

            int hasFeaturingValue = 0;

            while (resultSet.next()) {
                hasFeaturingValue = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING);
            }

            switch (hasFeaturingValue) {
                case 0:
                    hasFeaturedArtists = false;
                    break;
                case 1:
                    hasFeaturedArtists = true;
                    System.out.println("has featured artists");
                    break;
                default:
                    break;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasFeaturedArtists;
    }

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

    private List<String> getGenresOfSongBySongID(int songID, Connection connection) {
        List<String> genres = new ArrayList<>();

        String getGenresOfSongQuery = "SELECT genre_name FROM\n" +
                "(SELECT genre_name, song_id FROM genres_of_songs INNER JOIN genres\n" +
                "ON genres_of_songs.genre_id = genres.genre_id)\n" +
                "AS intermediate_table\n" +
                "INNER JOIN songs\n" +
                "ON intermediate_table.song_id = songs.song_id\n" +
                "WHERE songs.song_id = ?";
        int songIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGenresOfSongQuery);

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String genre = resultSet.getString(DatabaseConstants.ColumnLabels.GenresTable.GENRE_NAME);
                genres.add(genre);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

    public List<Song> getSongsByArtist(Artist artist, Connection connection) {

        List<Song> songs = new ArrayList<>();

        String getSongsByArtistID = "SELECT song_id FROM songs\n" +
                "WHERE artist_id = ?\n" +
                "ORDER BY song_name";

        int artistIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSongsByArtistID);
            preparedStatement.setInt(artistIDParameter, artist.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                boolean withLyrics = false;

                Song song = getSongBySongID(songID, withLyrics, connection);
                songs.add(song);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Song> featuredSongs = getSongsFeaturedByArtist(artist, connection);

        if (!featuredSongs.isEmpty()) {
            featuredSongs.forEach(song -> songs.add(song));
        }

        return songs;
    }

    private List<Song> getSongsFeaturedByArtist(Artist artist, Connection connection) {
        List<Song> songs = new ArrayList<>();

        String getSongsFeaturedByArtistQuery = "SELECT song_id FROM featurings\n" +
                "WHERE artist_id = ?";

        int artistIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSongsFeaturedByArtistQuery);
            preparedStatement.setInt(artistIDParameter, artist.getID());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.FeaturingsTable.SONG_ID);
                boolean withLyrics = false;

                Song song = getSongBySongID(songID, withLyrics, connection);
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    /**
     * Allows to get all IDs and titles of the songs contained in database.
     * @return All IDs and titles of the songs contained in database.
     */
    public Map<Integer, String> getSongIDsWithTitles(boolean approved, Connection connection){
        Map<Integer, String> songTitles = new LinkedHashMap<>();

        try {

            String songTitlesQuery = "SELECT song_id, artist_name, song_name\n" +
                    "FROM songs INNER JOIN artists\n" +
                    "ON songs.artist_id = artists.artist_id\n" +
                    "WHERE is_approved = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(songTitlesQuery);

            int isApprovedParameter = 1;
            int isApprovedValue = 1;
            int notApprovedValue = 0;

            if (approved == true) {
                preparedStatement.setInt(isApprovedParameter, isApprovedValue);
            } else {
                preparedStatement.setInt(isApprovedParameter, notApprovedValue);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                int songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                String artistName = resultSet.getString(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_NAME);
                String songName = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME);

                song.setArtist(new Artist(artistName));
                song.setName(songName);

                if (checkIfSongHasFeaturedArtistsBySongID(songID, connection)) {
                    song.setFeaturedArtists(getListFeaturedArtistsBySongID(songID, connection));
                }

                songTitles.put(songID, song.getTitle());
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songTitles;
    }

    /**
     * Allows to get song artist by song ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Artist of the song retrieved from the database.
     */
    private Artist getSongArtistBySongID(int songID, Connection connection) {
        Artist artist = new Artist();

        try {
            String songArtistQuery = "SELECT artist_name\n" +
                    "FROM songs INNER JOIN artists\n" +
                    "ON songs.artist_id = artists.artist_id\n" +
                    "WHERE song_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(songArtistQuery);
            preparedStatement.setInt(1, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            String artistName = new String();

            while (resultSet.next()) {
                artistName = resultSet.getString("artist_name");
            }

            artist.setName(artistName);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artist;
    }

    /**
     * Retrieves song lyrics from database using song ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Lyrics of the song.
     */
    private SongLyrics getSongLyricsBySongID(int songID, Connection connection) {
        SongLyrics songLyrics = new SongLyricsComposite();

        String songLyricsQuery = "SELECT song_part, content\n" +
                "FROM websitedatabase.lines\n" +
                "WHERE song_id = ?\n AND is_deleted = ?\n" +
                "ORDER BY line_position;";

        int songIDParameter = 1;
        int isDeletedParameter = 2;

        int isDeletedValue = 0;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(songLyricsQuery);
            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(isDeletedParameter, isDeletedValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<List<String>> listOfLyricsPartsAsStringValues = new ArrayList<>();

            while (resultSet.next()) {
                listOfLyricsPartsAsStringValues.add(Arrays.asList(resultSet.getString(DatabaseConstants.ColumnLabels.LinesTable.SONG_PART),
                        resultSet.getString(DatabaseConstants.ColumnLabels.LinesTable.CONTENT)));
            }

            songLyrics = buildLyricsFromStringValues(listOfLyricsPartsAsStringValues);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songLyrics;
    }



    /**
     * Retrieves list of featured artists of the specific song.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return List of featured artists of the specific song.
     */
    private List<Artist> getListFeaturedArtistsBySongID(int songID, Connection connection) {
        List<Artist> featuredArtists = new ArrayList<>();

        try {
            String songFeaturedArtistsQuery = "SELECT artist_name, artists.artist_id\n" +
                    "FROM featurings INNER JOIN artists\n" +
                    "ON featurings.artist_id = artists.artist_id\n" +
                    "WHERE song_id = ?;";
            int songIDParameter = 1;

            PreparedStatement preparedStatement = connection.prepareStatement(songFeaturedArtistsQuery);
            preparedStatement.setInt(songIDParameter, songID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String artistName = resultSet.getString(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_NAME);
                int artistID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID);

                Artist artist = new Artist();
                artist.setID(artistID);
                artist.setName(artistName);

                featuredArtists.add(artist);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return featuredArtists;
    }

    /**
     * Builds lyrics of the song.
     * @param listOfLyricsPartsAsStringValues List which contains lines of the lyrics.
     *                                        This list has a map-like structure. Each entry has two values:
     *                                        1. The first value is a part of the song which contains a specific line.
     *                                        2. The second value is a content of the line.
     *                                        For example:
     *                                        "chorus", "I've become so numb" — means that the entry contains the line
     *                                        "I've become so numb" which belongs to the chorus.
     * @return Lyrics of the song.
     */
    private SongLyrics buildLyricsFromStringValues(List<List<String>> listOfLyricsPartsAsStringValues) {
        SongLyrics songLyrics = new SongLyricsComposite();

        int lyricsPartType = 0;
        int lineContent = 1;
        SongLyricsComposite lyricsPart = null;

        for (int index = 0; index < listOfLyricsPartsAsStringValues.size(); index++) {

            List<String> entry = listOfLyricsPartsAsStringValues.get(index);

            String partType = entry.get(lyricsPartType);

            String content = entry.get(lineContent);

            switch (partType) {
                case "verse":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.VERSE);
                    }
                    break;
                case "chorus":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.CHORUS);
                    }
                    break;
                case "hook":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.HOOK);
                    }
                    break;
                case "bridge":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.BRIDGE);
                    }
                    break;
                case "intro":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.INTRO);
                    }
                    break;
                case "outro":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.OUTRO);
                    }
                    break;
                case "other":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.OTHER);
                        break;
                    }
                default:
                    break;
            }

            lyricsPart.add(new Line(content));

            int nextIndex = index + 1;

            String nextType = null;

            if (nextIndex != listOfLyricsPartsAsStringValues.size()) {
                nextType = listOfLyricsPartsAsStringValues.get(nextIndex).get(lyricsPartType);
            }

            if (!partType.equals(nextType)) {
                songLyrics.add(lyricsPart);
                lyricsPart = null;
            }
        }

        return songLyrics;
    }

    /**
     * Adds data related to song into <Code>songs</Code> table.
     * @param song Song which data is to be added.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into database.
     */
    public void addDataToSongsTable(Song song, Connection connection) throws SQLException {
        int lastID = getLastSongID(connection);
        song.setID(lastID + 1);

        String addSongQuery = "INSERT INTO songs (song_id, artist_id, song_name, is_approved, youtube_link, has_featuring)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        int songIDParameter = 1;
        int artistIDParameter = 2;
        int songNameParameter = 3;
        int isApprovedParameter = 4;
        int youTubeLinkParameter = 5;
        int hasFeaturingParameter = 6;

        int isApprovedValue = 0;

        if (song.isApproved()) {
            isApprovedValue = 1;
        }

        int hasFeaturingValue = 1;
        int doesntHaveFeaturingValue = 0;

        PreparedStatement preparedStatement = connection.prepareStatement(addSongQuery);
        preparedStatement.setInt(songIDParameter, song.getID());
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
     */
    public void approveSong(int songID, Connection connection) throws SQLException {

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
     * Retrieves the ID of the last song from the databsae.
     * @param connection Connection to be used.
     * @return ID of the last song.
     */
    private int getLastSongID(Connection connection) {
        int lastID = 0;

        try {
            String getLastSongIDQuery = "SELECT max(song_id) FROM songs";
            PreparedStatement preparedStatement = connection.prepareStatement(getLastSongIDQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastID = resultSet.getInt("max(song_id)");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastID;
    }
}
