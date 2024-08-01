package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class creates a connection to the database using the JDBC driver, and returns the connection.
 */
public class JDBC {

    /**
     * The prepared statement that is used to execute SQL statements.
     */
    private static PreparedStatement preparedStatement;
    /**
     * It's a constant that is used to create the JDBC URL.
     */
    private static final String protocol = "jdbc";
    /**
     * It's a constant that is used to create the JDBC URL.
     */
    private static final String vendorName = ":mysql:";
    /**
     * It's a constant that is used to create the JDBC URL on the lab.
     */
    private static final String ipAddress = "//127.0.0.1:3306/client_schedule";

    /**
     * the location of the database.
     */
    private static final String location = "//localhost/";
    /**
     * The name of the database.
     */
    private static final String databaseName = "client_schedule";

    //private static final String jdbcURL = protocol + vendorName + ipAddress + "?connectionTimeZone=SERVER";
    /**
     * The JDBC URL. The connectionTimeZone parameter is used to set the timezone to UTC instead of the default server time zone.<br>
     * This is done to avoid the "Data truncation" error when inserting a date into the database. Also, since the dateTime<br>
     * conversion is done by the driver manager on the server side, less coding and processing power is needed to convert the date and time in the application.
     */
    private static final String jdbcUrl = protocol + vendorName + ipAddress + "?connectionTimeZone= UTC";
    //private static final String jdbcUrl = protocol + vendorName + location + databaseName + "?connectionTimeZone = UTC"; // Setting the timezone to UTC instead of the default server time zone.
    /**
     * The JDBC driver.
     */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    /**
     * The connection to the database.
     */

    public static Connection conn;// = null;
    /**
     * The username to connect to the database.
     */

    private static final String username = "sqlUser";
    /**
     * The password to connect to the database.
     */
    private static final String password = "Passw0rd!";


    /**
     * It creates a connection to the database using the JDBC driver, and returns the connection
     *
     * @return A connection to the database.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     * @throws SQLException           if an error occurs while connecting to the database..
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(jdbcUrl, username, password);
            }
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * It checks if the connection is null or closed, if it is, it creates a new connection and returns it. If it's not, it
     * returns the existing connection
     *
     * @return A connection to the database.
     */
    public static Connection getConnection() {

        try {
            Class.forName(MYSQLJDBCDriver);
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(jdbcUrl, username, password);
            } else {
                System.out.println("Connection already exists!");
                return conn;
            }
            System.out.println("Get Connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("GET CONNECTION successful!");
        return conn;
    }

    /**
     * Close the connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This function sets the prepared statement to the connection and the sql statement
     *
     * @param conn         The connection to the database.
     * @param sqlStatement The SQL statement to be executed.
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    /**
     * It returns the prepared statement
     *
     * @return The preparedStatement object.
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}