package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.connectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
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
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for managing songs.
 */
public class SongsManager {

    /**
     * <Code>RequestWrapper</Code> which contains data sent by user.
     */
    private RequestWrapper requestWrapper;
    private static Logger logger = Logger.getLogger(SongsManager.class.getName());

    /**
     * Constructs <Code>SongsManager</Code> instance.
     */
    public SongsManager() {
    }

    /**
     * Constructs <Code>SongsManager</Code> instance with pre-defined requestWrapper.
     * @param requestWrapper <Code>RequestWrapper</Code> instance.
     */
    public SongsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    /**
     * Returns requestWrapper.
     * @return requestWrapper.
     */
    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    /**
     * Sets new requestWrapper.
     * @param requestWrapper New <Code>RequestWrapper</Code> instance which is to be set.
     */
    public void setRequestWrapper(RequestWrapper requestWrapper) {
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
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public void updateSong() throws NoSuchSongException, LyricsParsingException, InvalidSongNameException, InvalidFeaturedArtistsException, InvalidYouTubeVideoIDException, InvalidSongGenresException, InvalidArtistNameException, TooLongOrEmptyLyricsException, InvalidFeaturedArtistNameException, SongAlteringException, DataAccessException {
       Connection connection = ConnectionPool.getInstance().getConnection();
       int songID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SONG_ID));

       SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

       if (!songsDataAccessObject.checkIfSongExists(songID, connection)) {
           throw new NoSuchSongException();
       }

       SongRetriever songRetriever = new SongRetriever();

       Song updatedSong = songRetriever.retrieveSongFromRequest(requestWrapper.getRequest());

