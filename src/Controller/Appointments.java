package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import Model.Appointment;
import Model.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.utils.*;

/**
 * This class creates the Appointments controller.
 *
 * @author Mehdi Rahimi
 */
public class Appointments implements Initializable {
    /**
     * The stage of the Appointments object.
     */
    Stage stage;
    /**
     * The scene of the Appointments object.
     */
    Parent scene;
    /**
     * The observable list of Appointment objects used in the Appointments object.
     */
    ObservableList<Model.Appointment> Appointments = FXCollections.observableArrayList();
    /**
     * The week radio button.
     */
    @FXML
    private RadioButton weekRadioButton;
    /**
     * The month radio button.
     */
    @FXML
    private RadioButton monthRadioButton;
    /**
     * The all appointments radio button.
     */
    @FXML
    private RadioButton allRadioButton;
    /**
     * The table view of appointments.
     */
    @FXML
    private TableView<Appointment> appointmentTable;
    /**
     * The column for the appointment ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    /**
     * The column for the appointment title.
     */
    @FXML
    private TableColumn<Appointment, String> title;
    /**
     * The column for the appointment description.
     */
    @FXML
    private TableColumn<Appointment, String> description;
    /**
     * The column for the appointment location.
     */
    @FXML
    private TableColumn<Appointment, String> location;
    /**
     * The column for the appointment contact.
     */
    @FXML
    private TableColumn<Appointment, Integer> contact;
    /**
     * The column for the appointment type.
     */
    @FXML
    private TableColumn<Appointment, String> type;
    /**
     * The column for the appointment start time.
     */
    @FXML
    private TableColumn<Appointment, String> start;
    /**
     * The column for the appointment end time.
     */
    @FXML
    private TableColumn<Appointment, String> end;
    /**
     * The column for the appointment customer ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerId;
    /**
     * The column for the appointment user ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> userId;
    /**
     * The column for the appointment contact name.
     */
    @FXML
    private TableColumn<Appointment, String> contactName = new TableColumn<>();
    /**
     * The observable list of Contact objects used in the Appointments object.
     */
    ObservableList<Contact> Contacts = FXCollections.observableArrayList();

