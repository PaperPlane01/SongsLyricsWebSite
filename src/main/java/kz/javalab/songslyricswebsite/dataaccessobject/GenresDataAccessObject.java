package kz.javalab.songslyricswebsite.dataaccessobject;


import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for receiving, inserting and updating data of "Genres" table.
 */
public class GenresDataAccessObject extends AbstractDataAccessObject {

    private Logger logger = Logger.getLogger(GenresDataAccessObject.class.getName());

    /**
     * Constructs <Code>GenresDataAccessObject</Code> instance.
     */
    public GenresDataAccessObject() {
        super();
    }

    /**
     * Adds genre to database.
     * @param genreName Name of the genre to be added.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to insert data into database.
     */
    public void addGenreToDatabase(String genreName, Connection connection) throws SQLException {
        String addGenreQuery = "INSERT INTO genres\n" +
                "(genre_name)\n" +
                "VALUES\n" +
                "(?)";
        int genreNameParameter = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(addGenreQuery);

        preparedStatement.setString(genreNameParameter, genreName);

        preparedStatement.execute();

        preparedStatement.close();

    }

    /**
     * Checks if there is a genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such genre exists, <Code>False</Code> if not.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public boolean checkIfGenreExists(String genreName, Connection connection) throws DataAccessException {
        boolean result = false;

        String checkGenreQuery = "SELECT genre_id\n" +
                "FROM genres\n" +
                "WHERE genre_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGenreQuery);

            result = checkEntityExistenceByStringValue(preparedStatement, genreName);
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_GENRE_EXISTENCE, e);
            throw new DataAccessException();
        }

        return  result;
    }

    /**
     * Returns ID of the genre with specific name.
     * @param genreName Name of the genre.
     * @param connection Connection to be used.
     * @return ID of the genre with specific name.
     * @throws DataAccessException Thrown if some error occurred when attempted to retrieve data from database.
     */
    public int getGenreID(String genreName, Connection connection) throws DataAccessException {
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
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_GENRE_ID, e);
            throw new DataAccessException();
        }

        return genreID;
    }

}
