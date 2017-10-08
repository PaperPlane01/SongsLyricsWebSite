package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.conntectionpool.ConnectionPool;
import kz.javalab.songslyricswebsite.constant.LoggingConstants;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.dataaccessobject.UsersDataAccessObject;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is designated for managing users.
 */
public class UsersManager {

    /**
     * <Code>RequestWrapper</Code> instance which contains data sent by user.
     */
    private RequestWrapper requestWrapper;

    /**
     * Constant for {@link #checkIfUserExists(User, int, Connection)} method.
     * Indicates that checking of user's existence should be done using username.
     */
    public static final int CHECK_BY_USERNAME = 1;

    /**
     * Constant for {@link #checkIfUserExists(User, int, Connection)} method.
     * Indicates that checking of user's existence should be done using user ID.
     */
    public static final int CHECK_BY_USER_ID = 2;
    private static Logger logger = Logger.getLogger(UsersManager.class.getName());

    /**
     * Constructs <Code>UsersManager</Code> instance.
     */
    public UsersManager() {
    }

    /**
     * Constructs <Code>UsersManager</Code> instance with pre-defined request wrapper.
     * @param requestWrapper <Code>RequestWrapper</Code> instance which contains data sent by user.
     */
    public UsersManager(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public RequestWrapper getRequestWrapper() {
        return requestWrapper;
    }

    public void setRequestWrapper(RequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    /**
     * Registers new user.
     * @throws SuchUserAlreadyExistsException Thrown if user with specified username already exists.
     * @throws RegistrationFailedException Thrown if some error occurred when attempted to insert data into database.
     * @throws InvalidUserNameException Thrown if username if invalid.
     * @throws InvalidPasswordException Thrown if password is invalid.
     * @throws PasswordsAreNotEqualException Thrown if first password and second password sent by user are not equal.
     */
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
            ConnectionPool.getInstance().returnConnection(connection);
            throw new SuchUserAlreadyExistsException();
        }

        if (!validateUserName(userName)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidUserNameException();
        }

        if (!validatePassword(passwordAsString)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidPasswordException();
        }

        if (!passwordAsString.equals(secondPasswordAsString)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new PasswordsAreNotEqualException();
        }

        Password password = new Password();
        password.encodePassword(passwordAsString);

        user.setPassword(password);

        try {
            connection.setAutoCommit(false);
            usersDataAccessObject.addNewUser(user, connection);
            connection.commit();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_REGISTERING_USER, e);

            try {
                connection.rollback();
                throw new RegistrationFailedException();
            } catch (SQLException e1) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                throw new RegistrationFailedException();
            }

        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Validates username.
     * @param userName Username to be validated.
     * @return <Code>True</Code> if username is valid, <Code>False</Code> if not.
     */
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

    /**
     * Validates password.
     * @param unEncodedPassword Password to be validated.
     * @return <Code>True</Code> if password is valid, <Code>False</Code> if not.
     */
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

    /**
     * Does login.
     * @throws WrongPasswordException Thrown if password is wrong.
     * @throws WrongUsernameException Thrown if username is wrong.
     */
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

    /**
     * Checks if such user exists.
     * @param user User to be checked.
     * @param parameterOfChecking Specifies the parameter of checking.
     *                            1 - checks user's existence by username.
     *                            2 - checks user's existence by user ID.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if such user exists, <Code>False</Code> if not.
     */
    private boolean checkIfUserExists(User user, int parameterOfChecking, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.checkIfUserExists(user, connection, parameterOfChecking);
    }

    /**
     * Checks if password is correct.
     * @param userName Name of the user.
     * @param password Password which has been sent by user.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if password is correct, <Code>False</Code> if not.
     */
    private boolean checkIfPasswordCorrect(String userName, Password password, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.checkIfPasswordCorrect(userName, password, connection);
    }

    /**
     * Retrieves user ID by username.
     * @param userName Username.
     * @param connection Connection to be used.
     * @return User ID.
     */
    private int getUserIDByUserName(String userName, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.getUserIDByUserName(userName, connection);
    }

    /**
     * Retrieves username by user ID.
     * @param userID User ID.
     * @return Username.
     * @throws InvalidUserIDException Thrown if there is no user with such ID.
     */
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