    /**
     * Checks if a new appointment overlaps with an existing appointment for the same customer.
     *
     * @param customerID       The ID of the customer for whom the new appointment is being created.
     * @param appointmentID    The ID of the new appointment.
     * @param appointmentStart The start time of the new appointment.
     * @param appointmentEnd   The end time of the new appointment.
     * @return True if there is an overlap, false otherwise.
     * @throws Exception If there is an error retrieving the list of existing appointments.
     */
    public static boolean hasOverlap(int customerID, int appointmentID, LocalDateTime appointmentStart, LocalDateTime appointmentEnd) throws Exception {
        ObservableList<Appointment> allAppointments;
        try {
            allAppointments = AppointmentDaoImpl.getAllAppointments();
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error retrieving appointments", ex.getMessage());
            throw new Exception(ex);
        }
        allAppointments.sort(Comparator.comparing(Appointment::getStart)); // Sort by start time
        LocalDateTime checkApptStart;
        LocalDateTime checkApptEnd;

        for (Appointment a : allAppointments) {
            checkApptStart = a.getStart();
            checkApptEnd = a.getEnd();
            // Check if appointments overlap
            if (customerID == a.getCustomerId() && appointmentID != a.getAppointmentId() &&
                    ((appointmentStart.isBefore(checkApptEnd) && appointmentEnd.isAfter(checkApptStart)) ||
                            (appointmentStart.isEqual(checkApptStart) && appointmentEnd.isEqual(checkApptEnd)))) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method is called when the user clicks the "Add" button on the Appointments screen.
     * It loads the AppointmentsAdd screen and sets it as the active scene.
     *
     * @param event The event that triggered the method call.
     * @throws IOException If an error occurs while loading the AppointmentsAdd screen.
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        switchToScene(event, "/view/AppointmentsAdd.fxml", null);
    }

    /**
     * Deletes the selected appointment after displaying a confirmation dialog box.
     * <p>
     * If the user confirms the deletion, the appointment will be deleted from the database and the appointment table<br>
     * will be updated. If the user cancels the deletion, the confirmation dialog box will be closed and no action will be taken.
     * LAMBDA expressions execute if the user confirms the deletion, and closes the dialog if the user clicks on cancel button.
     *
     * @param event The ActionEvent that triggered this method.
     * @throws Exception If there is an error while accessing the database.
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws Exception {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No Appointment Selected", "Please select an appointment from the table.");
        } else {
            showConfirmationAlert("Confirmation", "Are you sure you want to cancel this appointment?", null,
                    () -> {
                        try {
                            int canceledApptID = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
                            String canceledApptType = appointmentTable.getSelectionModel().getSelectedItem().getType();
                            AppointmentDaoImpl.deleteAppointmentByApptId(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());
                            showAlert(Alert.AlertType.INFORMATION, "Warning", "Appointment Canceled", canceledApptType +
                                    " appointment ID # " + canceledApptID + " successfully canceled.");
                            Appointments = AppointmentDaoImpl.getAllAppointments();
                            appointmentTable.setItems(Appointments);
                            appointmentTable.refresh();
                        } catch (Exception e) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Error deleting appointment", "An error occurred while deleting the appointment.");
                            e.printStackTrace();
                        }
                    },
                    () -> {
                        // Do nothing and simply close the confirmation dialog.
                    });
        }
    }

    /**
     * This method handles the action event of the Modify button on the Appointments screen.
     * It checks whether an appointment is selected from the table and displays a warning message if no appointment
     * is selected. If an appointment is selected, it passes the selected appointment to the AppointmentsModify class
     * and switches the scene to the AppointmentsModify screen.
     *
     * @param event the action event triggered by the Modify button on the Appointments screen
     * @throws IOException if there is an error loading the AppointmentsModify.fxml file/
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No appointment selected", "");
        } else {
            AppointmentsModify.passedinSelectedAppointment(selectedAppointment);
            switchToScene(event, "/view/AppointmentsModify.fxml", null);
        }
    }

    /**
     * When the "All" radio button is selected, the "Week" and "Month" radio buttons are deselected, and the Appointments
     * list is cleared and repopulated with all appointments from the database.
     *
     * @param event The event that triggered the action.
     * @throws Exception If there is an error retrieving the list of appointments from the database.
     */
    @FXML
    void onActionAll(ActionEvent event) throws Exception {
        weekRadioButton.setSelected(false);
        monthRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(AppointmentDaoImpl.getAllAppointments());
    }


    /**
     * When the month radio button is selected, the other two radio buttons are deselected, the appointments list is
     * cleared, and the appointments list is populated with the appointments for the current month.
     *
     * @param event The event that triggered the action.
     * @throws Exception If there is an error retrieving the list of appointments from the database.
     */
    @FXML
    void onActionMonth(ActionEvent event) throws Exception {
        allRadioButton.setSelected(false);
        weekRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(AppointmentDaoImpl.getCurrentMonthAppointments());
    }


    /**
     * When the user clicks the "Week" radio button, the "All" and "Month" radio buttons are deselected, the Appointments
     * list is cleared, and the Appointments list is populated with the current week's appointments.
     *
     * @param event The event that triggered the action.
     * @throws Exception If there is an error retrieving the list of appointments from the database.
     */
    @FXML
    void onActionWeek(ActionEvent event) throws Exception {
        allRadioButton.setSelected(false);
        monthRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(AppointmentDaoImpl.getCurrentWeekAppointments());
    }


    /**
     * Initializes the appointment table view by setting the cell value factories and populating it with data.
     * <p>
     * This method sets the cell value factories for each column of the appointment table view, and populates the table with data
     * retrieved from the database.
     * LAMBDA expressions are used to create callbacks for the start and end date/time columns, which
     * formats the date/time to a user-friendly string. The contact name is retrieved using the contact ID associated with each
     * appointment, and is set as the value of the contact name column using a lambda expression.
     * <p>
     * The use of lambda expressions in this method provides a concise and efficient way to create callbacks for each cell value
     * factory, and makes the code more readable by reducing the need for boilerplate code.
     *
     * @param url            the location used to resolve relative paths for the root object or null if the location is not known
     * @param resourceBundle the resources used to localize the root object or null if the root object was not localized
     * @throws RuntimeException if there is an exception while getting the contact list from the database
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        start.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        end.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            Contacts = ContactDaoImpl.getAllContacts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // The code is setting the value of the contactName column to the contactName associated with the
        // appointment's contactId.
        contactName.setCellValueFactory(param -> {
            // Get the Contact name associated with the Appointment's Contact ID
            int contactId = param.getValue().getContactId();
            String contactName = "";
            for (Contact contact : Contacts) {
                if (contact.getContactId() == contactId) {
                    contactName = contact.getContactName();
                    break;
                }
            }
            return new SimpleStringProperty(contactName);
        });
        try {
            Appointments.addAll(AppointmentDaoImpl.getAllAppointments());
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        appointmentTable.setItems(Appointments);
        allRadioButton.setSelected(true);
    }
}
