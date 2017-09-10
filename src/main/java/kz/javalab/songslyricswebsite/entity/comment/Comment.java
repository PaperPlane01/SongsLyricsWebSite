package kz.javalab.songslyricswebsite.entity.comment;


import kz.javalab.songslyricswebsite.entity.user.User;

public class Comment {

    private int ID;
    private User author;
    private int songID;
    private String content;

    public Comment() {
    }

    public Comment(int ID, User author, int songID, String content) {
        this.ID = ID;
        this.author = author;
        this.songID = songID;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
