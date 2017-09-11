package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 08.09.2017.
 */
public class CommentsDataAccessObject extends AbstractDataAccessObject {

    /**
     * Returns list of comments of the specific song.
     * @param songID ID of the song, comments of which are to be retrieved.
     * @param connection Connection to be used.
     * @return List of comments of the specific song.
     */
    public List<Comment> getCommentsOfSong(int songID, Connection connection) {
        String getCommentsQuery = "SELECT comment_id, comments.user_id, comment_content, time, user_name, user_role\n" +
                "FROM comments INNER JOIN users\n" +
                "ON comments.user_id = users.user_id\n" +
                "WHERE song_id = ?\n" +
                "ORDER BY comment_id";

        int songIDParameter = 1;

        List<Comment> comments = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getCommentsQuery);

            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                User commentAuthor = new User();

                int commentID = resultSet.getInt(DatabaseConstants.ColumnLabels.CommentsTable.COMMENT_ID);
                String content = resultSet.getString(DatabaseConstants.ColumnLabels.CommentsTable.COMMENT_CONTENT);
                Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.ColumnLabels.CommentsTable.TIME);
                int userID = resultSet.getInt(DatabaseConstants.ColumnLabels.CommentsTable.USER_ID);
                String userName = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.USER_NAME);
                String userRole = resultSet.getString(DatabaseConstants.ColumnLabels.UsersTable.USER_ROLE);

                commentAuthor.setID(userID);
                commentAuthor.setUsername(userName);

                switch (userRole) {
                    case "MODERATOR":
                        commentAuthor.setUserType(UserType.MODERATOR);
                        break;
                    case "COMMON":
                        commentAuthor.setUserType(UserType.COMMON);
                        break;
                    default:
                        break;
                }

                comment.setAuthor(commentAuthor);
                comment.setID(commentID);
                comment.setSongID(songID);
                comment.setTime(timestamp);
                comment.setContent(content);

                comments.add(comment);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    /**
     * Checks if song has comments.
     * @param songID ID of the song which is to be checked.
     * @param connection Connection to be used.
     * @return <Code>True</Code> if the song has comments, <Code>False</Code> if not.
     */
    public boolean checkIfSongHasComments(int songID, Connection connection) {
        boolean result = false;

        String checkIfSongHasCommentsQuery = "SELECT comment_id\n" +
                "FROM comments\n" +
                "WHERE song_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkIfSongHasCommentsQuery);

            result = checkEntityExistence(preparedStatement, songID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Adds comment to the database.
     * @param comment Comment to be added.
     * @param connection Conection to be used.
     */
    public void addCommentToDatabase(Comment comment, Connection connection) {
        String addCommentQuery = "INSERT INTO comments\n" +
                "(song_id, user_id, comment_content)\n" +
                "VALUES" +
                "(?, ?, ?)";

        int songIDParameter = 1;
        int userIDParameter = 2;
        int contentParameter = 3;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addCommentQuery);

            preparedStatement.setInt(songIDParameter, comment.getSongID());
            preparedStatement.setInt(userIDParameter, comment.getAuthor().getID());
            preparedStatement.setString(contentParameter, comment.getContent());

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks specific comment as deleted.
     * @param commentID ID of the comment which is to be marked as deleted.
     * @param connection Connection to be used.
     */
    public void markCommentAsDeleted(int commentID, Connection connection) {
        String markCommentAsDeletedQuery = "UPDATE comments\n" +
                "SET is_deleted = ?\n" +
                "WHERE comment_id = ?";

        int isDeletedValue = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(markCommentAsDeletedQuery);

            executePreparedStatementWithMultipleIntegerValues(preparedStatement, isDeletedValue, commentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean checkEntityExistence(PreparedStatement preparedStatement, int... entityIDs) throws SQLException {
        return super.checkEntityExistence(preparedStatement, entityIDs);
    }

    @Override
    protected void updateStringValueByEntityID(PreparedStatement preparedStatement, int entityID, String newStringValue) throws SQLException {
        super.updateStringValueByEntityID(preparedStatement, entityID, newStringValue);
    }

    @Override
    protected void executePreparedStatementWithMultipleIntegerValues(PreparedStatement preparedStatement, int... values) throws SQLException {
        super.executePreparedStatementWithMultipleIntegerValues(preparedStatement, values);
    }

    @Override
    protected boolean checkEntityExistenceByStringValue(PreparedStatement preparedStatement, String value) throws SQLException {
        return super.checkEntityExistenceByStringValue(preparedStatement, value);
    }
}
