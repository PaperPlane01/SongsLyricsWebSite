package kz.javalab.songslyricswebsite.conntectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class is responsible for storing <Code>Connection</Code> objects.
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
    private BlockingQueue<Connection> connections;
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
     * Creates initialized <code>ConnectionPool</code> instance.
     */
    private ConnectionPool() {
        this.databaseURL = "jdbc:mysql://localhost:3306/websitedatabase";
        this.maxSize = 10;
        this.userName = "root";
        this.password = "admin";
        this.driverName = "com.mysql.jdbc.Driver";
        this.connections = new ArrayBlockingQueue<>(maxSize);

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
        }

        return connection;
    }

    /**
     * Retrieves connection from connection pool.
     * @return Connection from connection pool.
     */
    public Connection getConnection() {
        return connections.poll();
    }


    /**
     * Puts connection back into connection pool.
     * @param connection Connection to be returned.
     */
    public void returnConnection(Connection connection) {

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connections.add(connection);
    }

    /**
     * Closes all connections of the connection pool.
     */
    public void closeConnections() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
