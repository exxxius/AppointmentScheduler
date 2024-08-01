package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import Model.Appointment;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static utils.utils.showAlert;
import static utils.utils.switchToScene;

/**
 * This is the controller class for the AppointmentsModify screen. This class contains methods to initialize the
 * user, customer, and contact combo boxes with data from their respective DAO classes. It also contains methods
 * to save a modified appointment when the user clicks the Save button and to return to the Appointments screen when
 * the user clicks the Cancel button.
 * * @author Mehdi Rahimi
 */
public class AppointmentsModify implements Initializable {
    /**
     * The appointment to be modified.
     */
    private static Appointment selectedAppointment;
    /**
     * The appointment ID label.
     */
    @FXML
    private Label appointmentModifyIDLabel;
    /**
     * ComboBox object that will hold the Contact objects.
     */
    @FXML
    private ComboBox<Contact> appointmentModifyContactCombo;
    /**
     * The appointment location label.
     */
    @FXML
    private TextField appointmentModifyLocationLabel;
    /**
     * The appointment title label.
     */
    @FXML
    private TextField appointmentModifyTitleLabel;
    /**
     * The appointment description label.
     */
    @FXML
    private TextField appointmentModifyDescriptionLabel;
    /**
     * The appointment type text field.
     */
    @FXML
    private TextField appointmentModifyTypeLabel;

    /**
     * The appointment date picker.
     */
    @FXML
    private DatePicker appointmentModifyDatePicker;
    /**
     * The appointment start time combo box.
     */
    @FXML
    private ComboBox<ZonedDateTime> appointmentModifyStartCombo;
    /**
     * The appointment end time combo box.
     */
    @FXML
    private ComboBox<ZonedDateTime> appointmentModifyEndCombo;
    /**
     * ComboBox object that will hold the Customer objects.
     */
    @FXML
    private ComboBox<Customer> appointmentModifyCustomerIDCombo;
    /**
     * ComboBox object that will hold the User objects.
     */
    @FXML
    private ComboBox<User> appointmentModifyUserIDCombo;


