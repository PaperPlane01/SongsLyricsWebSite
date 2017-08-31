package kz.javalab.songslyricswebsite.dataaccessobject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class GenresDataAccessObject {

    public void addGenreToDatabase(String genreName, Connection connection) {
        String addGenreQuery = "INSERT INTO genres\n" +
                "(genre_name)\n" +
                "VALUES\n" +
                "(?)";
        int genreNameParameter = 1;

        if (!checkIfGenreExists(genreName, connection)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addGenreQuery);

                preparedStatement.setString(genreNameParameter, genreName);

                preparedStatement.execute();

                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addSongGenreMatch(int songID, int genreID, Connection connection) {
        String addSongGenreMatchQuery = "INSERT INTO genres_of_songs\n" +
                "(song_id, genre_id)\n" +
                "VALUES\n" +
                "(?, ?)";
        int songIDParameter = 1;
        int genreIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addSongGenreMatchQuery);

            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(genreIDParameter, genreID);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markSongGenreMatchAsDeleted(int matchID, Connection connection) {
        String markAsDeletedQuery = "UPDATE genres_of_songs\n" +
                "SET match_deleted = ?\n" +
                "WHERE match_id = ?";
        int matchDeletedParameter = 1;
        int matchIDParameter = 2;

        int matchDeletedValue = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(markAsDeletedQuery);

            preparedStatement.setInt(matchDeletedParameter, matchDeletedValue);
            preparedStatement.setInt(matchIDParameter, matchID);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSongGenreMatchID(int songID, int genreID, Connection connection) {
        int songGenreMatchID = 0;

        String getMatchID = "SELECT match_id FROM genres_of_songs\n" +
                "WHERE song_id = ? AND genre_id = ?";
        int songIDParameter = 1;
        int genreIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getMatchID);

            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(genreIDParameter, genreID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                songGenreMatchID = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresOfSongsTable.MATCH_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songGenreMatchID;
    }

    public boolean checkIfSongGenreMatchExists(int genreID, int songID, Connection connection) {
        boolean result = false;

        String checkSongGenreMatchQuery = "SELECT match_id FROM genres_of_songs\n" +
                "WHERE song_id = ? and genre_id = ?";
        int songIDParameter = 1;
        int genreIDParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkSongGenreMatchQuery);

            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(genreIDParameter, genreID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkIfGenreExists(String genreName, Connection connection) {
        boolean result = false;

        String checkGenreQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";
        int genreNameParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGenreQuery);

            preparedStatement.setString(genreNameParameter, genreName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public int getGenreID(String genreName, Connection connection) {
        int genreID = 0;

        String genreIDQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";
        int genreNameParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(genreIDQuery);

            preparedStatement.setString(genreNameParameter, genreName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                genreID = resultSet.getInt(DatabaseConstants.ColumnLabels.GenresTable.GENRE_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreID;
    }

}
