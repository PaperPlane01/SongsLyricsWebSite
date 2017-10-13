package kz.javalab.songslyricswebsite.dataaccessobject;
import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;

import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.service.UsersManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UsersDataAccessObject extends AbstractDataAccessObject {

    private static Logger logger = Logger.getLogger(UsersDataAccessObject.class.getName());

    /**
     * Constructs new <Code>UsersDataAccessObject</Code> instance.
     */
    public UsersDataAccessObject() {
        super();
    }

    /**
     * Add new user do database.
     * @param user User to be added.
     */
    public void addNewUser(User user, Connection connection) throws SQLException {

        String registerNewUserQuery = "INSERT INTO users\n" +
                "(user_name, hashed_password, user_role)\n" +
                "VALUES (?, ?, ?)";

        int userNameParameter = 1;
        int hashedPasswordParameter = 2;
        int userRoleParameter = 3;

        PreparedStatement preparedStatement = connection.prepareStatement(registerNewUserQuery);
        preparedStatement.setString(userNameParameter, user.getUsername());
        preparedStatement.setString(hashedPasswordParameter, user.getPassword().getHashedPassword());
        preparedStatement.setString(userRoleParameter, "COMMON");
        preparedStatement.execute();
        preparedStatement.close();
    }

    /**
     * Checks if user exists.
     * @param user User to be checked.
     * @return <Code>True</Code> if database contains such user, <Code>False</Code> if not.
     */
    public boolean checkIfUserExists(User user, Connection connection, int parameterOfChecking) throws DataAccessException {
        boolean result = false;

        if (parameterOfChecking == UsersManager.CHECK_BY_USERNAME) {
            result = checkIfUserExistsByUserName(user.getUsername(), connection);
        } else if (parameterOfChecking == UsersManager.CHECK_BY_USER_ID) {
            result = checkIfUserExistsByUserID(user.getID(), connection);
        }

        return result;
    }

    /**
     * Checks if user with specified username exists.
     * @param username Username be be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if user with such username exists, <Code>False</Code> if not.
     */
    private boolean checkIfUserExistsByUserName(String username, Connection connection) throws DataAccessException {
        String checkingUserQuery = "SELECT user_id FROM users\n" +
                "WHERE user_name = BINARY ?";

        boolean result = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkingUserQuery);
            result = checkEntityExistenceByStringValue(preparedStatement, username);
        } catch (SQLException e) {
           logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_USER_EXISTENCE_BY_USERNAME, e);
           throw new DataAccessException();
        }

        return result;
    }

    /**
     * Checks if user with specified ID exists.
     * @param userID User ID to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if user with such ID exists, <Code>False</Code> if not.
     */
    private boolean checkIfUserExistsByUserID(int userID, Connection connection) throws DataAccessException {
        String checkingUserQuery = "SELECT user_id FROM users\n" +
                "WHERE user_id = ?";

        boolean result = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkingUserQuery);
            result = checkEntityExistence(preparedStatement, userID);
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_USER_EXISTENCE_BY_USER_ID, e);
            throw new DataAccessException();
        }

        return result;
    }

    /**
     * Checks if password is correct.
     * @param userName Username of user to be checked.
     * @param password Password to be checked.
     * @return <Code>True</Code> if password is correct, <Code>False</Code> if not.
     */
    public boolean checkIfPasswordCorrect(String userName, Password password, Connection connection) throws DataAccessException {
        boolean result = false;

        int userID = getUserIDByUserName(userName, connection);

        String getPasswordQuery = "SELECT hashed_password FROM users\n" +
                "WHERE user_id = ?";
        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String hashedPasswordFromDatabase = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.HASHED_PASSWORD);
                result = password.getHashedPassword().equals(hashedPasswordFromDatabase.trim());
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_PASSWORD_CORRECTNESS, e);
            throw new DataAccessException();
        }

        return result;
    }

    /**
     * Retrieves user ID from database using username.
     * @param userName Username to be checked.
     * @return ID of specific user.
     */
    public int getUserIDByUserName(String userName, Connection connection) throws DataAccessException {

        int userID = 0;

        String getUserIDQuery = "SELECT user_id FROM users\n" +
                "WHERE user_name = ?";

        int userNameParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserIDQuery);
            preparedStatement.setString(userNameParameter, userName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userID = resultSet.getInt(DatabaseConstants.ColumnLabels.UsersTable.USER_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_USER_ID_BY_USERNAME, e);
            throw new DataAccessException();
        }

        return userID;
    }

    /**
     * Returns username by user ID.
     * @param userID User ID.
     * @param connection Connection to be used.
     * @return Username of the user with specified ID.
     */
    public String getUserNameByUserID(int userID, Connection connection) throws DataAccessException {
        String userName = new String();

        String getUsrNameQuery = "SELECT user_name FROM users\n" +
                "WHERE user_id = ?";

        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUsrNameQuery);

            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userName = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.USER_NAME);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_USERNAME_BY_USER_ID);
            throw new DataAccessException();
        }

        return userName;
    }

    /**
     * Retrieves hashed password of the specific user from the database.
     * @param userID ID of the user.
     * @return Password of the specific user
     */
    public Password getPasswordByUserID(int userID, Connection connection) throws DataAccessException {
        String hashedPassword = new String();

        String getHashedPasswordQuery = "SELECT hashed_password FROM users\n" +
                "WHERE user_id = ?";

        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getHashedPasswordQuery);
            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                hashedPassword = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.HASHED_PASSWORD);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_PASSWORD, e);
            throw new DataAccessException();
        }

        Password password = new Password();
        password.setHashedPassword(hashedPassword);

        return password;
    }

    /**
     * Retrieves <Code>UserType</Code> of the specific user from the database.
     * @param userID ID of the user.
     * @return <Code>UserType</Code> of the specific user.
     */
    public UserType getUserTypeByUserID(int userID, Connection connection) throws DataAccessException {
        String result = new String();

        String getUserTypeQuery = "SELECT user_role FROM users\n" +
                "WHERE user_id = ?";

        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserTypeQuery);

            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.USER_ROLE);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_GETTING_USER_TYPE_BY_USER_ID, e);
            throw new DataAccessException();
        }

        return UserType.valueOf(result);
    }

    /**
     * Marks user as blocked.
     * @param userID ID of the user which is to be blocked.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void markUserAsBlocked(int userID, Connection connection) throws SQLException {
        String blockUserQuery = "UPDATE users\n" +
                "SET is_blocked = ?\n" +
                "WHERE user_id = ?";

        int isBlockedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(blockUserQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isBlockedValue, userID);
    }

    public void markUserAsUnblocked(int userID, Connection connection) throws SQLException {
        String unblockUserQuery = "UPDATE users\n" +
                "SET is_blocked = ?\n" +
                "WHERE user_id = ?";

        int isBlockedValue = 0;

        PreparedStatement preparedStatement = connection.prepareStatement(unblockUserQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isBlockedValue, userID);
    }

    /**
     * Checks if user with specified ID is blocked.
     * @param userID ID of the user to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if user with such ID is blocked, <Code>False</Code> if not.
     */
    public boolean checkIfUserIsBlocked(int userID, Connection connection) throws DataAccessException {
        boolean isBlocked = false;

        String checkIfUserIsBlockedQuery = "SELECT is_blocked\n" +
                "FROM users\n" +
                "WHERE user_id = ?";

        int userIDParameter = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkIfUserIsBlockedQuery);
            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int isBlockedValue = resultSet.getInt(DatabaseConstants.ColumnLabels.UsersTable.IS_BLOCKED);

                switch (isBlockedValue) {
                    case 0:
                        isBlocked = false;
                        break;
                    case 1:
                        isBlocked = true;
                        break;
                    default:
                        isBlocked = false;
                        break;
                }
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHECKING_IF_USER_IS_BLOCKED, e);
            throw new DataAccessException();
        }

        return isBlocked;
    }

    /**
     * Changes password of the specified user.
     * @param userID ID of the user.
     * @param newPassword New password.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occured when attempted to modify data.
     */
    public void changePassword(int userID, Password newPassword, Connection connection) throws SQLException {
        String changePasswordQuery = "UPDATE users\n" +
                "SET hashed_password = ?\n" +
                "WHERE user_id = ?";

        int hashedPasswordParameter = 1;
        int userIDParameter = 2;

        PreparedStatement preparedStatement = connection.prepareStatement(changePasswordQuery);

        preparedStatement.setString(hashedPasswordParameter, newPassword.getHashedPassword());
        preparedStatement.setInt(userIDParameter, userID);

        preparedStatement.execute();

        preparedStatement.close();
    }
}
