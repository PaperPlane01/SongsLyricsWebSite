package kz.javalab.songslyricswebsite.dataaccessobject;
import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;

import kz.javalab.songslyricswebsite.service.UsersManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UsersDataAccessObject extends AbstractDataAccessObject {

    public UsersDataAccessObject() {
    }

    /**
     * Add new user do database.
     * @param user User to be added.
     */
    public void registerNewUser(User user, Connection connection) {
        int lastID = getLastUserID(connection);

        String registerNewUserQuery = "INSERT INTO `websitedatabase`.`users`\n" +
                "(`user_id`,\n" +
                "`user_name`,\n" +
                "`hashed_password`,\n" +
                "`user_role`)\n" +
                "VALUES\n" +
                "(?,\n" +
                "?,\n" +
                "?,\n" +
                "?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(registerNewUserQuery);
            preparedStatement.setInt(1, lastID + 1);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword().getHashedPassword());
            preparedStatement.setString(4, "COMMON");
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if user exists.
     * @param user User to be checked.
     * @return <Code>True</Code> if database contains such user, <Code>False</Code> if not.
     */
    public boolean checkIfUserExists(User user, Connection connection, int parameterOfChecking) {
        boolean result = false;

        if (parameterOfChecking == UsersManager.CHECK_BY_USERNAME) {
            result = checkIfUserExistsByUserName(user.getUsername(), connection);
        } else if (parameterOfChecking == UsersManager.CHECK_BY_USER_ID) {
            result = checkIfUserExistsByUserID(user.getID(), connection);
        }

        return result;
    }

    private boolean checkIfUserExistsByUserName(String username, Connection connection) {
        String checkingUserQuery = "SELECT user_id FROM users\n" +
                "WHERE user_name = BINARY ?";

        boolean result = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkingUserQuery);
            result = checkEntityExistenceByStringValue(preparedStatement, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean checkIfUserExistsByUserID(int userID, Connection connection) {
        String checkingUserQuery = "SELECT user_id FROM users\n" +
                "WHERE user_id = ?";

        boolean result = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkingUserQuery);
            result = checkEntityExistence(preparedStatement, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Checks if password is correct.
     * @param userName Username of user to be checked.
     * @param password Password to be checked.
     * @return
     */
    public boolean checkIfPasswordCorrect(String userName, Password password, Connection connection) {
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
                result = password.getHashedPassword().trim().equals(hashedPasswordFromDatabase.trim());
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Retrieves user ID from database using username.
     * @param userName Username to be checked.
     * @return ID of specific user.
     */
    public int getUserIDByUserName(String userName, Connection connection) {

        int userID = 0;

        String getUserIDQuery = "SELECT user_id FROM users\n" +
                "WHERE user_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserIDQuery);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userID = resultSet.getInt(DatabaseConstants.ColumnLabels.UsersTable.USER_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public String getUserNameByUserID(int userID, Connection connection) {
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
            e.printStackTrace();
        }

        return userName;
    }

    /**
     * Retrieves hashed password of the specific user from the database.
     * @param userID ID of the user.
     * @return Hashed password of the specific user
     */
    public String getHashedPasswordByUserID(int userID, Connection connection) {
        String hashedPassword = new String();

        String getHashedPasswordQuery = "SELECT hashed_password FROM users\n" +
                "WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getHashedPasswordQuery);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                hashedPassword = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.HASHED_PASSWORD);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }

    /**
     * Retrieves <Code>UserType</Code> of the specific user from the database.
     * @param userID ID of the user.
     * @return <Code>UserType</Code> of the specific user.
     */
    public UserType getUserTypeByUserID(int userID, Connection connection) {
        String result = new String();

        String getUserTypeQuery = "SELECT user_role FROM users\n" +
                "WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserTypeQuery);

            int userIDParameter = 1;

            preparedStatement.setInt(userIDParameter, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.USER_ROLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return UserType.valueOf(result);
    }

    public void blockUser(int userID, Connection connection) throws SQLException {
        String blockUserQuery = "UPDATE users\n" +
                "SET is_blocked = ?\n" +
                "WHERE user_id = ?";

        int isBlockedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(blockUserQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isBlockedValue, userID);
    }

    /**
     * Retrieves last user ID from the database.
     * @param connection Connection to be used.
     * @return Last user ID from the database.
     */
    private int getLastUserID(Connection connection) {
        int maxUserID = 0;
        String getLastUserIDQuery = "SELECT max(user_id) AS biggest_id FROM users";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLastUserIDQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                maxUserID = resultSet.getInt("biggest_id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return maxUserID;
    }
}
