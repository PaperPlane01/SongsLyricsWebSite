package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.model.password.Password;
import kz.javalab.songslyricswebsite.model.user.User;
import kz.javalab.songslyricswebsite.model.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UserDataAccessObject {

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
            System.out.println(user.getPassword().getHashedPassword());
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
    public boolean checkIfUserExists(User user, Connection connection) {
        boolean result = false;

        String checkingUserQuery = "SELECT user_id FROM users\n" +
                "WHERE user_name = BINARY ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkingUserQuery);
            preparedStatement.setString(1, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Checks if password is correct.
     * @param userName
     * @param password
     * @return
     */
    public boolean checkIfPasswordValidates(String userName, Password password, Connection connection) {
        boolean result = false;

        int userID = getUserIDByUserName(userName, connection);

        String getPasswordQuery = "SELECT hashed_password FROM users\n" +
                "WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String hashedPasswordFromDatabase = resultSet.getString("hashed_password");
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
                userID = resultSet.getInt("user_id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
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
                hashedPassword = resultSet.getString("hashed_password");
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