    /**
     * It cancels updating the appointment. It loads the Main Menu screen. It switches to the Appointments tab.
     *
     * @param event The event that triggered the method.
     * @throws IOException If the FXML file cannot be found.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchToScene(event, "/view/MainMenu.fxml", "Appointments");
    }


    /**
     * This method saves the modified appointment. It validates the data entered by the user.<br>
     * If the date field is not empty, and all other fields are not empty, and the start and end times are not empty, and
     * the appointment does not overlap with an existing appointment, then modify the appointment. It loads the Main Menu
     * screen. It switches to the Appointments tab.
     *
     * @param event The event that triggered the method.
     * @throws IOException If the FXML file cannot be found.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        try {
            if (appointmentModifyDatePicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Date Field Empty",
                        "The date field cannot be empty. Please enter a date.");
            } else if (appointmentModifyDatePicker.getValue() != null) {
                int appointmentID = Integer.parseInt(appointmentModifyIDLabel.getText());
                String appointmentTitle = appointmentModifyTitleLabel.getText();
                String appointmentDescription = appointmentModifyDescriptionLabel.getText();
                String appointmentLocation = appointmentModifyLocationLabel.getText();
                String appointmentType = appointmentModifyTypeLabel.getText();

                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdateBy = Login.currentUser.getUserName(); //Eliminating Global class
                int customerID = appointmentModifyCustomerIDCombo.getValue().getCustomerId();
                int userID = appointmentModifyUserIDCombo.getValue().getUserId();
                int contactID = appointmentModifyContactCombo.getValue().getContactId();

                if (appointmentTitle.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Title Field Empty",
                            "The title field cannot be empty. Please enter a title.");
                } else if (appointmentDescription.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Description Field Empty",
                            "The description field cannot be empty. Please enter a description.");
                } else if (appointmentLocation.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Location Field Empty",
                            "The location field cannot be empty. Please enter a location.");
                } else if (appointmentModifyContactCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Contact Field Empty",
                            "The contact field cannot be empty. Please select a contact.");
                } else if (appointmentType.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Type Field Empty",
                            "The type field cannot be empty. Please enter a type.");
                } else if (appointmentModifyCustomerIDCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Customer Field Empty",
                            "The customer field cannot be empty. Please select a customer.");
                } else if (appointmentModifyUserIDCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "User Field Empty",
                            "The user field cannot be empty. Please select a user.");
                } else if (appointmentModifyStartCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Start Time Empty",
                            "The start time cannot be empty. Please select a start time.");
                } else if (appointmentModifyEndCombo.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "End Time Empty",
                            "The end time cannot be empty. Please select an end time.");
                } else {
                    startDateTime = appointmentModifyStartCombo.getSelectionModel().getSelectedItem().toLocalDateTime();
                    endDateTime = appointmentModifyEndCombo.getSelectionModel().getSelectedItem().toLocalDateTime();
                    if (startDateTime == null || endDateTime == null) {
                        showAlert(Alert.AlertType.WARNING, "Validation Error", "Date Time Fields Empty",
                                "The date time fields cannot be empty. Please select a start and end time.");
                    } else {
                        if (Appointments.hasOverlap(customerID, appointmentID, startDateTime, endDateTime)) {
                            showAlert(Alert.AlertType.WARNING, "Validation Error", "Appointment Overlap Error",
                                    "The appointment overlaps with an existing appointment. Please select a different time.");
                        } else {
                            AppointmentDaoImpl.modifyAppointment(appointmentID, appointmentTitle, appointmentDescription,
                                    appointmentLocation, appointmentType, startDateTime, endDateTime, lastUpdate,
                                    lastUpdateBy, customerID, userID, contactID);
                            switchToScene(event, "/view/MainMenu.fxml", "Appointments");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method passed the appointment selected on the Appointments table view to be updated in the fields on the AppointmentsModify window.
     *
     * @param appointment the selected appointment.
     */
    public static void passedinSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }


    /**
     * This function initializes the appointment modify screen with the selected appointment's information, and
     * initializes the start and end time combo boxes based on the selected appointment's start and end time.
     * Lambda expressions are used to populate the start and end time combo boxes with the correct times whenever the
     * user selects a different date, or clicks on the start or end time combo boxes.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that was given to the FXMLLoader.
     * @throws Exception If the FXML file cannot be found or if the combo boxes cannot be populated.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentModifyContactCombo.setValue(ContactDaoImpl.getContactFromContactID(selectedAppointment.getContactId()));
            appointmentModifyContactCombo.setItems(ContactDaoImpl.getAllContacts());
            appointmentModifyCustomerIDCombo.setValue(CustomerDaoImpl.getCustomerByCustomerId(selectedAppointment.getCustomerId()));
            appointmentModifyCustomerIDCombo.setItems(CustomerDaoImpl.getAllCustomers());
            appointmentModifyUserIDCombo.setValue(UserDaoImpl.getUserFromUserID(selectedAppointment.getUserId()));
            appointmentModifyUserIDCombo.setItems(UserDaoImpl.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appointmentModifyIDLabel.setText(Integer.toString(selectedAppointment.getAppointmentId()));
        appointmentModifyTitleLabel.setText(selectedAppointment.getTitle());
        appointmentModifyDescriptionLabel.setText(selectedAppointment.getDescription());
        appointmentModifyLocationLabel.setText(selectedAppointment.getLocation());
        appointmentModifyTypeLabel.setText(selectedAppointment.getType());
        appointmentModifyDatePicker.setValue(selectedAppointment.getStart().toLocalDate());

        // Initialize the start and end time combo boxes based on the selected appointment's start and end time
        TimeComboBoxUtils.initializeStartTimeComboBox(appointmentModifyStartCombo, selectedAppointment.getStart().toLocalDate());
        TimeComboBoxUtils.initializeEndTimeComboBox(appointmentModifyEndCombo, selectedAppointment.getStart().toLocalDate(), selectedAppointment.getStart().atZone(ZoneId.systemDefault()));

        appointmentModifyStartCombo.setValue(selectedAppointment.getStart().atZone(ZoneId.systemDefault()));
        appointmentModifyEndCombo.setValue(selectedAppointment.getEnd().atZone(ZoneId.systemDefault()));

        appointmentModifyStartCombo.setOnAction(event2 -> {
            // Get the selected start time from the start combobox
            ZonedDateTime selectedStartTime = appointmentModifyStartCombo.getSelectionModel().getSelectedItem();
            if (selectedStartTime != null) {
                // Update the end combobox based on the selected start time and date
                TimeComboBoxUtils.initializeEndTimeComboBox(appointmentModifyEndCombo, appointmentModifyDatePicker.getValue(), selectedStartTime);
            }
        });
        try {
            appointmentModifyDatePicker.setOnAction(event -> {
                // Get the selected date from the datepicker
                LocalDate selectedDate = appointmentModifyDatePicker.getValue();
                if (selectedDate != null) {
                    // Update the start and end comboboxes based on the selected date
                    TimeComboBoxUtils.initializeStartTimeComboBox(appointmentModifyStartCombo, selectedDate);
                    // Update the end combobox when the start combobox selection changes
                    appointmentModifyStartCombo.setOnAction(event2 -> {
                        // Get the selected start time from the start combobox
                        ZonedDateTime selectedStartTime = appointmentModifyStartCombo.getSelectionModel().getSelectedItem();
                        if (selectedStartTime != null) {
                            // Update the end combobox based on the selected start time and date
                            TimeComboBoxUtils.initializeEndTimeComboBox(appointmentModifyEndCombo, selectedDate, selectedStartTime);
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
