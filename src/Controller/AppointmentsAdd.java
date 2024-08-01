package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import utils.TimeComboBoxUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static utils.utils.showAlert;
import static utils.utils.switchToScene;

/**
 * This is the controller class for the AppointmentsAdd screen. This class contains methods to initialize the
 * user, customer, and contact combo boxes with data from their respective DAO classes. It also contains methods
 * to save a new appointment when the user clicks the Save button and to return to the Appointments screen when
 * the user clicks the Cancel button.
 */
public class AppointmentsAdd implements Initializable {
    /**
     * The label for add appointments.
     */
    public Label appointmentAddIDLabel;
    /**
     * The combo box that allows the user to select a user.
     */
    public ComboBox<User> userCombo;
    /**
     * The combo box that allows the user to select a customer.
     */
    public ComboBox<Customer> customerCombo;
    /**
     * The combo box that allows the user to select a contact.
     */
    public ComboBox<Contact> contactCombo;
    /**
     * The combo box that allows the user to select a start time.
     */
    public ComboBox<ZonedDateTime> startTimeComboBox;
    /**
     * The combo box that allows the user to select an end time.
     */
    public ComboBox<ZonedDateTime> endTimeComboBox;
    /**
     * The text field that allows the user to enter a location.
     */
    @FXML
    private TextField appointmentAddLocationLabel;
    /**
     * The text field that allows the user to enter a title.
     */
    @FXML
    private TextField appointmentAddTitleLabel;
    /**
     * The text field that allows the user to enter a description.
     */
    @FXML
    private TextField appointmentAddDescriptionLabel;
    /**
     * The text field that allows the user to enter a type.
     */
    @FXML
    private TextField appointmentAddTypeLabel;
    /**
     * The date picker object that allows the user to select a date.
     */
    @FXML
    private DatePicker appointmentAddDatePicker;


    /**
     * It loads the Main Menu scene when the user clicks the Cancel button. It switches to the Appointments tab.
     *
     * @param event The event that triggered the method.
     * @throws IOException If the FXML file cannot be found.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchToScene(event, "/view/MainMenu.fxml", "Appointments");
    }


    /**
     * This method saves a new appointment to the database. It checks if the date field is empty. If the date field
     * is empty, show an alert. If the date field is not empty, check if the title, description,
     * location, type, contact, customer, user, start time, and end time fields are empty. If any of those fields are
     * empty, show an alert. If none of those fields are empty, check if the appointment overlaps with an existing
     * appointment. If the appointment overlaps with an existing appointment, show an alert. If the appointment does not
     * overlap with an existing appointment, add the appointment to the database.
     *
     * @param event The event that triggered the method.
     * @throws Exception If the FXML file cannot be found or if the SQL query cannot be executed.
     */

    @FXML
    void onActionSave(ActionEvent event) throws Exception {
        int customerId = 0;
        int userId = 0;
        int contactId = 0;
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        try {
            if (appointmentAddDatePicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Date Field Empty",
                        "The date field cannot be empty. Please enter a date.");
            } else if (appointmentAddDatePicker.getValue() != null) {

                String title = appointmentAddTitleLabel.getText();
                String description = appointmentAddDescriptionLabel.getText();
                String location = appointmentAddLocationLabel.getText();
                String type = appointmentAddTypeLabel.getText();

                Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
                String createdBy = Login.currentUser.getUserName();
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdateBy = Login.currentUser.getUserName();

                if (title.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Title Field Empty",
                            "The title field cannot be empty. Please enter a title.");
                } else if (description.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Description Field Empty",
                            "The description field cannot be empty. Please enter a description.");
                } else if (location.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Location Field Empty",
                            "The location field cannot be empty. Please enter a location.");
                } else if (contactCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Contact Field Empty",
                            "The contact field cannot be empty. Please select a contact.");
                } else if (type.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Type Field Empty",
                            "The type field cannot be empty. Please enter a type.");
                } else if (customerCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Customer Field Empty",
                            "The customer field cannot be empty. Please select a customer.");
                } else if (userCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "User Field Empty",
                            "The user field cannot be empty. Please select a user.");
                } else if (startTimeComboBox.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Start Time Field Empty",
                            "The start time field cannot be empty. Please select a start time.");
                } else if (endTimeComboBox.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "End Time Field Empty",
                            "The end time field cannot be empty. Please select an end time.");
                } else {
                    contactId = contactCombo.getValue().getContactId();
                    userId = userCombo.getValue().getUserId();
                    customerId = customerCombo.getValue().getCustomerId();
                    startDateTime = startTimeComboBox.getSelectionModel().getSelectedItem().toLocalDateTime();
                    endDateTime = endTimeComboBox.getSelectionModel().getSelectedItem().toLocalDateTime();
                    if (Appointments.hasOverlap(customerId, 0, startDateTime, endDateTime)) {
                        showAlert(Alert.AlertType.WARNING, "Validation Error", "Appointment Overlap Error",
                                "The appointment overlaps with an existing appointment. Please select a different time.");
                    } else {
                        AppointmentDaoImpl.addAppointment(title, description, location,
                                type, startDateTime, endDateTime, createDate, createdBy, lastUpdate,
                                lastUpdateBy, customerId, userId, contactId);
                        switchToScene(event, "/view/MainMenu.fxml", "Appointments");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the user, customer, and contact combo boxes with data from their respective DAO classes.
     * Also initializes the start and end time combo boxes by calling their respective initialization methods.
     *
     * @param url            - The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle - The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userCombo.setItems(UserDaoImpl.getAllUsers());
            customerCombo.setItems(CustomerDaoImpl.getAllCustomers());
            contactCombo.setItems(ContactDaoImpl.getAllContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appointmentAddDatePicker.setOnAction(event -> {
                // Get the selected date from the datepicker
                LocalDate selectedDate = appointmentAddDatePicker.getValue();
                if (selectedDate != null) {
                    // Update the start and end comboboxes based on the selected date
                    TimeComboBoxUtils.initializeStartTimeComboBox(startTimeComboBox, selectedDate);
                    // Update the end combobox when the start combobox selection changes
                    startTimeComboBox.setOnAction(event2 -> {
                        // Get the selected start time from the start combobox
                        ZonedDateTime selectedStartTime = startTimeComboBox.getSelectionModel().getSelectedItem();
                        System.out.println("Start time: " + startTimeComboBox.getSelectionModel().getSelectedItem());
                        if (selectedStartTime != null) {
                            // Update the end combobox based on the selected start time and date
                            TimeComboBoxUtils.initializeEndTimeComboBox(endTimeComboBox, selectedDate, selectedStartTime);
                        }
                    });
                }
            });
            endTimeComboBox.setOnAction(event2 -> System.out.println("End time: " + endTimeComboBox.getSelectionModel().getSelectedItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

