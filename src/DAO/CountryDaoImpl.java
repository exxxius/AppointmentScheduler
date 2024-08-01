package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates the Country database methods.
 *
 * @author Mehdi Rahimi
 */
public class CountryDaoImpl {

    /**
     * Returns an ObservableList containing all countries from the database.
     *
     * @return the ObservableList containing all countries
     * @throws SQLException if a database access error occurs
     * @throws Exception    for any other errors
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        // Get a connection from the database connection pool
        Connection connection = JDBC.getConnection();
        try {
            // Prepare the SQL statement
            String sqlStatement = "SELECT * FROM countries";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            // Execute the SQL statement and get the result set
            ResultSet result = statement.executeQuery();
            // Process each row in the result set
            while (result.next()) {
                int countryID = result.getInt("Country_ID");
                String countryName = result.getString("Country");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                Country country = new Country(countryID, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allCountries.add(country);
            }
        } finally {
            // Close the connection
            if (connection != null) {
                connection.close();
            }
        }
        return allCountries;
    }

    /**
     * This method etrieves a Country object from the database with the specified Country ID.
     *
     * @param countryId The ID of the Country to retrieve.
     * @return The Country object if it exists, null otherwise.
     * @throws SQLException if a database access error occurs
     * @throws Exception    for any other errors
     */
    public static Country getCountryFromCountryID(int countryId) throws SQLException, Exception {
        String sqlStatement = "SELECT * FROM countries WHERE Country_ID = ?";
        try {
            Connection conn = JDBC.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, countryId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("Country_ID");
                String country = result.getString("Country");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                return new Country(id, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error retrieving country with ID " + countryId + ": " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("Error retrieving country with ID " + countryId + ": " + ex.getMessage());
            throw ex;
        }
    }
}

