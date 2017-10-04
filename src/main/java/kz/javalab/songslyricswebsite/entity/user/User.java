package kz.javalab.songslyricswebsite.entity.user;

import kz.javalab.songslyricswebsite.entity.password.Password;

/**
 * This class represents a user.
 */
public class User {

    /**
     * ID of the user.
     */
    private int ID;

    /**
     * Username
     */
    private String username;

    /**
     * Type of the user.
     */
    private UserType userType;

    /**
     * Password of the user.
     */
    private Password password;

    /**
     * Indicates if user has been blocked.
     */
    private boolean blocked;

    /**
     * Constructs new <Code>User</Code> instance.
     */
    public User() {
    }

    /**
     * Constructs new <Code>User</Code> instance with pre-defined ID, username, user type, password and blocked state.
     * @param ID ID of the user.
     * @param username Name of the user.
     * @param userType Type of the user.
     * @param password Password of the user.
     * @param blocked Indicates if user has been blocked.
     */
    public User(int ID, String username, UserType userType, Password password, boolean blocked) {
        this.ID = ID;
        this.username = username;
        this.userType = userType;
        this.password = password;
        this.blocked = blocked;
    }

    /**
     * Constructs new <Code>User</Code> instance with pre-defined username and password.
     * @param username Name of the user.
     * @param password Password of the user.
     */
    public User(String username, Password password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns ID of the user.
     * @return ID of the user.
     */
    public int getID() {
        return ID;
    }

    /**
     * Modifies ID of the user.
     * @param ID New ID to be set.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns name of the user.
     * @return Name of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Modifies name of the user.
     * @param username New username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns type of the user.
     * @return Type of the user.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Modifies type of the user.
     * @param userType New type of the user to be set.
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Returns password of the user.
     * @return Password of the user.
     */
    public Password getPassword() {
        return password;
    }

    /**
     * Modifies password of the user.
     * @param password New password to be set.
     */
    public void setPassword(Password password) {
        this.password = password;
    }

    /**
     * Allows to check if user has been blocked.
     * @return <Code>True</Code> if user has been blocked, <Code>False</Code> if not.
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Modifies user's blocking status.
     * @param blocked New blocking status to be set.
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
