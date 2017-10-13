package kz.javalab.songslyricswebsite.resource;

import java.util.ResourceBundle;

/**
 * This class is responsible for retrieving data from database.properties file.
 */
public class DatabaseConfiguration {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    private DatabaseConfiguration() {
    }

    /**
     * Returns database URL.
     * @return Database URL.
     */
    public static String getDatabaseURL() {
        return resourceBundle.getString(Properties.DATABASE_URL);
    }

    /**
     * Returns maximum size of connection pool.
     * @return Maximum size of connection pool.
     */
    public static int getMaxSize() {
        return Integer.valueOf(resourceBundle.getString(Properties.MAX_SIZE));
    }

    /**
     * Returns username of database account.
     * @return Username of database account.
     */
    public static String getUsername() {
        return resourceBundle.getString(Properties.USERNAME);
    }

    /**
     * Returns password of database account.
     * @return Password of database account.
     */
    public static String getPassword() {
        return resourceBundle.getString(Properties.PASSWORD);
    }

    /**
     * Returns name of the driver used to connect to database.
     * @return Name of the driver used to connect to database.
     */
    public static String getDriverName() {
        return resourceBundle.getString(Properties.DRIVER_NAME);
    }

    private class Properties {
        private static final String DATABASE_URL = "databaseurl";
        private static final String MAX_SIZE = "maxsize";
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";
        private static final String DRIVER_NAME = "drivername";
    }
}
