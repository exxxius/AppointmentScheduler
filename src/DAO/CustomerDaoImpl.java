package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates the Customer database methods.
 *
 * @author Mehdi Rahimi
 */
public class CustomerDaoImpl {

    /**
     * This function returns an ObservableList of all customers in the database. This is used to populate the customer table.
     *
     * @return A list of all customers in the database.
     * @throws SQLException If there is an error with the SQL statement.
     * @throws Exception    If there is an error with the connection.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "select * from Customers";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement);
             ResultSet result = stmt.executeQuery()) {
            while (result.next()) {
                int customerId = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int Division_ID = result.getInt("Division_ID");
                Customer customerResult = new Customer(customerId, customerName, address, postalCode, phone, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, Division_ID);
                allCustomers.add(customerResult);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all customers: " + e.getMessage());
            throw e;
        }
        return allCustomers;
    }

    /**
     * This method adds a new customer to the database.
     *
     * @param customerName    the name of the customer
     * @param customerAddress the address of the customer
     * @param postalCode      the postal code of the customer
     * @param customerPhone   the phone number of the customer
     * @param createDate      the create date of the customer
     * @param createdBy       the name of the user who created the customer
     * @param lastUpdate      the last update date of the customer
     * @param lastUpdateBy    the name of the user who last updated the customer
     * @param divisionId      the ID of the division that the customer belongs to
     */
    public static void addCustomer(String customerName, String customerAddress, String postalCode, String customerPhone,
                                   Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int divisionId) {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By," +
                             " Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, customerName);
            pstmt.setString(2, customerAddress);
            pstmt.setString(3, postalCode);
            pstmt.setString(4, customerPhone);
            pstmt.setTimestamp(5, createDate);
            pstmt.setString(6, createdBy);
            pstmt.setTimestamp(7, lastUpdate);
            pstmt.setString(8, lastUpdateBy);
            pstmt.setInt(9, divisionId);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method modifies a customer record in the database with the given data.
     *
     * @param customerId      the ID of the customer to modify
     * @param customerName    the new name for the customer
     * @param customerAddress the new address for the customer
     * @param postalCode      the new postal code for the customer
     * @param customerPhone   the new phone number for the customer
     * @param lastUpdate      the new last update timestamp for the customer
     * @param lastUpdateBy    the user who last updated the record
     * @param divisionId      the division ID for the customer's location
     * @throws SQLException if there is an error executing the SQL statement
     */
    public static void modifyCustomer(int customerId, String customerName, String customerAddress, String postalCode,
                                      String customerPhone, Timestamp lastUpdate, String lastUpdateBy, int divisionId) throws SQLException {
        String sqlcm = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlcm)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, customerAddress);
            pstmt.setString(3, postalCode);
            pstmt.setString(4, customerPhone);
            pstmt.setTimestamp(5, lastUpdate);
            pstmt.setString(6, lastUpdateBy);
            pstmt.setInt(7, divisionId);
            pstmt.setInt(8, customerId);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method takes in a customer ID as an argument and deletes the customer from the database.
     *
     * @param customerID The ID of the customer to be deleted.
     */
    public static void deleteCustomerById(int customerID) {
        String sqlcd = "DELETE FROM customers WHERE Customer_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sqlcd)) {
            pstm.setInt(1, customerID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This fuction deletes the customer object from the database.
     *
     * @param customer The customer object to be deleted.
     * @throws SQLException if there is an error executing the SQL statement.
     */
    public static void deleteCustomerById(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves a customer with the given customer ID from the database.
     *
     * @param customerID the ID of the customer to retrieve
     * @return the customer object if found, null otherwise
     * @throws SQLException if there is an error executing the SQL statement
     */
    public static Customer getCustomerByCustomerId(int customerID) throws SQLException {
        String sqlStatement = "SELECT * FROM customers WHERE Customer_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            stmt.setInt(1, customerID);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int customerId = result.getInt("Customer_ID");
                String Customer_Name = result.getString("Customer_Name");
                String Address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String Phone = result.getString("Phone");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int divisionId = result.getInt("Division_ID");

                return new Customer(customerId, Customer_Name, Address, postalCode, Phone, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, divisionId);
            }
        }
        return null;
    }

    /**
     * This method returns the number of customers in a given country.
     *
     * @param countryId The ID of the country to count customers for.
     * @return The number of customers in a country.
     * @throws SQLException if there is an error executing the SQL statement.
     */
    public static int countCustomers(int countryId) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) AS customerCountry FROM customers WHERE Division_ID IN (SELECT Division_ID FROM First_Level_Divisions WHERE Country_ID = ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("customerCountry");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return count;
    }

}
