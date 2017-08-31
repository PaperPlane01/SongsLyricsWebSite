package kz.javalab.songslyricswebsite.dataaccessobject;



import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.SongAddingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by PaperPlane on 30.07.2017.
 * This class have methods allowing to get, insert and update data related to songs stored in database.
 */
public class SongDataAccessObject {

    public SongDataAccessObject() {
    }

    public void alterSongName(int songID, String newSongName, Connection connection) {
        String alterSongNameQuery = "UPDATE songs\n" +
                "SET song_name = ?\n" +
                "WHERE song_id = ?";
        int songNameParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(alterSongNameQuery);

            preparedStatement.setString(songNameParameter, newSongName);
            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();

            preparedStatement.close();
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
            preparedStatement.setInt(artistIDParameter, newArtistID);
            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterYouTubeLink(int songID, String newYouTubeLink, Connection connection) {
        String alterYouTubeLinkQuery = "UPDATE songs\n" +
                "SET youtube_link = ?\n" +
                "WHERE song_id = ?";

        int youTubeLinkParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(alterYouTubeLinkQuery);

            preparedStatement.setString(youTubeLinkParameter, newYouTubeLink);
            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Allows to get a song by song ID.
     * @param songID ID of the song.
     * @return Song which has a specific ID in database.
     */
    public Song getSongByID(int songID, Connection connection) {

        boolean withLyrics = true;

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

        Artist artist = getSongArtistBySongID(songID, connection);
        song.setArtist(artist);

        if (checkIfSongHasFeaturedArtistsBySongID(songID, connection)) {
            List<Artist> featuredArtists = getListFeaturedArtistsBySongID(songID, connection);
            song.setFeaturedArtists(featuredArtists);
        }

        String songName = getSongNameBySongID(songID, connection);
        song.setName(songName);

        song.setID(songID);

        song.setGenres(getGenresOfSongBySongID(songID, connection));

        if (withLyrics) {
            SongLyrics songLyrics = getSongLyricsBySongID(songID, connection);
            song.setLyrics(songLyrics);
        }

        return song;
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
        int songIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkSongQuery);

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }
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
                System.out.println(genre);
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
     * Allows to get Youtube link of the song by its ID.
     * @param songID ID of the song.
     * @return Youtube link of the song by its ID.
     */
    public String getYouTubeLinkBySongID(int songID, Connection connection) {
        String youTubeLink = new String();

        try {
            String songYouTubeLinkQuery = "SELECT youtube_link\n" +
                    "FROM songs\n" +
                    "WHERE song_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(songYouTubeLinkQuery);
            preparedStatement.setInt(1, songID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                youTubeLink = resultSet.getString(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return youTubeLink;
    }

    /**
     * Allows to save all the information about the song (song name, artists, featured artists, song genres and lyrics) into database.
     * @param song Song to be inserted into database.
     */
    public void addSongToDatabase(Song song, String youTubeLink, Connection connection) throws SongAddingException {
        addDataToSongsTable(song, connection);
        addSongGenresToDatabase(song, connection);
        addSongGenreMatchesToDatabase(song, connection);
        addFeaturinsgMatchesToDatabase(song, connection);
        addSongLyricsToDatabase(song, connection);

        if (youTubeLink != null) {
            if (!youTubeLink.isEmpty()) {
                addYouTubeLinkToDatabase(song.getID(), youTubeLink, connection);
            }
        }

    }

    /**
     * Adds data related to song into <Code>songs</Code> table.
     * @param song Song which data is to be added.
     * @param connection Connection to be used.
     */
    private void addDataToSongsTable(Song song, Connection connection) {
        int lastID = getLastSongID(connection);
        song.setID(lastID + 1);

        try {
            String addSongQuery = "INSERT INTO songs (song_id, artist_id, song_name, is_approved, has_featuring)\n" +
                    "VALUES (?, ?, ?, ?, ?)";

            int songIDParameter = 1;
            int artistIDParameter = 2;
            int songNameParameter = 3;
            int isApprovedParameter = 4;
            int hasFeaturingParameter = 5;

            int isApprovedValue = 0;
            int hasFeaturingValue = 1;
            int doesntHaveFeaturingValue = 0;

            PreparedStatement preparedStatement = connection.prepareStatement(addSongQuery);
            preparedStatement.setInt(songIDParameter, song.getID());
            preparedStatement.setInt(artistIDParameter, song.getArtist().getID());
            preparedStatement.setString(songNameParameter, song.getName());
            preparedStatement.setInt(isApprovedParameter, isApprovedValue);

            if (song.hasFeaturedArtists()) {
                preparedStatement.setInt(hasFeaturingParameter, hasFeaturingValue);
            } else {
                preparedStatement.setInt(hasFeaturingParameter, doesntHaveFeaturingValue);
            }

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Inserts all genres of the song into database.
     * @param song Song genres of which are to be inserted.
     * @param connection Connection to be used.
     */
    private void addSongGenresToDatabase(Song song, Connection connection) {

        if (song.getGenres() != null) {
            if (!song.getGenres().isEmpty()) {
                song.getGenres().forEach(genre -> {
                    if (!checkIfGenreExists(genre, connection)) {
                        addGenreToDatabase(genre, connection);
                    }
                });
            }
        }
    }

    /**
     * Inserts genre into database.
     * @param genre Genre to be inserted.
     * @param connection Connection to be used.
     */
    private void addGenreToDatabase(String genre, Connection connection) {
        int lastGenreID = getLastGenreID(connection);

        String addGenreQuery = "INSERT INTO genres (genre_id, genre_name)\n" +
                "VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addGenreQuery);

            int genreIDParameter = 1;
            int genreNameParameter = 2;

            preparedStatement.setInt(genreIDParameter, lastGenreID + 1);
            preparedStatement.setString(genreNameParameter, genre);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts all "song-genre" matches into database.
     * This method uses <Code>songID</Code> parameter, so it should be filled with proper data.
     * @param song
     * @param connection Connection to be used.
     */
    private void addSongGenreMatchesToDatabase(Song song, Connection connection) {
        String addSongGenreMatch = "INSERT INTO genres_of_songs (song_id, genre_id)\n" +
                "VALUES (?, ?)";

        song.getGenres().forEach(genre -> {
            int genreID = getGenreIDByGenreName(genre, connection);

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addSongGenreMatch);

                int songIDParameter = 1;
                int genreIDParameter = 2;

                preparedStatement.setInt(songIDParameter, song.getID());
                preparedStatement.setInt(genreIDParameter, genreID);

                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Inserts all "song-featuring" matches into database.
     * @param song
     * @param connection
     */
    private void addFeaturinsgMatchesToDatabase(Song song, Connection connection) {
        String addFeaturingQuery = "INSERT INTO featurings (artist_id, song_id)\n" +
                "VALUES (?, ?)";

        song.getFeaturedArtists().forEach(featuredArtist -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addFeaturingQuery);

                int artistIDParameter = 1;
                int songIDParameter = 2;

                preparedStatement.setInt(artistIDParameter, featuredArtist.getID());
                preparedStatement.setInt(songIDParameter, song.getID());

                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Inserts lyrics of the song into database.
     * @param song Song which lyrics are to be inserted into database.
     * @param connection Connection to be used.
     */
    private void addSongLyricsToDatabase(Song song, Connection connection) throws SongAddingException {
        String addLineQuery = "INSERT INTO websitedatabase.lines (song_id, content, song_part, line_position)\n" +
                "VALUES (?, ?, ?, ?)";

        int songIDParameter = 1;
        int contentParameter = 2;
        int songPartParameter = 3;
        int linePositionParameter = 4;

        int linePosition = 0;

        for (SongLyrics songPart : song.getLyrics().getComponents()) {

            for (SongLyrics line : songPart.getComponents()) {
                linePosition++;
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(addLineQuery);

                    preparedStatement.setInt(songIDParameter, song.getID());
                    preparedStatement.setString(contentParameter, line.toString());
                    preparedStatement.setString(songPartParameter, songPart.getType().toString().toLowerCase());
                    preparedStatement.setInt(linePositionParameter, linePosition);

                    preparedStatement.execute();

                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SongAddingException();
                }
            }
        }

    }

    /**
     * Adds YouTube link of the specific song to database.
     * @param songID ID of the song.
     * @param youTubeLink YouTube link.
     * @param connection Connection to be used.
     */
    private void addYouTubeLinkToDatabase(int songID, String youTubeLink, Connection connection) {
        String addYouTubeLinkQuery = "UPDATE songs\n" +
                "SET youtube_link = ?\n" +
                "WHERE song_id = ?";

        int youTubeLinkParameter = 1;
        int songIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addYouTubeLinkQuery);

            preparedStatement.setString(youTubeLinkParameter, youTubeLink);
            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks database contains a specific genre.
     * @param genre Genre to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if genre has already been inserted into database, <Code>False</Code> if not.
     */
    private boolean checkIfGenreExists(String genre, Connection connection) {
        boolean result = false;

        try {
            String checkGenreQuery = "SELECT genre_id FROM genres\n" +
                    "WHERE genre_name = ?";
            int genreNameParameter = 1;

            PreparedStatement preparedStatement = connection.prepareStatement(checkGenreQuery);
            preparedStatement.setString(genreNameParameter, genre);

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
     * Retrieves genre's ID by its name.
     * @param genre Genre which ID is to be retrieved.
     * @param connection Connection to be used.
     * @return ID of the genre.
     */
    private int getGenreIDByGenreName(String genre, Connection connection) {
        int id = 0;

        String getGenreIDQuery = "SELECT genre_id FROM genres\n" +
                "WHERE genre_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGenreIDQuery);
            int genreIDParameter = 1;

            preparedStatement.setString(genreIDParameter, genre);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresTable.GENRE_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Retrieves last genre ID from the database.
     * @param connection Connection to be used.
     * @return Last genre ID.
     */
    private int getLastGenreID(Connection connection) {
        int lastID = 0;

        try {
            String getLastGenreIDQuery = "SELECT max(genre_id) FROM genres";
            PreparedStatement preparedStatement = connection.prepareStatement(getLastGenreIDQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastID = resultSet.getInt("max(genre_id)");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastID;
    }

    /**
     * Allows to get ID of the specific song.
     * This method uses artist ID and song name to identify the song, so this fields must be filled with proper data.
     * @param song Song to be identified.
     * @return ID of the song.
     */
    public int getSongID(Song song, Connection connection) {
        int songID = 0;

        try {
            String getSongIDQuery = "SELECT song_id FROM songs\n" +
                    "WHERE artist_id = ? AND song_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getSongIDQuery);
            preparedStatement.setInt(1, song.getArtist().getID());
            preparedStatement.setString(2, song.getName());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                songID = resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.SONG_ID);
                System.out.println(songID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songID;
    }

    /**
     * Approves song by its ID.
     * @param songID ID of the song which is to be approved.
     */
    public void approveSong(int songID, Connection connection) {

        String approveSongQuery = "UPDATE songs\n" +
                "SET is_approved = 1\n" +
                "WHERE song_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(approveSongQuery);

            int songIDParameter = 1;

            preparedStatement.setInt(songIDParameter, songID);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if song is appproved.
     * @param songID ID of the song to be checked.
     * @return <Code>True</Code> if song is approved, <Code>False</Code> if not.
     */
    public boolean checkIfSongApprovedBySongID (int songID, Connection connection) {
        boolean result = false;

        String checkIfSongApprovedQuery = "SELECT is_approved\n" +
                "FROM songs\n" +
                "WHERE song_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkIfSongApprovedQuery);

            int songIDParameter = 1;

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED) == 1) {
                    result = true;
                } else {
                    result = false;
                }
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
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
     * Allows to get song name by song ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Name of the song retrieved from the database.
     */
    private String getSongNameBySongID(int songID, Connection connection) {
        String songName = new String();

        try {
            String songNameQuery = "SELECT song_name\n" +
                    "FROM songs\n" +
                    "WHERE song_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(songNameQuery);
            preparedStatement.setInt(1, songID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                songName = resultSet.getString("song_name");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songName;
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
