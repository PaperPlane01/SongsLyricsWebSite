package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.UserDataAccessObject;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UserService {

    private UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
    public static final int CHECK_BY_USERNAME = 1;
    public static final int CHECK_BY_USER_ID = 2;

    public void registerNewUser(User user) throws SuchUserAlreadyExistsException, RegistrationFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();

        if (checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            throw new SuchUserAlreadyExistsException();
        }

        try {
            connection.setAutoCommit(false);
            userDataAccessObject.registerNewUser(user, connection);
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RegistrationFailedException();
            }

            throw new RegistrationFailedException();
        }

        connectionPool.returnConnection(connection);
    }

    public void doLogin(User user) throws InvalidPasswordException, InvalidUserNameException{
        Connection connection = ConnectionPool.getInstance().getConnection();

        if (!checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidUserNameException();
        }

        if (!checkIfPasswordValidates(user.getUsername(), user.getPassword(), connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidPasswordException();
        }
    }

    private boolean checkIfUserExists(User user, int parameterOfChecking, Connection connection) {
        boolean userExists = userDataAccessObject.checkIfUserExists(user, connection, parameterOfChecking);
        ConnectionPool.getInstance().returnConnection(connection);
        return userExists;
    }

    private boolean checkIfPasswordValidates(String userName, Password password, Connection connection) {
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

    public String getUserNameByUserID(int userID) throws InvalidUserIDException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        User user = new User();
        user.setID(userID);

        if (checkIfUserExists(user, CHECK_BY_USER_ID, connection)) {
            String userName = userDataAccessObject.getUserNameByUserID(userID, connection);
            ConnectionPool.getInstance().returnConnection(connection);
            return userName;
        } else {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidUserIDException();
        }

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