        try {
            connection.setAutoCommit(false);
            updatedSong.setID(songID);
            updateSong(songID, updatedSong, connection);
            connection.commit();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_UPDATING_SONG, e);
            try {
                connection.rollback();
                throw new SongAlteringException();
            } catch (SQLException e1) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK);
                throw new SongAlteringException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * Received updated song and commits changes to database.
     * @param songID ID of the song to be updated.
     * @param updatedSong Altered song.
     * @throws NoSuchSongException Thrown if there is no song with this ID.
     * @throws SongAlteringException Thrown if some exception occurred when tried to update song.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateSong(int songID, Song updatedSong, Connection connection) throws NoSuchSongException, SongAlteringException, SQLException, DataAccessException {

        boolean withLyrics = true;

        Song oldSong = getSongByID(songID, withLyrics, connection);

        updateSongName(updatedSong, oldSong, connection);

        updateArtistID(updatedSong, oldSong, connection);

        updateFeaturings(updatedSong, oldSong, connection);

        updateGenresOfSong(updatedSong, oldSong, connection);

        updateLyrics(updatedSong, oldSong, connection);

        updateYouTubeLink(songID, updatedSong.getYouTubeVideoID(), oldSong.getYouTubeVideoID(), connection);
    }

    /**
     * Compares names of the song received from user and song stored in database, and applies changes (if any).
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     */
    private void updateSongName(Song updatedSong, Song oldSong, Connection connection) throws SQLException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (!updatedSong.getName().equals(oldSong.getName())) { ;
            songsDataAccessObject.updateSongName(oldSong.getID(), updatedSong.getName(), connection);
        }
    }

    /**
     * Compares artist of the song received from the user and song stored in database, and applies changes (if any).
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateArtistID(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        if (!updatedSong.getArtist().getName().trim().equals(oldSong.getArtist().getName().trim())) {
            artistDataAccessObject.addArtistToDatabase(updatedSong.getArtist(), connection);

            updatedSong.getArtist().setID(artistDataAccessObject.getArtistID(updatedSong.getArtist(), connection));
            songsDataAccessObject.updateArtistID(oldSong.getID(), updatedSong.getArtist().getID(), connection);
        }
    }

    /**
     * Compares featured artists of the song received from the user and song stored in database, and applies changes (if any).
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateFeaturings(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();
        FeaturingsDataAccessObject featuringsDataAccessObject = new FeaturingsDataAccessObject();
        List<Integer> idsOfFeaturingArtists = featuringsDataAccessObject.getIDsOfFeaturedArtists(oldSong.getID(), connection);

        if (updatedSong.hasFeaturedArtists()) {
            for (Artist featuredArtist : updatedSong.getFeaturedArtists()) {
                if (!artistDataAccessObject.checkIfArtistExists(featuredArtist, connection)) {
                    artistDataAccessObject.addArtistToDatabase(featuredArtist, connection);
                }

                featuredArtist.setID(artistDataAccessObject.getArtistID(featuredArtist, connection));

                if (!idsOfFeaturingArtists.contains(featuredArtist.getID())) {
                    featuringsDataAccessObject.addNewFeaturing(featuredArtist.getID(), updatedSong.getID(), connection);
                }
            }

            if (oldSong.hasFeaturedArtists()) {
                for (Artist featuredArtist : oldSong.getFeaturedArtists()) {
                    if (!updatedSong.getFeaturedArtists().contains(featuredArtist)) {
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
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateGenresOfSong(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        GenresDataAccessObject genresDataAccessObject = new GenresDataAccessObject();
        GenresOfSongsDataAccessObject genresOfSongsDataAccessObject = new GenresOfSongsDataAccessObject();

        if (!updatedSong.getGenres().isEmpty()) {
            for (String genre : updatedSong.getGenres()) {
                if (!genresDataAccessObject.checkIfGenreExists(genre, connection)) {
                    genresDataAccessObject.addGenreToDatabase(genre, connection);
                }
            }

            for (String genre : oldSong.getGenres()) {
                if (!oldSong.getGenres().contains(genre)) {
                    int genreID = genresDataAccessObject.getGenreID(genre, connection);
                    genresOfSongsDataAccessObject.addGenreOfSong(updatedSong.getID(), genreID, connection);
                }
            }

            if (!oldSong.getGenres().isEmpty()) {
                for (String genre : oldSong.getGenres()) {
                    if (!updatedSong.getGenres().contains(genre)) {
                        int genreID = genresDataAccessObject.getGenreID(genre, connection);
                        int matchID = genresOfSongsDataAccessObject.getSongGenreMatchID(updatedSong.getID(), genreID, connection);
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
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateLyrics(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        if (oldSong.getLyrics().equals(updatedSong.getLyrics())) { ;
            return;
        }

        updateLines(updatedSong, oldSong, connection);

        updateLyricsPartTypes(updatedSong, oldSong, connection);
    }

    /**
     * Compares lines of song received from the user and song stored in database, and applies changes (if any).
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateLines(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        List<Line> updatedLines = Songs.getLines(updatedSong);
        List<Line> oldLines = Songs.getLines(oldSong);

        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

        //Do nothing if there is no difference between old lyrics and edited lyrics.
        if (updatedLines.equals(oldLines)) {
            return;
        }

        if (updatedLines.size() == oldLines.size()) {
            for (int linePosition = 0; linePosition < updatedLines.size(); linePosition++) {
                if (!updatedLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLine(lineID, updatedLines.get(linePosition), connection);
                }
            }
        } else if (updatedLines.size() < oldLines.size()) {
            int linePosition;
            for (linePosition = 0; linePosition < updatedLines.size(); linePosition++) {
                if (!updatedLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLine(lineID, updatedLines.get(linePosition), connection);
                }
            }

            //Mark remaining lines as deleted.
            while (linePosition < oldLines.size()) {
                int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                linesDataAccessObject.markLineAsDeleted(lineID, connection);
                linePosition++;
            }
        } else if (updatedLines.size() > oldLines.size()) {
            int linePosition;

            for (linePosition = 0; linePosition < oldLines.size(); linePosition++) {
                if (!updatedLines.get(linePosition).equals(oldLines.get(linePosition))) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLine(lineID, updatedLines.get(linePosition), connection);
                }
            }

            while (linePosition < updatedLines.size()) {
                SongLyricsPartType songLyricsPartType = Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition);
                linesDataAccessObject.addLineToDatabase(linePosition, updatedSong.getID(), updatedLines.get(linePosition), songLyricsPartType, connection);
                linePosition++;
            }
        }
    }

    /**
     * Compares lyrics part types of the song received from the user and song stored in database, and applies changes (if any).
     * @param updatedSong Song received from user.
     * @param oldSong Song stored in database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private void updateLyricsPartTypes(Song updatedSong, Song oldSong, Connection connection) throws SQLException, DataAccessException {
        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

        if (Songs.getNumberOfLines(updatedSong) == Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(updatedSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition), connection);
                }
            }
        } else if (Songs.getNumberOfLines(updatedSong) > Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(oldSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition), connection);
                }
            }

            while (linePosition < Songs.getNumberOfLines(updatedSong)) {
                SongLyricsPartType songLyricsPartType = Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition);
                linesDataAccessObject.addLineToDatabase(linePosition, updatedSong.getID(), Songs.getLineAt(updatedSong, linePosition), songLyricsPartType, connection);
                linePosition++;
            }
        } else if (Songs.getNumberOfLines(updatedSong)  <Songs.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < Songs.getNumberOfLines(updatedSong); linePosition++) {
                if (Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition) != Songs.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, updatedSong.getID(), connection);
                    linesDataAccessObject.updateLyricsPart(lineID, Songs.getTypeOfLyricsPartWhichContainsLine(updatedSong, linePosition), connection);
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
     * @throws SQLException Thrown of some error occurred when attempted to update data.
     */
    private void updateYouTubeLink(int songID, String newYouTubeVideoID, String oldYouTubeVideoID, Connection connection) throws SQLException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (!newYouTubeVideoID.trim().equals(oldYouTubeVideoID)) {
            songsDataAccessObject.updateYouTubeLink(songID, newYouTubeVideoID, connection);
        }
    }

    /**
     * Retrieves song with the specific ID.
     * @param songID ID of song.
     * @return <Code>Song</Code> object.
     * @throws NoSuchSongException Thrown if there is no song with such ID.
     */
    public Song getSongByID(int songID) throws NoSuchSongException, DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        if (!checkIfSongExists(songID, connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new NoSuchSongException();
        }

        boolean withLyrics = true;

        Song song = getSongByID(songID, withLyrics, connection);

        ConnectionPool.getInstance().returnConnection(connection);

        return song;
    }

    /**
     * Retrieves song with the specific ID
     * @param songID ID of song.
     * @param connection Connection to be used.
     * @return <Code>Song</Code> object.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    private Song getSongByID(int songID, boolean withLyrics, Connection connection) throws DataAccessException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();
        FeaturingsDataAccessObject featuringsDataAccessObject = new FeaturingsDataAccessObject();
        GenresOfSongsDataAccessObject genresOfSongsDataAccessObject = new GenresOfSongsDataAccessObject();
        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();

        Song song = new Song();

        Map<String, Object> data = songsDataAccessObject.getSongData(songID, connection);

        Integer artistID = (Integer) data.get(DatabaseConstants.ColumnLabels.SongsTable.ARTIST_ID);
        String songName = (String) data.get(DatabaseConstants.ColumnLabels.SongsTable.SONG_NAME);
        String youTubeVideoID = (String) data.get(DatabaseConstants.ColumnLabels.SongsTable.YOUTUBE_LINK);
        Boolean approved = (Boolean) data.get(DatabaseConstants.ColumnLabels.SongsTable.IS_APPROVED);
        Boolean hasFeaturings = (Boolean) data.get(DatabaseConstants.ColumnLabels.SongsTable.HAS_FEATURING);
        Integer contributedUserID = (Integer) data.get(DatabaseConstants.ColumnLabels.SongsTable.CONTRIBUTED_USER_ID);

        Artist artist = artistDataAccessObject.getArtistByID(artistID, connection);
        song.setID(songID);
        song.setArtist(artist);
        song.setName(songName);
        song.setYouTubeVideoID(youTubeVideoID);
        song.setApproved(approved);

        if (hasFeaturings) {
            List<Integer> featuredArtistsIDs = featuringsDataAccessObject.getIDsOfFeaturedArtists(songID, connection);
            List<Artist> featuredArtists = new ArrayList<>();

            for (Integer featuredArtistID : featuredArtistsIDs) {
                featuredArtists.add(artistDataAccessObject.getArtistByID(featuredArtistID, connection));
            }

            song.setFeaturedArtists(featuredArtists);
        }

        song.setGenres(genresOfSongsDataAccessObject.getGenresOfSongBySongID(songID, connection));

        song.setAverageRating(songsRatingsDataAccessObject.getAverageRatingOfSong(songID, connection));

        if (withLyrics) {
            song.setLyrics(linesDataAccessObject.getSongLyricsBySongID(songID, connection));
        }

        User user = new User();
        user.setID(contributedUserID);
        user.setUsername(usersDataAccessObject.getUserNameByUserID(contributedUserID, connection));
        song.setContributedUser(user);

        return song;

    }

    /**
     * Adds new song to database.
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
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public void addSongToDatabase() throws SongAddingException, SuchSongAlreadyExistsException, LyricsParsingException, InvalidSongNameException, InvalidFeaturedArtistsException, InvalidYouTubeVideoIDException, InvalidSongGenresException, InvalidArtistNameException, TooLongOrEmptyLyricsException, InvalidFeaturedArtistNameException, DataAccessException {
       Connection connection = ConnectionPool.getInstance().getConnection();
       SongRetriever songRetriever = new SongRetriever();

       Song song = songRetriever.retrieveSongFromRequest(requestWrapper.getRequest());

       User user = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

       if (user == null) {
           song.setApproved(false);
           User anonymousUser = new User();
           anonymousUser.setID(0);
           song.setContributedUser(anonymousUser);
       } else if (user.getUserType() == UserType.MODERATOR) {
           song.setApproved(true);
           song.setContributedUser(user);
       } else {
           song.setApproved(false);
           song.setContributedUser(user);
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

           if (!artistDataAccessObject.checkIfArtistExists(song.getArtist(), connection)) {
               artistDataAccessObject.addArtistToDatabase(song.getArtist(), connection);
           }

           song.getArtist().setID(artistDataAccessObject.getArtistID(song.getArtist(), connection));

           songsDataAccessObject.addDataToSongsTable(song, connection);
           song.setID(songsDataAccessObject.getLastSongID(connection));

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

                   if (!genresDataAccessObject.checkIfGenreExists(genre, connection)) {
                       genresDataAccessObject.addGenreToDatabase(genre, connection);
                   }

                   int genreID = genresDataAccessObject.getGenreID(genre, connection);
                   genresOfSongsDataAccessObject.addGenreOfSong(song.getID(), genreID, connection);
               }
           }

           linesDataAccessObject.addSongLyricsToDatabase(song, connection);
           connection.commit();

       } catch (SQLException e) {
           logger.error(LoggingConstants.EXCEPTION_WHILE_ADDING_SONG, e);
           try {
               connection.rollback();
               throw new SongAddingException();
           } catch (SQLException e1) {
               logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
               throw new SongAddingException();
           }
       } finally {
           ConnectionPool.getInstance().returnConnection(connection);
       }
    }

    /**
     * Returns list of songs performed by specific artist.
     * @return List of songs performed by specific artist.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Song> getSongsByArtist() throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
        FeaturingsDataAccessObject featuringsDataAccessObject = new FeaturingsDataAccessObject();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        String artistName = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.ARTIST_NAME);

        Artist artist = new Artist(artistName);

        artist.setID(artistDataAccessObject.getArtistID(artist, connection));

        List<Integer> songIDs = songsDataAccessObject.getIDsOfSongsPerformedByArtist(artist.getID(), connection);
        List<Integer> featuredSongsIDs = featuringsDataAccessObject.getIDsOfSongsFeaturedByArtist(artist.getID(), connection);

        if (!featuredSongsIDs.isEmpty()) {
            songIDs.addAll(featuredSongsIDs);
        }

        List<Song> songsByArtist =  new ArrayList<>();
        boolean withLyrics = false;

        for (Integer songID : songIDs) {
            songsByArtist.add(getSongByID(songID, withLyrics, connection));
        }

        ConnectionPool.getInstance().returnConnection(connection);
        return songsByArtist;
    }

    /**
     * Returns list of recently added songs.
     * @return List of recently added songs.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Song> getRecentlyAddedSongs() throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        int numberOfSongs = 10;

        List<Integer> songIDs = songsDataAccessObject.getIDsOfRecentlyAddedSongs(numberOfSongs, connection);

        List<Song> songs = new ArrayList<>();
        boolean withLyrics = false;

        for (Integer songID : songIDs) {
            songs.add(getSongByID(songID, withLyrics, connection));
        }

        ConnectionPool.getInstance().returnConnection(connection);

        return songs;
    }

    /**
     * Returns songs contributed by the specified user.
     * @param userID ID of the user.
     * @return Songs contributed by the specified user.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Song> getSongsContributedByUser(int userID) throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        List<Integer> songIDs = songsDataAccessObject.getIDsOfSongsContributedByUser(userID, connection);

        List<Song> songs = new ArrayList<>();
        boolean withLyrics = false;

        for (Integer songID : songIDs) {
            songs.add(getSongByID(songID, withLyrics, connection));
        }

        ConnectionPool.getInstance().returnConnection(connection);

        return songs;
    }

    /**
     * Returns list of not approved songs.
     * @return List of not approved songs.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Song> getListOfNotApprovedSongs() throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        List<Integer> songIDs = songsDataAccessObject.getIDsOfNotApprovedSongs(connection);

        List<Song> songs = new ArrayList<>();
        boolean withLyrics = false;

        for (Integer songID : songIDs) {
            songs.add(getSongByID(songID, withLyrics, connection));
        }

        ConnectionPool.getInstance().returnConnection(connection);

        return songs;
    }

    /**
     * Returns list of top ten rated songs.
     * @return List of top ten rated songs.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Song> getTopTenRatedSongs() throws DataAccessException {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        List<Song> songs = new ArrayList<>();
        Map<Integer, Double> mapWithSongsIDs = songsRatingsDataAccessObject.getTopTenRatedSongsIDsAndRatings(connection);

        for (Map.Entry entry : mapWithSongsIDs.entrySet()) {
            Integer songID = (Integer) entry.getKey();
            Double songRating = (Double) entry.getValue();

            boolean withLyrics = false;

            Song song = getSongByID(songID, withLyrics, connection);

            song.setAverageRating(songRating);

            songs.add(song);
        }

        return songs;
    }

    /**
     * Marks song with specific ID as approved.
     * @throws NoPermissionException Thrown if user who attempted to approve the song does not have a permission
     * to perform this operation.
     * @throws SongApprovingException Thrown if some error occurred when attempred to modify data.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public void approveSong() throws NoPermissionException, NoSuchSongException, SongApprovingException, DataAccessException {
        User user = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

        if (user == null || user.getUserType() != UserType.MODERATOR) {
            throw new NoPermissionException();
        }

        try {
            int songID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SONG_ID));

            Connection connection = ConnectionPool.getInstance().getConnection();
            SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

            if (!checkIfSongExists(songID, connection)) {
                ConnectionPool.getInstance().returnConnection(connection);
                throw new NoSuchSongException();
            }

            try {
                songsDataAccessObject.markSongAsApproved(songID, connection);
            } catch (SQLException e) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_APPROVING_SONG, e);
                try {
                    connection.rollback();
                    throw new SongApprovingException();
                } catch (SQLException e1) {
                    logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK);
                    throw new SongApprovingException();
                }
            } finally {
                ConnectionPool.getInstance().returnConnection(connection);
            }
        } catch (NumberFormatException ex) {
            throw new NoSuchSongException();
        }
    }

    /**
     * Checks if song with the specified ID exists.
     * @param songID ID of the song which is to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if there is a song with such ID, <Code>False</Code> if not.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data.
     */
    private boolean checkIfSongExists(int songID, Connection connection) throws DataAccessException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();
        boolean result = false;

        if (songsDataAccessObject.checkIfSongExists(songID, connection)) {
            result = true;
        }

        return result;
    }
}
