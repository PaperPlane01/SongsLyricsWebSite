package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.*;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.*;
import kz.javalab.songslyricswebsite.util.SongRetriever;
import kz.javalab.songslyricswebsite.util.Songs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class SongsManager {

    private RequestWrapper requestWrapper;

    public SongsManager() {
    }

    public SongsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    /**
     * Alters song.
     * @throws NoSuchSongException Thrown if there is no song with specified ID.
     * @throws LyricsParsingException Thrown if song lyrics are invalid.
     * @throws InvalidSongNameException Thrown if song name is invalid.
     * @throws InvalidFeaturedArtistsException Thrown if list of featured artists if invalid.
     * @throws InvalidYouTubeVideoIDException Thrown if YouTube Video ID is invalid.
     * @throws InvalidSongGenresException Thrown if list of genres is invalid.
     * @throws InvalidArtistNameException Thrown if artist name is invalid.
     * @throws TooLongOrEmptyLyricsException Thrown if song lyrics are too long or empty.
     * @throws InvalidFeaturedArtistNameException Thrown if one of featured artists' name is invalid.
     * @throws SongAlteringException Thrown if there is exception occurred when attempted to commit changes.
     */
    public void alterSong() throws NoSuchSongException, LyricsParsingException, InvalidSongNameException, InvalidFeaturedArtistsException, InvalidYouTubeVideoIDException, InvalidSongGenresException, InvalidArtistNameException, TooLongOrEmptyLyricsException, InvalidFeaturedArtistNameException, SongAlteringException {
       Connection connection = ConnectionPool.getInstance().getConnection();
       int songID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SONG_ID));

       SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

       if (!songsDataAccessObject.checkIfSongExists(songID, connection)) {
           throw new NoSuchSongException();
       }

       SongRetriever songRetriever = new SongRetriever();

       Song alteredSong = songRetriever.retrieveSongFromRequest(requestWrapper.getRequest());

        try {
            connection.setAutoCommit(false);
            alterSong(songID, alteredSong, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                throw new SongAlteringException();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new SongAlteringException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * Received altered song and commits changes to database.
     * @param songID ID of the song to be altered.
     * @param alteredSong Altered song.
     * @throws NoSuchSongException Thrown if there is no song with this ID.
     * @throws SongAlteringException Thrown if some exception occurred when tried to alter song.
     */
    private void alterSong(int songID, Song alteredSong, Connection connection) throws NoSuchSongException, SongAlteringException, SQLException {

        Song oldSong = getSongByID(songID, connection);

        alterSongName(alteredSong, oldSong, connection);

        alterArtistID(alteredSong, oldSong, connection);

        alterFeaturings(alteredSong, oldSong, connection);

        alterGenresOfSong(alteredSong, oldSong, connection);

        alterLyrics(alteredSong, oldSong, connection);

        alterYouTubeLink(songID, alteredSong.getYouTubeVideoID(), oldSong.getYouTubeVideoID(), connection);
    }

    /**
     * Compares names of the song received from user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterSongName(Song alteredSong, Song oldSong, Connection connection) {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (!alteredSong.getName().equals(oldSong.getName())) { ;
            songsDataAccessObject.alterSongName(oldSong.getID(), alteredSong.getName(), connection);
        }
    }

    /**
     * Compares artist of the song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterArtistID(Song alteredSong, Song oldSong, Connection connection) throws SongAlteringException, SQLException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        if (!alteredSong.getArtist().getName().trim().equals(oldSong.getArtist().getName().trim())) {
            artistDataAccessObject.addArtistToDatabase(alteredSong.getArtist(), connection);

            alteredSong.getArtist().setID(artistDataAccessObject.getArtistID(alteredSong.getArtist(), connection));
            songsDataAccessObject.alterArtistID(oldSong.getID(), alteredSong.getArtist().getID(), connection);
        }
    }

    /**
     * Compares featured artists of the song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterFeaturings(Song alteredSong, Song oldSong, Connection connection) throws SongAlteringException, SQLException {
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();
        FeaturingsDataAccessObject featuringsDataAccessObject = new FeaturingsDataAccessObject();

        if (alteredSong.hasFeaturedArtists()) {
            for (Artist featuredArtist : alteredSong.getFeaturedArtists()) {
                if (!artistDataAccessObject.checkIfArtistExists(featuredArtist, connection)) {
                    artistDataAccessObject.addArtistToDatabase(featuredArtist, connection);
                }

                featuredArtist.setID(artistDataAccessObject.getArtistID(featuredArtist, connection));
                featuringsDataAccessObject.addNewFeaturing(featuredArtist.getID(), alteredSong.getID(), connection);

            }

            if (oldSong.hasFeaturedArtists()) {
                for (Artist featuredArtist : oldSong.getFeaturedArtists()) {
                    if (!alteredSong.getFeaturedArtists().contains(featuredArtist)) {
                        int featuringID = featuringsDataAccessObject.getFeaturingID(featuredArtist.getID(), oldSong.getID(), connection);
                        featuringsDataAccessObject.markFeatuirngAsDeleted(featuringID, connection);
                    }
                }
            }
        } else if (oldSong.hasFeaturedArtists()) {
            for (Artist featuredArtist : oldSong.getFeaturedArtists()) {
                int featuringID = featuringsDataAccessObject.getFeaturingID(featuredArtist.getID(), oldSong.getID(), connection);
                featuringsDataAccessObject.markFeatuirngAsDeleted(featuringID, connection);
            }
        }
    }

    /**
     * Compares genres of song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterGenresOfSong(Song alteredSong, Song oldSong, Connection connection) throws SQLException {
        GenresDataAccessObject genresDataAccessObject = new GenresDataAccessObject();
        GenresOfSongsDataAccessObject genresOfSongsDataAccessObject = new GenresOfSongsDataAccessObject();

        if (!alteredSong.getGenres().isEmpty()) {
            for (String genre : alteredSong.getGenres()) {
                if (!genresDataAccessObject.checkIfGenreExists(genre, connection)) {
                    genresDataAccessObject.addGenreToDatabase(genre, connection);
                }
            }

            for (String genre : oldSong.getGenres()) {
                if (!oldSong.getGenres().contains(genre)) {
                    int genreID = genresDataAccessObject.getGenreID(genre, connection);
                    genresOfSongsDataAccessObject.addGenreOfSong(alteredSong.getID(), genreID, connection);
                }
            }

            if (!oldSong.getGenres().isEmpty()) {
                for (String genre : oldSong.getGenres()) {
                    if (!alteredSong.getGenres().contains(genre)) {
                        int genreID = genresDataAccessObject.getGenreID(genre, connection);
                        int matchID = genresOfSongsDataAccessObject.getSongGenreMatchID(alteredSong.getID(), genreID, connection);
                        genresOfSongsDataAccessObject.markSongGenreMatchAsDeleted(matchID, connection);
                    }
                }
            }
        } else if (!oldSong.getGenres().isEmpty()) {
            for (String genre : oldSong.getGenres()) {
                int genreID = genresDataAccessObject.getGenreID(genre, connection);
                int matchID = genresOfSongsDataAccessObject.getSongGenreMatchID(oldSong.getID(), genreID, connection);
                genresOfSongsDataAccessObject.markSongGenreMatchAsDeleted(matchID, connection);
            }
        }
    }

    /**
     * Compares lyrics of song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterLyrics(Song alteredSong, Song oldSong, Connection connection) throws SQLException {
        if (oldSong.getLyrics().equals(alteredSong.getLyrics())) { ;
            return;
        }

        alterLines(alteredSong, oldSong, connection);

        alterLyricsPartTypes(alteredSong, oldSong, connection);
    }

    /**
     * Compares lines of song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterLines(Song alteredSong, Song oldSong, Connection connection) throws SQLException {
        List<Line> alteredLines = Songs.getLines(alteredSong);
        List<Line> oldLines = Songs.getLines(oldSong);

        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

        //Do nothing if there is no difference between old lyrics and edited lyrics.
        if (alteredLines.equals(oldLines)) {
            return;
        }

        if (alteredLines.size() == oldLines.size()) {
            for (int linePosition = 0; linePosition < alteredLines.size(); linePosition++) {
                if (!alteredLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLine(lineID, alteredLines.get(linePosition), connection);
                }
            }
        } else if (alteredLines.size() < oldLines.size()) {
            int linePosition;
            for (linePosition = 0; linePosition < alteredLines.size(); linePosition++) {
                if (!alteredLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLine(lineID, alteredLines.get(linePosition), connection);
                }
            }

            //Mark remaining lines as deleted.
            while (linePosition < oldLines.size()) {
                int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                linesDataAccessObject.markLineAsDeleted(lineID, connection);
                linePosition++;
            }
        } else if (alteredLines.size() > oldLines.size()) {
            int linePosition;

            for (linePosition = 0; linePosition < oldLines.size(); linePosition++) {
                if (!alteredLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLine(lineID, alteredLines.get(linePosition), connection);
                }
            }

            while (linePosition < alteredLines.size()) {
                SongLyricsPartType songLyricsPartType = Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition);
                linesDataAccessObject.addLineToDatabase(linePosition, alteredSong.getID(), alteredLines.get(linePosition), songLyricsPartType, connection);
                linePosition++;
            }
        }
    }

    /**
     * Compares lyrics part types of the song received from the user and song stored in database, and applies changes (if any).
     * @param alteredSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void alterLyricsPartTypes(Song alteredSong, Song oldSong, Connection connection) throws SQLException {
        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

        if (Songs.getNumberOfLines(alteredSong) == Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(alteredSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
                }
            }
        } else if (Songs.getNumberOfLines(alteredSong) > Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(oldSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
                }
            }

            while (linePosition < Songs.getNumberOfLines(alteredSong)) {
                SongLyricsPartType songLyricsPartType = Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition);
                linesDataAccessObject.addLineToDatabase(linePosition, alteredSong.getID(), Songs.getLineAt(alteredSong, linePosition), songLyricsPartType, connection);
                linePosition++;
            }
        } else if (Songs.getNumberOfLines(alteredSong)  <Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(alteredSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
                }
            }
            //no need to do anything else as next lines have been marked as deleted
        }
    }

    /**
     * Compares YouTube video ID received from the user and YouTube video ID stored in database, and applies changes (if any).
     * @param songID ID of the sing
     * @param newYouTubeVideoID YouTube video ID received from the user.
     * @param connection Connection to be used.
     */
    private void alterYouTubeLink(int songID, String newYouTubeVideoID, String oldYouTubeVideoID, Connection connection) throws SQLException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (!newYouTubeVideoID.trim().equals(oldYouTubeVideoID)) {
            songsDataAccessObject.alterYouTubeLink(songID, newYouTubeVideoID, connection);
        }
    }

    /**
     * Retrieves song with the specific ID.
     * @param songID ID of song.
     * @return <Code>Song</Code> object.
     * @throws NoSuchSongException Thrown if there is no song with such ID.
     */
    public Song getSongByID(int songID) throws NoSuchSongException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        Song song = getSongByID(songID, connection);

        ConnectionPool.getInstance().returnConnection(connection);

        return song;
    }

    /**
     * Retrieves song with the specific ID
     * @param songID ID of song.
     * @param connection Connection to be used.
     * @return <Code>Song</Code> object.
     * @throws NoSuchSongException Thrown if there is no song with such ID.
     */
    private Song getSongByID(int songID, Connection connection) throws NoSuchSongException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (songsDataAccessObject.checkIfSongExists(songID, connection)) {
            boolean withLyrics = true;
            return songsDataAccessObject.getSongByID(songID, withLyrics, connection);
        } else {
            throw new NoSuchSongException();
        }
    }

    public Map<Integer, String> getSongIDsWithTitles(boolean approved) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        Map<Integer, String> songIDsWithTitles =  songsDataAccessObject.getSongIDsWithTitles(approved, connection);

        ConnectionPool.getInstance().returnConnection(connection);

        return songIDsWithTitles;
    }

    /**
     * Adda new song to database.
     * @throws SongAddingException Thrown if some error occurred when attempted to insert information into database.
     * @throws SuchSongAlreadyExistsException Thrown if such song already exists.
     * @throws LyricsParsingException Thrown if song lyrics are invalid.
     * @throws InvalidSongNameException Thrown if song name is invalid.
     * @throws InvalidFeaturedArtistsException Thrown if list of featured artists if invalid.
     * @throws InvalidYouTubeVideoIDException Thrown if YouTube Video ID is invalid.
     * @throws InvalidSongGenresException Thrown if list of genres is invalid.
     * @throws InvalidArtistNameException Thrown if artist name is invalid.
     * @throws TooLongOrEmptyLyricsException Thrown if song lyrics are too long or empty.
     * @throws InvalidFeaturedArtistNameException Thrown if one of featured artists' name is invalid.
     */
    public void addSongToDatabase() throws SongAddingException, SuchSongAlreadyExistsException, LyricsParsingException, InvalidSongNameException, InvalidFeaturedArtistsException, InvalidYouTubeVideoIDException, InvalidSongGenresException, InvalidArtistNameException, TooLongOrEmptyLyricsException, InvalidFeaturedArtistNameException {
       Connection connection = ConnectionPool.getInstance().getConnection();
       SongRetriever songRetriever = new SongRetriever();

       Song song = songRetriever.retrieveSongFromRequest(requestWrapper.getRequest());

       User user = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

       if (user == null) {
           song.setApproved(false);
       } else if (user.getUserType() == UserType.MODERATOR) {
           song.setApproved(true);
       } else {
           song.setApproved(false);
       }

       SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
       ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();
       FeaturingsDataAccessObject featuringsDataAccessObject = new FeaturingsDataAccessObject();
       GenresDataAccessObject genresDataAccessObject = new GenresDataAccessObject();
       GenresOfSongsDataAccessObject genresOfSongsDataAccessObject = new GenresOfSongsDataAccessObject();
       LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

       if (songsDataAccessObject.checkIfSongExists(song, connection)) {
           throw new SuchSongAlreadyExistsException();
       }

       try {
           connection.setAutoCommit(false);

           songsDataAccessObject.addDataToSongsTable(song, connection);
           artistDataAccessObject.addArtistToDatabase(song.getArtist(), connection);

           if (song.hasFeaturedArtists()) {
               for (Artist featuredArtist : song.getFeaturedArtists()) {
                   if (!artistDataAccessObject.checkIfArtistExists(featuredArtist, connection)) {
                       artistDataAccessObject.addArtistToDatabase(featuredArtist, connection);
                   }

                   featuredArtist.setID(artistDataAccessObject.getArtistID(featuredArtist, connection));
                   featuringsDataAccessObject.addNewFeaturing(featuredArtist.getID(), song.getID(), connection);
               }
           }

           if (!song.getGenres().isEmpty()) {
               for (String genre : song.getGenres()) {
                   if (genresDataAccessObject.checkIfGenreExists(genre, connection)) {
                       genresDataAccessObject.addGenreToDatabase(genre, connection);
                   }

                   int genreID = genresDataAccessObject.getGenreID(genre, connection);
                   genresOfSongsDataAccessObject.addGenreOfSong(song.getID(), genreID, connection);
               }
           }

           linesDataAccessObject.addSongLyricsToDatabase(song, connection);

       } catch (SQLException e) {
           e.printStackTrace();
           try {
               connection.rollback();
               throw new SongAddingException();
           } catch (SQLException e1) {
               throw new SongAddingException();
           }
       } finally {
           ConnectionPool.getInstance().returnConnection(connection);
       }
    }

    /**
     * Returns list of songs performed by specific artist.
     * @return List of songs performed by specific artist.
     */
    public List<Song> getSongsByArtist() {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        String artistName = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.ARTIST_NAME);

        Artist artist = new Artist(artistName);

        artist.setID(artistDataAccessObject.getArtistID(artist, connection));
        List<Song> songsByArtist =  songsDataAccessObject.getSongsByArtist(artist, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return songsByArtist;
    }

    /**
     * Returns list of recently added songs.
     * @return List of recently added songs.
     */
    public List<Song> getRecentlyAddedSongs() {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        List<Song> songs = new ArrayList<>();
        int numberOfSongs = 10;
        List<Integer> songsIDs = songsDataAccessObject.getIDsOfRecentlyAddedSongs(numberOfSongs, connection);

        for (Integer songID : songsIDs) {
            boolean withLyrics = false;
            Song song = songsDataAccessObject.getSongByID(songID, withLyrics, connection);
            songs.add(song);
        }

        ConnectionPool.getInstance().returnConnection(connection);

        return songs;
    }

    /**
     * Marks song with specific ID as approved.
     * @param songID ID of song which is to be approved.
     */
    public void approveSong(int songID) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        try {
            connection.setAutoCommit(false);
            songsDataAccessObject.approveSong(songID, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }
}
