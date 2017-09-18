package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.SongsRatingsDataAccessObject;
import kz.javalab.songslyricswebsite.dataaccessobject.UsersDataAccessObject;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PaperPlane on 05.08.2017.
 */
public class UsersManager {

    private RequestWrapper requestWrapper;
    public static final int CHECK_BY_USERNAME = 1;
    public static final int CHECK_BY_USER_ID = 2;

    public UsersManager() {
    }

    public UsersManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public void registerNewUser() throws SuchUserAlreadyExistsException, RegistrationFailedException, InvalidUserNameException, InvalidPasswordException, PasswordsAreNotEqualException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();

        String userName = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.USERNAME);
        String passwordAsString = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.PASSWORD);
        String secondPasswordAsString = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.SECOND_PASSWORD);

        User user = new User();
        user.setUsername(userName);

        if (checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            throw new SuchUserAlreadyExistsException();
        }

        if (!validateUserName(userName)) {
            throw new InvalidUserNameException();
        }

        if (!validatePassword(passwordAsString)) {
            throw new InvalidPasswordException();
        }

        if (!passwordAsString.equals(secondPasswordAsString)) {
            throw new PasswordsAreNotEqualException();
        }

        Password password = new Password();
        password.encodePassword(passwordAsString);

        user.setPassword(password);

        try {
            connection.setAutoCommit(false);
            usersDataAccessObject.registerNewUser(user, connection);
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

    private boolean validateUserName(String userName) {
        int minSize = 3;
        int maxSize = 20;

        if (userName == null) {
            return false;
        }

        if (userName.isEmpty()) {
            return false;
        }

        if (userName.length() < minSize) {
            return false;
        }

        if (userName.length() > maxSize) {
            return  false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я_.#$%^&]*");
        Matcher matcher = pattern.matcher(userName);

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

    private boolean validatePassword(String unEncodedPassword) {
        int minSize = 5;
        int maxSize = 25;

        if (unEncodedPassword == null) {
            return false;
        }

        if (unEncodedPassword.isEmpty()) {
            return false;
        }

        if (unEncodedPassword.length() < minSize) {
            return false;
        }

        if (unEncodedPassword.length() > maxSize) {
            return false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я_.#$%^&]*");
        Matcher matcher = pattern.matcher(unEncodedPassword);

        if (!(matcher.matches())) {
            return false;
        }

        return true;
    }

    public void doLogin() throws WrongPasswordException, WrongUsernameException { ;
        Connection connection = ConnectionPool.getInstance().getConnection();

        String userName = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.USERNAME);
        String passwordAsString = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.PASSWORD);
        Password password = new Password();
        password.encodePassword(passwordAsString);

        User user = new User(userName, password);

        if (!checkIfUserExists(user, CHECK_BY_USERNAME, connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new WrongUsernameException();
        }

        if (!checkIfPasswordCorrect(user.getUsername(), user.getPassword(), connection)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new WrongPasswordException();
        }

        user.setID(getUserIDByUserName(userName, connection));
        user.setUserType(getUserTypeByUserID(user.getID(), connection));
        requestWrapper.setSessionAttribute(RequestConstants.SessionAttributes.USER, user);

        ConnectionPool.getInstance().returnConnection(connection);
    }

    private boolean checkIfUserExists(User user, int parameterOfChecking, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        boolean userExists = usersDataAccessObject.checkIfUserExists(user, connection, parameterOfChecking);
        ConnectionPool.getInstance().returnConnection(connection);
        return userExists;
    }

    private boolean checkIfPasswordCorrect(String userName, Password password, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        boolean passwordValidates = usersDataAccessObject.checkIfPasswordCorrect(userName, password, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return passwordValidates;
    }

    private int getUserIDByUserName(String userName, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.getUserIDByUserName(userName, connection);
    }

    public String getUserNameByUserID(int userID) throws InvalidUserIDException {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        Connection connection = ConnectionPool.getInstance().getConnection();

        User user = new User();
        user.setID(userID);

        if (checkIfUserExists(user, CHECK_BY_USER_ID, connection)) {
            String userName = usersDataAccessObject.getUserNameByUserID(userID, connection);
            ConnectionPool.getInstance().returnConnection(connection);
            return userName;
        } else {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidUserIDException();
        }

    }

    public UserType getUserTypeByUserID(int userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserType userType = getUserTypeByUserID(userID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userType;
    }

    private UserType getUserTypeByUserID(int userID, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.getUserTypeByUserID(userID, connection);
    }

    public void changePassword(int userID, String oldHashedPassword, String newHashedPassword) {

    }

    public void changeUserType(int userID, UserType newUserType) {

    }
}
