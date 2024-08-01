package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates the User database methods.
 *
 * @author Mehdi Rahimi
 */
public class UserDaoImpl {
    /**
     * Validates a user's login credentials by checking if the provided username and password match
     * with any existing user's credentials in the database.
     *
     * @param userName The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password match with an existing user's credentials, false otherwise.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static boolean validateLogIn(String userName, String password) throws SQLException {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE User_Name = ? AND Password = ?")) {
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * It takes a user name as a parameter, and returns true if the user name is already in the database, and false if it
     * is not
     *
     * @param userName The user name to be validated.
     * @return A boolean value.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static boolean validateUserName(String userName) throws SQLException {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE User_Name = ?")) {
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * "If the password is in the database, return true, otherwise return false."
     * <p>
     * The function takes a String as a parameter, and returns a boolean.
     *
     * @param password The password to validate.
     * @return A boolean value.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static boolean validatePassword(String password) throws SQLException {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE Password = ?")) {
            ps.setString(1, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the user with the specified user name from the database.
     *
     * @param userName The user name of the user to retrieve.
     * @return The User object corresponding to the user name, or null if the user is not found.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static User getUser(String userName) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM users WHERE User_Name = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement)) {
            ps.setString(1, userName);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int userid = result.getInt("User_ID");
                String userNameG = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateby = result.getString("Last_Updated_By");

                return new User(userid, userNameG, password, createDate, createdBy, lastUpdate, lastUpdateby);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function returns an ObservableList of User objects that are created from the results of the SQL query.
     *
     * @return A list of all users in the database.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Users";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement);
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                int userid = result.getInt("User_ID");
                String userNameG = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateby = result.getString("Last_Updated_By");
                User userResult = new User(userid, userNameG, password, createDate, createdBy, lastUpdate, lastUpdateby);
                allUsers.add(userResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    /**
     * This function takes in a userID and returns a User object if found in the database.
     *
     * @param userID The userID of the user you want to get.
     * @return A User object.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static User getUserFromUserID(int userID) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement)) {
            ps.setInt(1, userID);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int User_ID = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                return new User(User_ID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function takes in a userName and returns a User_ID if found in the database.
     *
     * @param userName The userName of the user you want to get.
     * @return A User_ID.
     * @throws SQLException If an error occurs while querying the database.
     */
    public static int getUserIDFromUserName(String userName) throws SQLException {
        int userId = 0;
        String sqlStatement = "SELECT User_ID FROM users WHERE User_Name = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement)) {
            ps.setString(1, userName);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                userId = result.getInt("User_ID");
                int getUserIDFromUserNameResult = userId;
                return getUserIDFromUserNameResult;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
