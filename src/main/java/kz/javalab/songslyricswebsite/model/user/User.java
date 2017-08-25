package kz.javalab.songslyricswebsite.model.user;


import kz.javalab.songslyricswebsite.model.password.Password;

public class User {

    private int ID;
    private String username;
    private UserType userType;
    private Password password;

    public User() {
    }

    public User(int ID, String username, UserType userType, Password password) {
        this.ID = ID;
        this.username = username;
        this.userType = userType;
        this.password = password;
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
}
