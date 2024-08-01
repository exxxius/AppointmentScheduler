package DAO;

import Model.Appointment;
import Model.AppointmentType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class creates the Appointment database methods.
 *
 * @author Mehdi Rahimi
 */
public class AppointmentDaoImpl {

    /**
     * This function takes in a user ID and returns a list of appointments associated with that user ID
     *
     * @param userID The ID of the user whose appointments you want to retrieve.
     * @return A list of appointments for a specific user.
     * @throws SQLException if a database access error occurs.
     */
    public static ObservableList<Appointment> getAppointmentByUserId(int userID) throws SQLException {
        ObservableList<Appointment> userIDAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Appointments WHERE User_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)) {
            stmt.setInt(1, userID);
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    int appointmentID = result.getInt("Appointment_ID");
                    String title = result.getString("Title");
                    String description = result.getString("Description");
                    String location = result.getString("Location");
                    String type = result.getString("Type");
                    LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                    LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                    String createdBy = result.getString("Created_By");
                    LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                    String lastUpdatedBy = result.getString("Last_Updated_By");
                    int customerID = result.getInt("Customer_ID");
                    int contactID = result.getInt("Contact_ID");

                    Appointment appointment = new Appointment(appointmentID, title, description, location, type, start,
                            end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
                    userIDAppointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving appointments by user ID: " + e.getMessage());
            throw e;
        }
        return userIDAppointments;
    }

    /**
     * This method takes a customer ID as a parameter and returns an ObservableList of Appointments that are associated
     * with that customer ID
     *
     * @param customerId The customer ID of the customer you want to get the appointments for.
     * @return A list of appointments for a specific customer.
     * @throws SQLException if a database access error occurs.
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerId(int customerId) throws SQLException {
        ObservableList<Appointment> appointmentsByCustomerId = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Appointments WHERE Customer_ID = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int appointmentId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerIdResult = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type,
                        start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerIdResult, userId, contactId);
                appointmentsByCustomerId.add(appointmentResult);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return appointmentsByCustomerId;
    }

    /**
     * This function returns an ObservableList of all Appointments in the database.
     * It is used to populate the Appointments table view in the Appointments tab.
     *
     * @return A list of all appointments in the database.
     * @throws SQLException if a database access error occurs.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Appointments";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
             ResultSet result = preparedStatement.executeQuery()) {
            while (result.next()) {
                int appointmentId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, description, location, type,
                        start, end, createDate, createdBy, lastUpdate,
                        lastUpdatedBy, customerId, userId, contactId);
                allAppointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return allAppointments;
    }


    /**
     * Adds a new appointment to the database with the specified parameters.
     *
     * @param appointmentTitle       the title for the new appointment
     * @param appointmentDescription the description for the new appointment
     * @param appointmentLocation    the location for the new appointment
     * @param appointmentType        the type for the new appointment
     * @param appointmentStart       the start time for the new appointment
     * @param appointmentEnd         the end time for the new appointment
     * @param createDate             the timestamp of when the appointment was created
     * @param createdBy              the username of the user who created the appointment
     * @param lastUpdate             the timestamp of when the appointment was last updated
     * @param lastUpdateBy           the username of the user who last updated the appointment
     * @param customerID             the ID of the customer associated with the appointment
     * @param userID                 the ID of the user who created the appointment
     * @param contactID              the ID of the contact associated with the appointment
     * @throws SQLException if an error occurs while executing the SQL statement
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation,
                                      String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd,
                                      Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy,
                                      int customerID, int userID, int contactID) throws SQLException {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, " +
                             "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setString(1, appointmentTitle);
            pst.setString(2, appointmentDescription);
            pst.setString(3, appointmentLocation);
            pst.setString(4, appointmentType);
            pst.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            pst.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            pst.setTimestamp(7, createDate);
            pst.setString(8, createdBy);
            pst.setTimestamp(9, lastUpdate);
            pst.setString(10, lastUpdateBy);
            pst.setInt(11, customerID);
            pst.setInt(12, userID);
            pst.setInt(13, contactID);

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding appointment: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing appointment in the database with the specified parameters.
     *
     * @param appointmentID          the ID of the appointment to be updated
     * @param appointmentTitle       the updated title for the appointment
     * @param appointmentDescription the updated description for the appointment
     * @param appointmentLocation    the updated location for the appointment
     * @param appointmentType        the updated type for the appointment
     * @param appointmentStart       the updated start time for the appointment
     * @param appointmentEnd         the updated end time for the appointment
     * @param lastUpdate             the timestamp of when the appointment was last updated
     * @param lastUpdateBy           the username of the user who last updated the appointment
     * @param customerID             the ID of the customer associated with the appointment
     * @param userID                 the ID of the user who created the appointment
     * @param contactID              the ID of the contact associated with the appointment
     * @throws SQLException if an error occurs while executing the SQL statement
     */
    public static void modifyAppointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                                         String appointmentLocation, String appointmentType, LocalDateTime appointmentStart,
                                         LocalDateTime appointmentEnd, Timestamp lastUpdate, String lastUpdateBy,
                                         int customerID, int userID, int contactID) throws SQLException {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            Connection conn = JDBC.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, appointmentTitle);
            pst.setString(2, appointmentDescription);
            pst.setString(3, appointmentLocation);
            pst.setString(4, appointmentType);
            pst.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            pst.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            pst.setTimestamp(7, lastUpdate);
            pst.setString(8, lastUpdateBy);
            pst.setInt(9, customerID);
            pst.setInt(10, userID);
            pst.setInt(11, contactID);
            pst.setInt(12, appointmentID);

            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("Error updating appointment: " + e.getMessage(), e);
        }
    }

    /**
     * This method deletes an appointment from the database based on the appointment ID.
     *
     * @param appointmentID The appointment ID of the appointment you want to delete.
     */
    public static void deleteAppointmentByApptId(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            Connection conn = JDBC.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, appointmentID);

            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the method to delete an appointment. The method executes an SQL query to delete an existing Appointment
     * in the Appointments table of the database with the selected Appointment ID.
     *
     * @param appointment the appointment to delete.
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlStatement)) {
            ps.setInt(1, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This is the method to delete an appointment when a customer is deleted. The method executes an SQL query to delete
     * an existing Appointment in the Appointments table of the database with the selected Customer ID.
     *
     * @param customerID    the ID of the customer being deleted
     * @param appointmentID the ID of the appointment to delete
     */
    public static void deleteApptCustID(int customerID, int appointmentID) {
        try (Connection conn = JDBC.getConnection()) {
            String sqlcd = "DELETE FROM appointments WHERE Customer_ID = ? AND Appointment_ID = ?";
            PreparedStatement psti = conn.prepareStatement(sqlcd);

            psti.setInt(1, customerID);
            psti.setInt(2, appointmentID);

            psti.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This is the method to create the ObservableList typeAppt with only the distinct appointment types. The method
     * executes an SQL query to select only the distinct Type strings from the Appointments table.
     *
     * @return the ObservableList typeAppt with only the distinct appointment types
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static ObservableList<AppointmentType> typeAppt() throws SQLException {
        ObservableList<AppointmentType> typeAppt = FXCollections.observableArrayList();
        String sqlStatement = "select distinct Type from appointments";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String type = rs.getString("Type");
                AppointmentType appointmentTypeResult = new AppointmentType(type);
                typeAppt.add(appointmentTypeResult);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return typeAppt;
    }

    /**
     * This method takes in a selected appointment type and a month and returns the number of appointments of that type
     * in that month
     *
     * @param selectedType The type of appointment that is selected from the combo box.
     * @param month        The month to be counted.
     * @return The number of appointments of a certain type in a certain month.
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static int countMonthType(AppointmentType selectedType, String month) throws SQLException {
        String sqlStatement = "SELECT COUNT(*) AS monthType FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ?";
        int countMonthTypeResult = 0;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlStatement)) {
            pstmt.setString(1, selectedType.getType());
            pstmt.setString(2, month);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                countMonthTypeResult = result.getInt("monthType");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countMonthTypeResult;
    }


    /**
     * This method takes in a contact ID and returns a list of appointments associated with that contact ID
     *
     * @param selectedContactID The ID of the contact that was selected in the table view.
     * @return A list of appointments that are associated with the selected contact.
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static ObservableList<Appointment> getAppointmentsByContactId(int selectedContactID) throws SQLException {
        ObservableList<Appointment> contactList = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);) {

            preparedStatement.setInt(1, selectedContactID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int appointmentId = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type, start,
                        end, createDate, createdBy, lastUpdate, lastUpdateBy, customerId, userId, contactId);
                contactList.add(appointmentResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return contactList;
    }

    /**
     * This method returns an ObservableList of Appointment objects that are scheduled for the current month.
     *
     * @return A list of all appointments that are scheduled for the current month.
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static ObservableList<Appointment> getCurrentMonthAppointments() throws SQLException {
        ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE())";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement);
             ResultSet result = stmt.executeQuery()) {
            while (result.next()) {
                int Appointment_ID = result.getInt("Appointment_ID");
                String Title = result.getString("Title");
                String Description = result.getString("Description");
                String Location = result.getString("Location");
                String Type = result.getString("Type");
                LocalDateTime Start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime Create_Date = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int Customer_ID = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointmentResult = new Appointment(Appointment_ID, Title, Description, Location, Type,
                        Start, End, Create_Date, createdBy, lastUpdate, lastUpdatedBy,
                        Customer_ID, userId, contactId);
                currentMonthAppointments.add(appointmentResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentMonthAppointments;
    }


    /**
     * This function returns an ObservableList of Appointment objects that are scheduled for the current week
     *
     * @return A list of all appointments that are scheduled for the current week.
     * @throws SQLException if an error occurs while executing the SQL statement.
     * @throws NullPointerException if the result set is empty.
     */
    public static ObservableList<Appointment> getCurrentWeekAppointments() throws NullPointerException, SQLException {
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURDATE())";
        Connection conn = JDBC.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(sqlStatement)) {
            while (result.next()) {
                int Appointment_ID = result.getInt("Appointment_ID");
                String Title = result.getString("Title");
                String Description = result.getString("Description");
                String Location = result.getString("Location");
                String Type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                Timestamp createDate = result.getTimestamp("Create_Date");
                LocalDateTime Create_Date = null;
                if (createDate != null) {
                    Create_Date = createDate.toLocalDateTime();
                }
                String Created_By = result.getString("Created_By");
                Timestamp lastUpdate = result.getTimestamp("Last_Update");
                LocalDateTime Last_Update = null;
                if (lastUpdate != null) {
                    Last_Update = lastUpdate.toLocalDateTime();
                }
                String lastUpdatedBy = result.getString("Last_Updated_By");
                int customerId = result.getInt("Customer_ID");
                int userId = result.getInt("User_ID");
                int contactId = result.getInt("Contact_ID");

                Appointment appointmentResult = new Appointment(Appointment_ID, Title, Description, Location, Type,
                        start, end, Create_Date, Created_By, Last_Update, lastUpdatedBy,
                        customerId, userId, contactId);
                currentWeekAppointments.add(appointmentResult);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return currentWeekAppointments;
    }

    /**
     * This function counts the number of appointments a customer has.
     *
     * @param customerID The customer ID of the customer to count the appointments for.
     * @return The number of appointments for a given customer.
     * @throws SQLException if an error occurs while executing the SQL statement.
     */
    public static int countCustAppt(int customerID) {
        String sqlStatement = "SELECT COUNT(*) AS custAppt FROM appointments WHERE Customer_ID  = '" + customerID + "'";
        int countCustApptResult = 0;
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlStatement);
             ResultSet result = ps.executeQuery()) {
            if (result.next()) {
                countCustApptResult = result.getInt("custAppt");
            }
        } catch (SQLException e) {
            System.out.println("Error counting customer appointments: " + e.getMessage());
        }
        return countCustApptResult;
    }
}
