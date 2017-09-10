package kz.javalab.songslyricswebsite.dataaccessobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 08.09.2017.
 */
public abstract class AbstractDataAccessObject {

    /**
     * Checks if there is a record in table with specific parameters.
     * @param preparedStatement A statement to be used.
     * @param parameters Parameters of the statement.
     * @return <Code>True</Code> if there is a record with specified parameters, <Code>False</Code> if not.
     * @throws SQLException Thrown if there is error occurred while attempting to execute query.
     */
    protected boolean checkEntityExistence(PreparedStatement preparedStatement, int... parameters) throws SQLException {
        for (int index = 0; index < parameters.length; index++) {
            preparedStatement.setInt(index + 1, parameters[index]);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        boolean result = resultSet.next();

        resultSet.close();
        preparedStatement.close();

        return result;
    }


    protected void updateStringValueByEntityID(PreparedStatement preparedStatement, int entityID, String newStringValue) throws SQLException {
        int stringParameter = 1;
        int entityIDParameter = 2;

        preparedStatement.setString(stringParameter, newStringValue);
        preparedStatement.setInt(entityIDParameter, entityID);

        preparedStatement.execute();

        preparedStatement.close();
    }

    protected void executePreparedStatementWithMultipleIntegerValues(PreparedStatement preparedStatement, int... values) throws SQLException {
        for (int index = 0; index < values.length; index++) {
            preparedStatement.setInt(index + 1, values[index]);
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    protected boolean checkEntityExistenceByStringValue(PreparedStatement preparedStatement, String value) throws SQLException {
        int stringValueParameter = 1;

        preparedStatement.setString(stringValueParameter, value);

        ResultSet resultSet = preparedStatement.executeQuery();

        boolean result = resultSet.next();

        resultSet.close();
        preparedStatement.close();

        return result;
    }
}
