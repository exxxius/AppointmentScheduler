package DAO;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates the FirstLevelDivision database methods.
 *
 * @author Mehdi Rahimi
 */
public class FirstLevelDivisionDaoImpl {
    /**
     * This method gets the FirstLevelDivision from the getValue of the divisionCombo combobox in
     * CustomersModify.
     *
     * @param firstLevelDivision the FirstLevelDivision to get the FirstLevelDivision for
     * @return the FirstLevelDivision divisionResult
     * @throws SQLException if an error occurs while retrieving data from the database.
     */
    public static FirstLevelDivision getDivision(FirstLevelDivision firstLevelDivision) throws SQLException {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division=?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pst = conn.prepareStatement(sqlStatement)) {

            pst.setString(1, firstLevelDivision.getDivision());
            ResultSet result = pst.executeQuery();

            if (result.next()) {
                int divisionId = result.getInt("Division_ID");
                String divisionName = result.getString("Division");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int countryId = result.getInt("COUNTRY_ID");

                return new FirstLevelDivision(divisionId, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Returns an ObservableList of FirstLevelDivision objects for a specified country ID
     *
     * @param countryId The country ID to filter the FirstLevelDivision objects by
     * @return ObservableList of FirstLevelDivision objects
     * @throws SQLException if an error occurs while retrieving data from the database
     */
    public static ObservableList<FirstLevelDivision> getDiv(int countryId) throws SQLException {
        ObservableList<FirstLevelDivision> div = FXCollections.observableArrayList();
        String sqlStatement = "select * from first_level_divisions where COUNTRY_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            stmt.setInt(1, countryId);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int divisionId = result.getInt("Division_ID");
                String Division = result.getString("Division");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int country_ID = result.getInt("COUNTRY_ID");

                FirstLevelDivision divResult = new FirstLevelDivision(divisionId, Division, createDate, createdBy, lastUpdate, lastUpdatedBy, country_ID);
                div.add(divResult);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting divisions from database: " + ex.getMessage());
        }
        return div;
    }

    /**
     * This method retrieves a FirstLevelDivision based on division ID.
     *
     * @param divisionId The ID of the division to retrieve.
     * @return A FirstLevelDivision object representing the retrieved division.
     * @throws SQLException If an error occurs while executing the SQL statement.
     */
    public static FirstLevelDivision getFirstLvlDivByDivID(int divisionId) throws SQLException {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE DIVISION_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            stmt.setInt(1, divisionId);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int division_ID = result.getInt("Division_ID");
                String Division = result.getString("Division");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int countryId = result.getInt("COUNTRY_ID");

                return new FirstLevelDivision(division_ID, Division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting division from division ID: " + ex.getMessage());
            throw ex;
        }
        return null;
    }

}