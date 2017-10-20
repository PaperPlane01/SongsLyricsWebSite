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

        boolean result = false;

        try (ResultSet resultSet = preparedStatement.executeQuery();) {

            if (resultSet.next()) {
                result = true;
            }
        }

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

        boolean result = false;

        try (ResultSet resultSet = preparedStatement.executeQuery();) {
            result = resultSet.next();
        }

        return result;
    }

    protected ResultSet getResultSetOfPreparedStatementWithMultipleIntegerValues(PreparedStatement preparedStatement, int... values) throws SQLException {
        for (int index = 0; index < values.length; index++) {
            preparedStatement.setInt(index + 1, values[index]);
        };

        return preparedStatement.executeQuery();
    }

    /**
     * Converts integer to boolean.
     * @param integer Integer number which is to be converted to boolean. Must be 0 or 1.
     * @return Boolean value.
     */
    protected boolean convertIntToBoolean(int integer) {
        boolean booleanValue = false;

        switch (integer) {
            case 0:
                booleanValue = false;
                break;
            case 1:
                booleanValue = true;
                break;
            default:
                break;
        }

        return booleanValue;
    }
}
