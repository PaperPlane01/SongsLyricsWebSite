package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.SongAddingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class LinesDataAccessObject extends AbstractDataAccessObject {

    public LinesDataAccessObject() {
    }

    public void alterLine(int lineID, Line newLine, Connection connection) throws SQLException {
        String alterLineQuery = "UPDATE websitedatabase.lines\n" +
                "SET content = ?\n" +
                "WHERE line_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(alterLineQuery);

        updateStringValueByEntityID(preparedStatement, lineID, newLine.toString());

    }

    public void alterLyricsPart(int lineID, SongLyricsPartType songLyricsPartType, Connection connection) throws SQLException {
        String alterLyricsPartQuery = "UPDATE websitedatabase.lines\n" +
                "SET song_part = ?\n" +
                "WHERE line_id = ?";
        int songPartParameter = 1;
        int lineIDParameter = 2;

        PreparedStatement preparedStatement = connection.prepareStatement(alterLyricsPartQuery);

        preparedStatement.setString(songPartParameter, songLyricsPartType.toString().toLowerCase());
        preparedStatement.setInt(lineIDParameter, lineID);

        preparedStatement.execute();

        preparedStatement.close();

    }

    public void addLineToDatabase(int linePosition, int songID, Line line, SongLyricsPartType songLyricsPartType, Connection connection) {
        String addLineQuery = "INSERT INTO websitedatabase.lines\n" +
                "(content, song_id, song_part, line_position)\n" +
                "VALUES (?, ?, ?, ?)";
        int lineContentParameter = 1;
        int songIDParameter = 2;
        int songPartParameter = 3;
        int linePositionParameter = 4;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addLineQuery);

            preparedStatement.setString(lineContentParameter, line.toString());
            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setString(songPartParameter, songLyricsPartType.toString());
            preparedStatement.setInt(linePositionParameter, linePosition);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markLineAsDeleted(int lineID, Connection connection) throws SQLException {
        String markLineAsDeletedQuery = "UPDATE websitedatabase.lines\n" +
                "SET is_deleted = ?\n" +
                "WHERE line_id = ?";

        int isDeletedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(markLineAsDeletedQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isDeletedValue, lineID);

    }

    public int getLineID(int linePosition, int songID, Connection connection) {
        int lineID = 0;

        String getLineIDQuery = "SELECT line_id FROM websitedatabase.lines\n" +
                "WHERE song_id = ? AND line_position = ?";

        int songIDParameter = 1;
        int linePositionParameter = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getLineIDQuery);

            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(linePositionParameter, linePosition);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lineID = resultSet.getInt(DatabaseConstants.ColumnLabels.LinesTable.LINE_ID);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineID;
    }

    public void addSongLyricsToDatabase(Song song, Connection connection) {
        String addLineQuery = "INSERT INTO websitedatabase.lines (song_id, content, song_part, line_position)\n" +
                "VALUES (?, ?, ?, ?)";

        int songIDParameter = 1;
        int contentParameter = 2;
        int songPartParameter = 3;
        int linePositionParameter = 4;

        int linePosition = 0;

        for (SongLyrics songPart : song.getLyrics().getComponents()) {

            for (SongLyrics line : songPart.getComponents()) {
                linePosition++;
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(addLineQuery);

                    preparedStatement.setInt(songIDParameter, song.getID());
                    preparedStatement.setString(contentParameter, line.toString());
                    preparedStatement.setString(songPartParameter, songPart.getType().toString().toLowerCase());
                    preparedStatement.setInt(linePositionParameter, linePosition);

                    preparedStatement.execute();

                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
