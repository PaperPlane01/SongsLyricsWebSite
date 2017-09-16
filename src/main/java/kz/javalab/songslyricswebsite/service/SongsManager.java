package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.*;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.exception.SongAddingException;
import kz.javalab.songslyricswebsite.exception.SongAlteringException;
import kz.javalab.songslyricswebsite.exception.SuchSongAlreadyExistsException;
import kz.javalab.songslyricswebsite.util.SongHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class SongsManager {

    public SongsManager() {
    }

    /**
     * Received altered song and commits changes to database.
     * @param songID ID of the song to be altered.
     * @param alteredSong Altered song.
     * @throws NoSuchSongException Thrown if there is no song with this ID.
     * @throws SongAlteringException Thrown if some exception occurred when tried to alter song.
     */
    public void alterSong(int songID, Song alteredSong) throws NoSuchSongException, SongAlteringException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Song oldSong = getSongByID(songID, connection);

        try {

            connection.setAutoCommit(false);

            alterSongName(alteredSong, oldSong, connection);

            alterArtistID(alteredSong, oldSong, connection);

            alterFeaturings(alteredSong, oldSong, connection);

            alterGenresOfSong(alteredSong, oldSong, connection);

            alterLyrics(alteredSong, oldSong, connection);

            alterYouTubeLink(songID, alteredSong.getYouTubeVideoID(), oldSong.getYouTubeVideoID(), connection);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                throw  new SongAlteringException();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new SongAlteringException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
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
    private void alterArtistID(Song alteredSong, Song oldSong, Connection connection) {
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
    private void alterFeaturings(Song alteredSong, Song oldSong, Connection connection) {
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
    private void alterGenresOfSong(Song alteredSong, Song oldSong, Connection connection) {
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
    private void alterLyrics(Song alteredSong, Song oldSong, Connection connection)  {
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
    private void alterLines(Song alteredSong, Song oldSong, Connection connection) {
        List<Line> alteredLines = SongHelper.getLines(alteredSong);
        List<Line> oldLines = SongHelper.getLines(oldSong);

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
                SongLyricsPartType songLyricsPartType = SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition);
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
    private void alterLyricsPartTypes(Song alteredSong, Song oldSong, Connection connection) {
        LinesDataAccessObject linesDataAccessObject = new LinesDataAccessObject();

        if (SongHelper.getNumberOfLines(alteredSong) == SongHelper.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < SongHelper.getNumberOfLines(alteredSong); linePosition++) {
                if (SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != SongHelper.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
                }
            }
        } else if (SongHelper.getNumberOfLines(alteredSong) > SongHelper.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < SongHelper.getNumberOfLines(oldSong); linePosition++) {
                if (SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != SongHelper.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
                }
            }

            while (linePosition < SongHelper.getNumberOfLines(alteredSong)) {
                SongLyricsPartType songLyricsPartType = SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition);
                linesDataAccessObject.addLineToDatabase(linePosition, alteredSong.getID(), SongHelper.getLineAt(alteredSong, linePosition), songLyricsPartType, connection);
                linePosition++;
            }
        } else if (SongHelper.getNumberOfLines(alteredSong) < SongHelper.getNumberOfLines(oldSong)) {
            int linePosition;
            for (linePosition = 0; linePosition < SongHelper.getNumberOfLines(alteredSong); linePosition++) {
                if (SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition) != SongHelper.getTypeOfLyricsPartWhichContainsLine(oldSong, linePosition)) {
                    int lineID = linesDataAccessObject.getLineID(linePosition, alteredSong.getID(), connection);
                    linesDataAccessObject.alterLyricsPart(lineID, SongHelper.getTypeOfLyricsPartWhichContainsLine(alteredSong, linePosition), connection);
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
    private void alterYouTubeLink(int songID, String newYouTubeVideoID, String oldYouTubeVideoID, Connection connection) {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (!newYouTubeVideoID.trim().equals(oldYouTubeVideoID)) {
            songsDataAccessObject.alterYouTubeLink(songID, newYouTubeVideoID, connection);
        }
    }

    /**
     * Retrieves <Code>Song</Code> object from database by its ID.
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

    private Song getSongByID(int songID, Connection connection) throws NoSuchSongException {
        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        if (songsDataAccessObject.checkIfSongExists(songID, connection)) {
            boolean withLyrics = true;
            Song song = songsDataAccessObject.getSongByID(songID, withLyrics, connection);
            return song;
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
     * Adds song to database.
     * @param song Song to be added.
     * @throws SongAddingException Thrown if there is an error occurred when attempted to insert data into database.
     * @throws SuchSongAlreadyExistsException Thrown if such song already exists.
     */
    public void addSongToDatabase(Song song) throws SongAddingException, SuchSongAlreadyExistsException {
       Connection connection = ConnectionPool.getInstance().getConnection();

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
     * @param artist Artist whose songs are to be retrieved.
     * @return List of songs performed by specific artist.
     */
    public List<Song> getSongsByArtist(Artist artist) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        SongsDataAccessObject songsDataAccessObject = new SongsDataAccessObject();

        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        artist.setID(artistDataAccessObject.getArtistID(artist, connection));
        List<Song> songsByArtist =  songsDataAccessObject.getSongsByArtist(artist, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return songsByArtist;
    }

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
