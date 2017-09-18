package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.ArtistDataAccessObject;
import kz.javalab.songslyricswebsite.entity.artist.Artist;


import java.sql.Connection;
import java.util.List;

/**
 * Created by PaperPlane on 23.08.2017.
 */
public class ArtistsManager {
    private ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();
    private RequestWrapper requestWrapper = new RequestWrapper();

    public ArtistsManager() {
    }

    public ArtistsManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public List<Artist> getArtistsByLetter() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        int index = 0;
        Character letter = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.LETTER).charAt(index);

        List<Artist> artists = artistDataAccessObject.getArtistsByLetter(letter, connection);
        connectionPool.returnConnection(connection);

        return artists;
    }

    public List<Character> getArtistsLetters() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<Character> artistsLetters =  artistDataAccessObject.getArtistLetters(connection);

        ConnectionPool.getInstance().returnConnection(connection);
        return artistsLetters;
    }
}
