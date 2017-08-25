package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.ArtistDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.SongDataAccessObject;
import kz.javalab.songslyricswebsite.model.song.Song;
import kz.javalab.songslyricswebsite.model.song.artist.Artist;

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

    public Song getSongByID(int songID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Song song = songDataAccessObject.getSongByID(songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return song;
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

    public void addSongToDatabase(Song song, String youTubeLink) {
       Connection connection = ConnectionPool.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
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
        }

        ConnectionPool.getInstance().returnConnection(connection);

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
