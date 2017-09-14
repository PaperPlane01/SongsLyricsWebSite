package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.entity.artist.Artist;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 17.08.2017.
 */
public class ArtistDataAccessObject extends AbstractDataAccessObject {

    public ArtistDataAccessObject() {
    }

    /**
     * Retrieves artists whose names are beginning with a specific letter.
     * @param letter First letter of the artists.
     * @return List of Artists whose names are beginning with a specific letter.
     */
    public List<Artist> getArtistsByLetter(char letter, Connection connection) {

        List<Artist> artists = new ArrayList<>();

        String getArtistsByLetterQuery = "SELECT * FROM artists\n" +
                "WHERE artist_letter = ?\n" +
                "ORDER BY artist_name";

        int artistLetterParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getArtistsByLetterQuery);
            preparedStatement.setString(artistLetterParameter, (new Character(letter)).toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Artist artist = new Artist();

                int artistID = resultSet.getInt(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_ID);
                String artistName = resultSet.getString(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_NAME);

                artist.setID(artistID);
                artist.setName(artistName);

                artists.add(artist);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artists;
    }

    /**
     * Retrieves all starting letters of the artists from the database.
     * @return List of all starting letters of the artists.
     */
    public List<Character> getArtistLetters(Connection connection) {
        List<Character> artistLetters = new ArrayList<>();

        String getArtistLettersQuery = "SELECT artist_letter FROM artists\n" +
                "ORDER BY artist_letter";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getArtistLettersQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            int charIndex = 0;

            while (resultSet.next()) {
                Character artistLetter = resultSet.getString(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_LETTER).charAt(charIndex);

                if (!artistLetters.contains(artistLetter)) {
                    artistLetters.add(artistLetter);
                }
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistLetters;
    }

    /**
     * Inserts artist to the database.
     * @param artist Artist to be inserted.
     */
    public void addArtistToDatabase(Artist artist, Connection connection) {

        if (!checkIfArtistExists(artist, connection)) {
            int lastID = getLastArtistID(connection);

            String addArtistQuery = "INSERT INTO artists (artist_id, artist_name, artist_letter)\n" +
                    "VALUES (?, ?, ?)";

            int artistIDParameter = 1;
            int artistNameParameter = 2;
            int artistLetterParameter = 3;

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addArtistQuery);
                preparedStatement.setInt(artistIDParameter,lastID + 1);
                preparedStatement.setString(artistNameParameter, artist.getName());
                preparedStatement.setString(artistLetterParameter, (new Character(artist.getName().charAt(0))).toString());
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves ID of the specific artist from the database.
     * @param artist Artist whose ID is to be retrieved.
     * @return ID of the specific artist
     */
    public int getArtistID(Artist artist, Connection connection) {
        int id = 0;

        if (checkIfArtistExists(artist, connection)) {
            String getArtistIDQuery = "SELECT artist_id FROM artists\n" +
                    "WHERE artist_name = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(getArtistIDQuery);
                preparedStatement.setString(1, artist.getName());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    id = resultSet.getInt(DatabaseConstants.ColumnLabels.ArtistsTable.ARTIST_ID);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    /**
     * Checks if such artist is in database.
     * @param artist Artist to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such artist is in database, <Code>False</Code> if not.
     */
    private boolean checkIfArtistExists(Artist artist, Connection connection) {
        boolean result = false;

        String checkArtistQuery = "SELECT artist_id FROM artists\n" +
                "WHERE artist_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkArtistQuery);
            result = checkEntityExistenceByStringValue(preparedStatement, artist.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Retrieves last artist ID from the database.
     * @param connection Connection to be used.
     * @return Last artist ID
     */
    private int getLastArtistID(Connection connection) {
        int lastID = 0;
        String getLastArtistIDQuery = "SELECT max(artist_id) FROM artists";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLastArtistIDQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastID = resultSet.getInt("max(artist_id)");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastID;
    }
}