    /**
     * Retrieves user type by user ID.
     * @param userID User ID.
     * @return User type of the user with specific ID.
     */
    public UserType getUserTypeByUserID(int userID) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        UserType userType = getUserTypeByUserID(userID, connection);
        ConnectionPool.getInstance().returnConnection(connection);
        return userType;
    }

    /**
     * Retrieves user type by user ID.
     * @param userID User ID.
     * @param connection Connection to be used.
     * @return User type of the user with specific ID.
     */
    private UserType getUserTypeByUserID(int userID, Connection connection) {
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();
        return usersDataAccessObject.getUserTypeByUserID(userID, connection);
    }

    /**
     * Blocks user.
     * @throws NoPermissionException Thrown if attempt of blocking has been made by not logged in user,
     *                               or by user who does not have moderator's privileges.
     * @throws UserBlockingException Thrown if some error occurred when attempted to update data.
     * @throws InvalidUserIDException Thrown of there is no user with received ID.
     */
    public void blockUser() throws NoPermissionException, UserBlockingException, InvalidUserIDException {
        User currentUser = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);
        Connection connection = ConnectionPool.getInstance().getConnection();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();

        if (currentUser == null) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new NoPermissionException();
        }

        if (currentUser.getUserType() != UserType.MODERATOR) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new NoPermissionException();
        }

        try {
            int userID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.USER_ID));
            User user = new User();
            user.setID(userID);

            try {
                connection.setAutoCommit(false);

                if (!checkIfUserExists(user, CHECK_BY_USER_ID, connection)) {
                    ConnectionPool.getInstance().returnConnection(connection);
                    throw new InvalidUserIDException();
                }

                usersDataAccessObject.markUserAsBlocked(userID, connection);
                connection.commit();
            } catch (SQLException e) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_BLOCKING_USER, e);

                try {
                    connection.rollback();
                    throw new UserBlockingException();
                } catch (SQLException e1) {
                    logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                    throw new UserBlockingException();
                }

            } finally {
                ConnectionPool.getInstance().returnConnection(connection);
            }
        } catch (NumberFormatException e) {
            throw new InvalidUserIDException();
        }

    }

    public void unblockUser() throws InvalidUserIDException, UserUnblockingException, NoPermissionException {
        User currentUser = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);
        User user = new User();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();

        Connection connection = ConnectionPool.getInstance().getConnection();

        if (currentUser == null) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new NoPermissionException();
        }

        if (currentUser.getUserType() != UserType.MODERATOR) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new NoPermissionException();
        }


        try {
            int userID = Integer.valueOf(requestWrapper.getRequestParameter(RequestConstants.RequestParameters.USER_ID));
            user.setID(userID);

            try {
                connection.setAutoCommit(false);

                if (!checkIfUserExists(user, CHECK_BY_USER_ID, connection)) {
                    ConnectionPool.getInstance().returnConnection(connection);
                    throw new InvalidUserIDException();
                }

                usersDataAccessObject.markUserAsUnblocked(user.getID(), connection);

                System.out.println(connection.getAutoCommit());

                connection.commit();
            } catch (SQLException e) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_UNBLOCKING_USER, e);

                try {
                    connection.rollback();
                    throw new UserUnblockingException();
                } catch (SQLException e1) {
                    logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                    throw new UserUnblockingException();
                }
            } finally {
                ConnectionPool.getInstance().returnConnection(connection);
            }
        } catch (NumberFormatException e) {
            throw new InvalidUserIDException();
        }

    }

    public void changePassword() throws UserNotLoggedInException, WrongPasswordException, InvalidPasswordException, PasswordsAreNotEqualException, PasswordChangingException {
        User currentUser = (User) requestWrapper.getSessionAttribute(RequestConstants.SessionAttributes.USER);

        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }

        Connection connection = ConnectionPool.getInstance().getConnection();
        UsersDataAccessObject usersDataAccessObject = new UsersDataAccessObject();

        String passwordReceivedFromUser = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.CURRENT_PASSWORD);

        Password currentPassword = usersDataAccessObject.getPasswordByUserID(currentUser.getID(), connection);

        if (!currentPassword.matches(passwordReceivedFromUser)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new WrongPasswordException();
        }

        String newPasswordAsString = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.NEW_PASSWORD);

        if (!validatePassword(newPasswordAsString)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new InvalidPasswordException();
        }

        String duplicatedNewPasswordAsString = requestWrapper.getRequestParameter(RequestConstants.RequestParameters.DUPLICATED_NEW_PASSWORD);

        if (!newPasswordAsString.equals(duplicatedNewPasswordAsString)) {
            ConnectionPool.getInstance().returnConnection(connection);
            throw new PasswordsAreNotEqualException();
        }

        try {
            connection.setAutoCommit(false);

            Password newPassword = new Password();
            newPassword.encodePassword(newPasswordAsString);

            usersDataAccessObject.changePassword(currentUser.getID(), newPassword, connection);

            connection.commit();
        } catch (SQLException e) {
            logger.error(LoggingConstants.EXCEPTION_WHILE_CHANGING_PASSWORD, e);
            try {
                connection.rollback();
                throw new PasswordChangingException();
            } catch (SQLException e1) {
                logger.error(LoggingConstants.EXCEPTION_WHILE_ROLLING_TRANSACTION_BACK, e1);
                throw new PasswordChangingException();
            }
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
