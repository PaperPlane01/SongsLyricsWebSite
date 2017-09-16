package kz.javalab.songslyricswebsite.dataaccessobject;


import kz.javalab.songslyricswebsite.constant.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class GenresDataAccessObject extends AbstractDataAccessObject {

    public GenresDataAccessObject() {
    }

    /**
     * Adds genre to database.
     * @param genreName Name of the genre to be added.
     * @param connection Connection to be used.
     */
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

    /**
     * Checks if there is a genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such genre exists, <Code>False</Code> if not.
     */
    public boolean checkIfGenreExists(String genreName, Connection connection) {
        boolean result = false;

        String checkGenreQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGenreQuery);

            result = checkEntityExistenceByStringValue(preparedStatement, genreName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  result;
    }

    /**
     * Returns ID of the genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return ID of the genre with specific name.
     */
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
