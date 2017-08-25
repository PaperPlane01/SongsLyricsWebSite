package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.UserDataAccessObject;
import kz.javalab.songslyricswebsite.model.password.Password;
import kz.javalab.songslyricswebsite.model.user.User;
import kz.javalab.songslyricswebsite.model.user.UserType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UserService {

    private UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

    public void registerNewUser(User user) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        try {
            connection.setAutoCommit(false);
            userDataAccessObject.registerNewUser(user, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        connectionPool.returnConnection(connection);

    }

    public boolean checkIfUserExists(User user) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean userExists = userDataAccessObject.checkIfUserExists(user, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userExists;
    }

    public boolean checkIfPasswordValidates(String userName, Password password) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean passwordValidates = userDataAccessObject.checkIfPasswordValidates(userName, password, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return passwordValidates;
    }

    public int getUserIDByUserName(String userName) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        int userID =  userDataAccessObject.getUserIDByUserName(userName, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userID;
    }

    public Password getHashedPasswordByUserID(int userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Password password = new Password();
        String hashedPassword = userDataAccessObject.getHashedPasswordByUserID(userID, connection);
        password.setHashedPassword(hashedPassword);
        ConnectionPool.getInstance().returnConnection(connection);
        return password;
    }

    public UserType getUserTypeByUserID(int userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserType userType = userDataAccessObject.getUserTypeByUserID(userID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userType;
    }

    public void changePassword(int userID, String oldHashedPassword, String newHashedPassword) {

    }

    public void changeUserType(int userID, UserType newUserType) {

    }
}
