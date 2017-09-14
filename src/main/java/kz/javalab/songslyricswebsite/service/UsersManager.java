package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsRatingsDataAccessObject;
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
public class UsersManager {

    public static final int CHECK_BY_USERNAME = 1;
    public static final int CHECK_BY_USER_ID = 2;

    public UsersManager() {
    }

    public void registerNewUser(User user) throws SuchUserAlreadyExistsException, RegistrationFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();

        if (checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            throw new SuchUserAlreadyExistsException();
        }

        try {
            connection.setAutoCommit(false);
            userDataAccessObject.registerNewUser(user, connection);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RegistrationFailedException();
            }

            throw new RegistrationFailedException();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void doLogin(User user) throws WrongPasswordException, WrongUsernameException { ;
        Connection connection = ConnectionPool.getInstance().getConnection();

        if (!checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new WrongUsernameException();
        }

        if (!checkIfPasswordCorrect(user.getUsername(), user.getPassword(), connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new WrongPasswordException();
        }

        ConnectionPool.getInstance().returnConnection(connection);
    }

    public void voteForSong(int userID, int songID, int rating) {

    }

    public boolean checkIfUserRatedSong(int userID, int songID) {
        SongsRatingsDataAccessObject songsRatingsDataAccessObject = new SongsRatingsDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        boolean result = songsRatingsDataAccessObject.checkIfUserRatedSong(userID, songID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return result;
    }

    private boolean checkIfUserExists(User user, int parameterOfChecking, Connection connection) {
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        boolean userExists = userDataAccessObject.checkIfUserExists(user, connection, parameterOfChecking);
        ConnectionPool.getInstance().returnConnection(connection);
        return userExists;
    }

    private boolean checkIfPasswordCorrect(String userName, Password password, Connection connection) {
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        boolean passwordValidates = userDataAccessObject.checkIfPasswordCorrect(userName, password, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return passwordValidates;
    }

    public int getUserIDByUserName(String userName) {
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();
        int userID =  userDataAccessObject.getUserIDByUserName(userName, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userID;
    }

    public String getUserNameByUserID(int userID) throws InvalidUserIDException {
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
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

    public UserType getUserTypeByUserID(int userID) {
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
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
