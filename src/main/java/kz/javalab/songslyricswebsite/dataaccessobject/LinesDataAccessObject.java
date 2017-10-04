package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;
import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.SongAddingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains methods for receiving, inserting and updating data of "Lines" table.
 */
public class LinesDataAccessObject extends AbstractDataAccessObject {

    /**
     * Constructs <Code>LinesDataAccessObject</Code> instance.
     */
    public LinesDataAccessObject() {
        super();
    }

    /**
     * Updates content of the line with specific ID.
     * @param lineID ID of the line which is to be modified.
     * @param newLine New line.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void updateLine(int lineID, Line newLine, Connection connection) throws SQLException {
        String updateLineQuery = "UPDATE websitedatabase.lines\n" +
                "SET content = ?\n" +
                "WHERE line_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateLineQuery);

        updateStringValueByEntityID(preparedStatement, lineID, newLine.toString());

    }

    /**
     * Updates type of lyrics part of the line with specific ID.
     * @param lineID ID of the line which is to be modified.
     * @param songLyricsPartType New value of type of lyrics part.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void updateLyricsPart(int lineID, SongLyricsPartType songLyricsPartType, Connection connection) throws SQLException {
        String updateLyricsPartQuery = "UPDATE websitedatabase.lines\n" +
                "SET song_part = ?\n" +
                "WHERE line_id = ?";
        int songPartParameter = 1;
        int lineIDParameter = 2;

        PreparedStatement preparedStatement = connection.prepareStatement(updateLyricsPartQuery);

        preparedStatement.setString(songPartParameter, songLyricsPartType.toString().toLowerCase());
        preparedStatement.setInt(lineIDParameter, lineID);

        preparedStatement.execute();

        preparedStatement.close();

    }

    /**
     * Inserts new line to database.
     * @param linePosition Position of the line in the song.
     * @param songID ID of the song.
     * @param line <Code>Line</Code> object to be inserted.
     * @param songLyricsPartType Type of lyrics part which contains this <Code>line</Code>.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to insert data into database.
     */
    public void addLineToDatabase(int linePosition, int songID, Line line, SongLyricsPartType songLyricsPartType, Connection connection) throws SQLException {
        String addLineQuery = "INSERT INTO websitedatabase.lines\n" +
                "(content, song_id, song_part, line_position)\n" +
                "VALUES (?, ?, ?, ?)";
        int lineContentParameter = 1;
        int songIDParameter = 2;
        int songPartParameter = 3;
        int linePositionParameter = 4;

        PreparedStatement preparedStatement = connection.prepareStatement(addLineQuery);

        preparedStatement.setString(lineContentParameter, line.toString());
        preparedStatement.setInt(songIDParameter, songID);
        preparedStatement.setString(songPartParameter, songLyricsPartType.toString());
        preparedStatement.setInt(linePositionParameter, linePosition);

        preparedStatement.execute();

        preparedStatement.close();

    }

    /**
     * Marks line with the specific ID as deleted.
     * @param lineID ID of the line.
     * @param connection Connection to be used.
     * @throws SQLException Thrown if some error occurred when attempted to update data.
     */
    public void markLineAsDeleted(int lineID, Connection connection) throws SQLException {
        String markLineAsDeletedQuery = "UPDATE websitedatabase.lines\n" +
                "SET is_deleted = ?\n" +
                "WHERE line_id = ?";

        int isDeletedValue = 1;

        PreparedStatement preparedStatement = connection.prepareStatement(markLineAsDeletedQuery);

        executePreparedStatementWithMultipleIntegerValues(preparedStatement, isDeletedValue, lineID);

    }

    /**
     * Retrieves ID of the specific line.
     * @param linePosition Position of the line in the song.
     * @param songID ID of the song which contains specified line.
     * @param connection Connection to be used.
     * @return ID of the specific line.
     */
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

