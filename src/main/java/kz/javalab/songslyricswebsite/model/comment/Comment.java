package kz.javalab.songslyricswebsite.model.comment;


public class Comment {

    private long ID;
    private String content;

    public Comment() {
    }

    public Comment(long ID, String content) {
        this.ID = ID;
        this.content = content;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
