package kz.javalab.songslyricswebsite.conntectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by PaperPlane on 26.07.2017.
 */
public class ConnectionPool {

    /**
     * Instance of <code>ConnectionPool</code>.
     */
    private static volatile ConnectionPool instance;
    /**
     * The number of connection contained in <code>ConnectionPool</code>.
     */
    private int maxSize;
    /**
     * Connections stored.
     */
    private ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<>();
    /**
     * URL of the database.
     */
    private String databaseURL;
    /**
     * Username used to connect to the database.
     */
    private String userName;
    /**
     * Password used to connect to the database.
     */
    private String password;
    /**
     * Name of the driver used to connect to the database.
     */
    private String driverName;

    /**
     * Initializes connection pool.
     */
    public static void initConnectionPool() {
        if (instance == null) {
            instance = getInstance();
        }
    }

    /**
     * Returns instance of <code>ConnectionPool</code>.
     * @return Instance of <code>ConnectionPool</code>.
     */
    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }

        return localInstance;
    }

    /**
     * Creates initialized <code>ConnectionPool</code>.
     */
    private ConnectionPool() {
        this.databaseURL = "jdbc:mysql://localhost:3306/websitedatabase";
        this.maxSize = 10;
        this.userName = "root";
        this.password = "admin";
        this.driverName = "com.mysql.jdbc.Driver";

        while (!isConnectionPoolFull()) {
            connections.add(createNewConnection());
        }
    }


    /**
     * Checks if <code>ConnectionPool</code> is full.
     * @return <code>True</code> if <code>ConnectionPool</code> is full, <code>False</code> if not.
     */
    private synchronized boolean isConnectionPoolFull() {
        if (connections.size() >= maxSize) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates new connection.
     * @return New connection.
     */
    private Connection createNewConnection() {
        Connection connection = null;

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(databaseURL, userName, password);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return connection;
    }

    /**
     * Allows to retrieve connection from connection pool.
     * @return Connection from connection pool.
     */
    public Connection getConnection() {

        Connection connection = null;

        if (connections.size() > 0) {
            connection = connections.poll();
        }

        return connection;
    }


    /**
     * Allows to put connection back into connection pool.
     * @param connection Connection to be returned.
     */
    public void returnConnection(Connection connection) {
        connections.add(connection);
    }

    public void closeConnections() {
        connections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