    /**
     * Inserts lyrics of the song into the database.
     * @param song Songs lyrics of which are to be inserted into the database.
     * @param connection Connection to be used.
     * @throws SQLException Thrown of some error occurred when attempted to insert data into database.
     */
    public void addSongLyricsToDatabase(Song song, Connection connection) throws SQLException {
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

                PreparedStatement preparedStatement = connection.prepareStatement(addLineQuery);

                preparedStatement.setInt(songIDParameter, song.getID());
                preparedStatement.setString(contentParameter, line.toString());
                preparedStatement.setString(songPartParameter, songPart.getType().toString().toLowerCase());
                preparedStatement.setInt(linePositionParameter, linePosition);

                preparedStatement.execute();

                preparedStatement.close();
            }
        }

    }

    /**
     * Retrieves song lyrics from database using song ID.
     * @param songID ID of the song.
     * @param connection Connection to be used.
     * @return Lyrics of the song.
     */
    public SongLyrics getSongLyricsBySongID(int songID, Connection connection) {
        SongLyrics songLyrics = new SongLyricsComposite();

        String songLyricsQuery = "SELECT song_part, content\n" +
                "FROM websitedatabase.lines\n" +
                "WHERE song_id = ?\n AND is_deleted = ?\n" +
                "ORDER BY line_position;";

        int songIDParameter = 1;
        int isDeletedParameter = 2;

        int isDeletedValue = 0;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(songLyricsQuery);
            preparedStatement.setInt(songIDParameter, songID);
            preparedStatement.setInt(isDeletedParameter, isDeletedValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<List<String>> listOfLyricsPartsAsStringValues = new ArrayList<>();

            while (resultSet.next()) {
                listOfLyricsPartsAsStringValues.add(Arrays.asList(resultSet.getString(DatabaseConstants.ColumnLabels.LinesTable.SONG_PART),
                        resultSet.getString(DatabaseConstants.ColumnLabels.LinesTable.CONTENT)));
            }

            songLyrics = buildLyricsFromStringValues(listOfLyricsPartsAsStringValues);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songLyrics;
    }

    /**
     * Builds lyrics of the song.
     * @param listOfLyricsPartsAsStringValues List which contains lines of the lyrics.
     *                                        This list has a map-like structure. Each entry has two values:
     *                                        1. The first value is a part of the song which contains a specific line.
     *                                        2. The second value is a content of the line.
     *                                        For example:
     *                                        "chorus", "I've become so numb" — means that the entry contains the line
     *                                        "I've become so numb" which belongs to the chorus.
     * @return Lyrics of the song.
     */
    private SongLyrics buildLyricsFromStringValues(List<List<String>> listOfLyricsPartsAsStringValues) {
        SongLyrics songLyrics = new SongLyricsComposite();

        int lyricsPartType = 0;
        int lineContent = 1;
        SongLyricsComposite lyricsPart = null;

        for (int index = 0; index < listOfLyricsPartsAsStringValues.size(); index++) {

            List<String> entry = listOfLyricsPartsAsStringValues.get(index);

            String partType = entry.get(lyricsPartType);

            String content = entry.get(lineContent);

            switch (partType) {
                case "verse":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.VERSE);
                    }
                    break;
                case "chorus":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.CHORUS);
                    }
                    break;
                case "hook":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.HOOK);
                    }
                    break;
                case "bridge":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.BRIDGE);
                    }
                    break;
                case "intro":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.INTRO);
                    }
                    break;
                case "outro":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.OUTRO);
                    }
                    break;
                case "other":
                    if (lyricsPart == null) {
                        lyricsPart = new SongLyricsComposite();
                        lyricsPart.setType(SongLyricsPartType.OTHER);
                        break;
                    }
                default:
                    break;
            }

            lyricsPart.add(new Line(content));

            int nextIndex = index + 1;

            String nextType = null;

            if (nextIndex != listOfLyricsPartsAsStringValues.size()) {
                nextType = listOfLyricsPartsAsStringValues.get(nextIndex).get(lyricsPartType);
            }

            if (!partType.equals(nextType)) {
                songLyrics.add(lyricsPart);
                lyricsPart = null;
            }
        }

        return songLyrics;
    }
}
