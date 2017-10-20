package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.connectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.ArtistDataAccessObject;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.exception.DataAccessException;


import java.sql.Connection;
import java.util.List;

/**
 * This class is responsible for managing artists.
 */
public class ArtistsManager {

    /**
     * <Code>RequestWrapper</Code> which contains data sent by user.
     */
    private RequestWrapper requestWrapper;

    /**
     * Constructs <Code>ArtistsManager</Code> instance.
     */
    public ArtistsManager() {
    }

    /**
     * Constructs <Code>ArtistsManager</Code> instance with pre-defined requestWrapper.
     * @param requestWrapper <Code>RequestWrapper</Code> instance.
     */
    public ArtistsManager(RequestWrapper requestWrapper) {
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
     * Returns list of artists whose names begin with specified letter.
     * @return List of artists whose names begin with specified letter.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Artist> getArtistsByLetter() throws DataAccessException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        int index = 0;
        Character letter = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.LETTER).charAt(index);

        List<Artist> artists = artistDataAccessObject.getArtistsByLetter(letter, connection);
        connectionPool.returnConnection(connection);

        return artists;
    }

    /**
     * Returns list of beginning letters of names of artists.
     * @return List of beginning letters of names of artists.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public List<Character> getArtistsLetters() throws DataAccessException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

        List<Character> artistsLetters =  artistDataAccessObject.getArtistLetters(connection);

        ConnectionPool.getInstance().returnConnection(connection);
        return artistsLetters;
    }
}
