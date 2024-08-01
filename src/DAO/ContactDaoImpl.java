package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class creates the Contact database methods.
 *
 * @author Mehdi Rahimi
 */
public class ContactDaoImpl {

    /**
     * Returns an ObservableList containing all contacts from the database.
     *
     * @return the ObservableList containing all contacts
     * @throws SQLException if a database access error occurs
     * @throws Exception    for any other errors
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Contacts";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement);
             ResultSet result = stmt.executeQuery()) {
            while (result.next()) {
                int contactId = result.getInt("Contact_ID");
                String contactName = result.getString("Contact_Name");
                String email = result.getString("Email");
                Contact contact = new Contact(contactId, contactName, email);
                allContacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all contacts: " + e.getMessage());
            throw e;
        }
        return allContacts;
    }

    /**
     * Retrieves a contact with the given contact ID from the database.
     *
     * @param contactId the ID of the contact to retrieve
     * @return the contact object if found, null otherwise
     * @throws SQLException if there is an error executing the SQL statement
     */
    public static Contact getContactFromContactID(int contactId) throws SQLException {
        String sqlStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            stmt.setInt(1, contactId);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int Contact_ID = result.getInt("Contact_ID");
                String Contact_Name = result.getString("Contact_Name");
                String Email = result.getString("Email");
                return new Contact(Contact_ID, Contact_Name, Email);
            }
        }
        return null;
    }
}
