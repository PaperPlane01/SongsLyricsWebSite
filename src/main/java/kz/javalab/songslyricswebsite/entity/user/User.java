package kz.javalab.songslyricswebsite.entity.user;

import kz.javalab.songslyricswebsite.entity.password.Password;

public class User {

    private int ID;
    private String username;
    private UserType userType;
    private Password password;
    private boolean blocked;

    public User() {
    }

    public User(int ID, String username, UserType userType, Password password, boolean blocked) {
        this.ID = ID;
        this.username = username;
        this.userType = userType;
        this.password = password;
        this.blocked = blocked;
    }

    public User(String username, Password password) {
        this.username = username;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
