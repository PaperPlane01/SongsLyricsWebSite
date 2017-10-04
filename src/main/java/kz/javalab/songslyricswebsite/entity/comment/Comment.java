package kz.javalab.songslyricswebsite.entity.comment;


import kz.javalab.songslyricswebsite.entity.user.User;

import java.sql.Timestamp;

/**
 * This class represents a comment.
 */
public class Comment {

    /**
     * ID of the comment.
     */
    private int ID;

    /**
     * Author of the comment.
     */
    private User author;

    /**
     * ID of the song
     */
    private int songID;

    /**
     * Content of the comment.
     */
    private String content;

    /**
     * Date of the comment.
     */
    private Timestamp time;

    /**
     * Constructs <Code>Comment</Code> instance.
     */
    public Comment() {
    }

    /**
     * Constructs <Code>Comment</Code> instance with pre-defined ID, author, song ID and content.
     * @param ID
     * @param author
     * @param songID
     * @param content
     */
    public Comment(int ID, User author, int songID, String content) {
        this.ID = ID;
        this.author = author;
        this.songID = songID;
        this.content = content;
    }

    /**
     * Returns ID of the comment.
     * @return ID of the comment.
     */
    public int getID() {
        return ID;
    }

    /**
     * Modifies ID of the comment.
     * @param ID New ID to be set.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns author of the comment.
     * @return Author of the comment.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Modifies author of the comment.
     * @param author New author to be set.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Returns ID of the song.
     * @return ID of the song.
     */
    public int getSongID() {
        return songID;
    }

    /**
     * Modifies ID of the song.
     * @param songID New ID of the song to be set.
     */
    public void setSongID(int songID) {
        this.songID = songID;
    }

    /**
     * Returns content of the comment.
     * @return Content of the comment.
     */
    public String getContent() {
        return content;
    }

    /**
     * Modifies content of the comment.
     * @param content New content to be set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns date of the comment.
     * @return Date of the comment.
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Modifies date of the comment.
     * @param time New date to be set.
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }
}
