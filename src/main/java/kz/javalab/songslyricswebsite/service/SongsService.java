package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.ArtistDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.SongDataAccessObject;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.exception.SongAddingException;
import kz.javalab.songslyricswebsite.exception.SuchSongAlreadyExistsException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class SongsService {

    private SongDataAccessObject songDataAccessObject = new SongDataAccessObject();
    private ArtistDataAccessObject artistDataAccessObject = new ArtistDataAccessObject();

    public Song getSongByID(int songID) throws NoSuchSongException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        if (songDataAccessObject.checkIfSongExists(songID, connection)) {
            Song song = songDataAccessObject.getSongByID(songID, connection);
            ConnectionPool.getInstance().returnConnection(connection);
            return song;
        } else {
            throw new NoSuchSongException();
        }
    }

    public Map<Integer, String> getSongIDsWithTitles(boolean approved) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        Map<Integer, String> songIDsWithTitles =  songDataAccessObject.getSongIDsWithTitles(approved, connection);

        ConnectionPool.getInstance().returnConnection(connection);

        return songIDsWithTitles;
    }


    public String getYouTubeLinkBySongID(int songID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String youTubeLink =  songDataAccessObject.getYouTubeLinkBySongID(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return youTubeLink;
    }

    public void addSongToDatabase(Song song, String youTubeLink) throws SongAddingException, SuchSongAlreadyExistsException {
       Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            if (checkIfSongExists(song, connection)) {
                throw new SuchSongAlreadyExistsException();
            }
            artistDataAccessObject.addArtistToDatabase(song.getArtist(), connection);
            int artistID = artistDataAccessObject.getArtistID(song.getArtist());

            song.getArtist().setID(artistID);

            if (song.hasFeaturedArtists()) {
                song.getFeaturedArtists().forEach(featuredArtist -> {
                    artistDataAccessObject.addArtistToDatabase(featuredArtist, connection);
                    featuredArtist.setID(artistDataAccessObject.getArtistID(featuredArtist));
                });
            }

            songDataAccessObject.addSongToDatabase(song, youTubeLink, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new SongAddingException();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    private boolean checkIfSongExists(Song song, Connection connection) {
        return songDataAccessObject.checkIfSongExists(song, connection);
    }

    public List<Song> getSongsByArtist(Artist artist) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        artist.setID(artistDataAccessObject.getArtistID(artist));
        List<Song> songsByArtist =  songDataAccessObject.getSongsByArtist(artist, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return songsByArtist;
    }

    public void approveSong(int songID) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            songDataAccessObject.approveSong(songID, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        ConnectionPool.getInstance().returnConnection(connection);

    }

    public boolean checkIfSongApproved(int songID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean approved = songDataAccessObject.checkIfSongApprovedBySongID(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return approved;
    }
}
