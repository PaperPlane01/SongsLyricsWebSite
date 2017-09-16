package kz.javalab.songslyricswebsite.dataaccessobject;

import kz.javalab.songslyricswebsite.constant.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class FeaturingsDataAccessObject extends AbstractDataAccessObject {

    public FeaturingsDataAccessObject() {
    }

    public boolean checkIfFeaturingExists(int artistID, int songID, Connection connection) {
        boolean result = false;

        String checkFeaturingQuery = "SELECT featuring_id\n" +
                "FROM featurings\n" +
                "WHERE artist_id = ?\n" +
                "AND song_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkFeaturingQuery);

            result = checkEntityExistence(preparedStatement, artistID, songID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getFeaturingID(int artistID, int songID, Connection connection) {
        String checkFeaturingQuery = "SELECT featuring_id\n" +
                "FROM featurings\n" +
                "WHERE artist_id = ?\n" +
                "AND song_id = ?";
        int artistIDParameter = 1;
        int songIDParameter = 2;

        int featuringID = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkFeaturingQuery);

            preparedStatement.setInt(artistIDParameter, artistID);
            preparedStatement.setInt(songIDParameter, songID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                featuringID = resultSet.getInt(DatabaseConstants.ColumnLabels.FeaturingsTable.FEATURING_ID);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  featuringID;
    }

    public void addNewFeaturing(int artistID, int songID, Connection connection) {
        String addFeaturingQuery = "INSERT INTO featurings\n" +
                "(artist_id, song_id)\n" +
                "VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addFeaturingQuery);

            executePreparedStatementWithMultipleIntegerValues(preparedStatement, artistID, songID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markFeatuirngAsDeleted(int featuringID, Connection connection) {
        String markFeaturingAsDeletedQuery = "UPDATE featurings\n" +
                "SET is_deleted = ?\n" +
                "WHERE featuring_id = ?";

        int isDeletedValue = 1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(markFeaturingAsDeletedQuery);

            executePreparedStatementWithMultipleIntegerValues(preparedStatement, isDeletedValue, featuringID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
