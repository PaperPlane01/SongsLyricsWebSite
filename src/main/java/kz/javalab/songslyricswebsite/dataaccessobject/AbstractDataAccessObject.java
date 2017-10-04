package kz.javalab.songslyricswebsite.dataaccessobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for operating SQL statements common for several data access objects.
 */
public abstract class AbstractDataAccessObject {

    public AbstractDataAccessObject() {
    }

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

        boolean result = false;

        if (resultSet.next()) {
            result = true;
        }

        resultSet.close();
        preparedStatement.close();

        return result;
    }

    /**
     * Updates string value of entity by it's ID.
     * @param preparedStatement Prepared statement to be executed.
     * @param entityID ID of entity.
     * @param newStringValue New string value.
     * @throws SQLException Thrown if there is error occurred while attempting to execute query.
     */
    protected void updateStringValueByEntityID(PreparedStatement preparedStatement, int entityID, String newStringValue) throws SQLException {
        int stringParameter = 1;
        int entityIDParameter = 2;

        preparedStatement.setString(stringParameter, newStringValue);
        preparedStatement.setInt(entityIDParameter, entityID);

        preparedStatement.execute();

        preparedStatement.close();
    }

    /**
     * Executes query with multiple integer values as parameters.
     * @param preparedStatement Prepared statement to be executed.
     * @param values Integer parameters of query.
     * @throws SQLException Thrown if there is error occurred while attempting to execute query.
     */
    protected void executePreparedStatementWithMultipleIntegerValues(PreparedStatement preparedStatement, int... values) throws SQLException {
        for (int index = 0; index < values.length; index++) {
            preparedStatement.setInt(index + 1, values[index]);
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    /**
     * Checks entity existence by string value.
     * @param preparedStatement Prepared statement to be executed.
     * @param value String value which is to be used to check entity's existence.
     * @return <Code>True</Code> if entity exists, <Code>False</Code> if not.
     * @throws SQLException Thrown if there is error occurred while attempting to execute query.
     */
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
