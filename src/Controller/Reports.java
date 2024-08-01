package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Appointment;
import Model.AppointmentType;
import Model.Contact;
import Model.Country;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.utils.showAlert;

/**
 * This class creates the Reports controller.
 *
 * @author Mehdi Rahimi
 */
public class Reports implements Initializable {

    /**
     * A variable that is used to store the stage of the application.
     *
     * @deprecated This variable is no longer used.
     */
    Stage stage;
    /**
     * A variable that is used to store the scene of the application.
     *
     * @deprecated This variable is no longer used.
     */
    Parent scene;
    /**
     * A variable that is used to store the list of appointments.
     */
    ObservableList<Model.Appointment> Appointment = FXCollections.observableArrayList();
    /**
     * A variable that is used to store the list of countries.
     */
    ObservableList<String> Months = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    /**
     * A combobox that is used to store the list of appointment months.
     */
    @FXML
    private ComboBox<String> monthCombo;
    /**
     * A combobox that is used to store the list of appointment types.
     */
    @FXML
    private ComboBox<AppointmentType> typeCombo;
    /**
     * A label that is used to store the total number of appointments.
     */
    @FXML
    private Label appointmentsTotalLabel;
    /**
     * ComboBox that is used to store the list of contacts.
     */
    @FXML
    private ComboBox<Contact> contactCombo;
    /**
     * ComboBox that is used to store the list of countries.
     */
    @FXML
    private ComboBox<Country> countryCombo;
    /**
     * Label that is used to show the total number of customers.
     */
    @FXML
    private Label customersTotalLabel;
    /**
     * TableView that is used to show the list of appointments.
     */
    @FXML
    private TableView<Appointment> appointmentTableView;
    /**
     * TableColumn that is used to show the appointment ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    /**
     * TableColumn that is used to show the appointment title.
     */
    @FXML
    private TableColumn<Appointment, String> title;
    /**
     * TableColumn that is used to show the appointment description.
     */
    @FXML
    private TableColumn<Appointment, String> description;
    /**
     * TableColumn that is used to show the appointment location.
     */
    @FXML
    private TableColumn<Appointment, String> location;
    /**
     * TableColumn that is used to show the appointment contact ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> contactId;
    /**
     * TableColumn that is used to show the appointment type.
     */
    @FXML
    private TableColumn<Appointment, String> type;
    /**
     * TableColumn that is used to show the appointment start time.
     */
    @FXML
    private TableColumn<Appointment, String> start;
    /**
     * TableColumn that is used to show the appointment end time.
     */
    @FXML
    private TableColumn<Appointment, String> end;
    /**
     * TableColumn that is used to show the appointment customer ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerId;
    /**
     * TableColumn that is used to show the appointment user ID.
     */
    @FXML
    private TableColumn<Appointment, Integer> userId;


    /**
     * When the user selects a country from the countryCombo, the function gets the countryId of the selected country and
     * passes it to the countCustomers() function in the CustomerDaoImpl class. The countCustomers() function returns the
     * number of customers in the selected country and the function sets the customersTotalLabel to the number of customers
     *
     * @param event The event that triggered the method.
     * @throws SQLException If the SQL query is invalid.
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) throws SQLException {
        Country selectedCountry = countryCombo.getValue();
        int selectedCountryID = selectedCountry.getCountryId();
        customersTotalLabel.setText(String.valueOf(CustomerDaoImpl.countCustomers(selectedCountryID)));
    }

    /**
     * If the user selects a month and a type, then the function will call the validateSelection function
     *
     * @param event The event that triggered the method.
     * @throws SQLException If the SQL query is invalid.
     */
    @FXML
    void onActionTypeCombo(ActionEvent event) throws SQLException {
        AppointmentType selectedType = typeCombo.getValue();
        String selectedMonth = monthCombo.getValue();
        validateSelection(selectedType, selectedMonth);
    }

    /**
     * If the user selects a month from the monthCombo, then the function will validate the selection
     *
     * @param event The event that triggered the method.
     * @throws SQLException If the SQL query is invalid.
     */
    @FXML
    void onActionMonthCombo(ActionEvent event) throws SQLException {
        AppointmentType selectedType = typeCombo.getValue();
        String selectedMonth = monthCombo.getValue();
        validateSelection(selectedType, selectedMonth);
    }

    /**
     * Validates the selected appointment type and month. If either is null, an error
     * alert is displayed and the method returns without running the countMonthType method.
     * Otherwise, it counts the number of appointments of the selected type in the selected
     * month, and sets the appointmentsTotalLabel to display the count.
     *
     * @param selectedType  The selected appointment type.
     * @param selectedMonth The selected month.
     * @throws SQLException If an error occurs while querying the database.
     */
    private void validateSelection(AppointmentType selectedType, String selectedMonth) throws SQLException {
        if (selectedType == null || selectedMonth == null) {
            String message = selectedType == null
                    ? "Please select a type from the dropdown menu."
                    : "Please select a month from the dropdown menu.";
            showAlert(Alert.AlertType.ERROR, "Error", "Selection Error", message);
            return;
        }

        int count = AppointmentDaoImpl.countMonthType(selectedType, selectedMonth);
        appointmentsTotalLabel.setText(String.valueOf(count));
    }

    /**
     * This is the method to display the appointments for the selected contact when the user selects a contact from the contactCombo combo box.
     *
     * @param event the user selects a contact from the contactCombo combo box
     * @throws SQLException If the SQL query is invalid.
     */
    @FXML
    void onActionContactCombo(ActionEvent event) {
        Appointment.clear();
        Contact selectedContact = contactCombo.getValue();
        int selectedContactID = selectedContact.getContactId();

        try {
            Appointment.addAll(AppointmentDaoImpl.getAppointmentsByContactId(selectedContactID));
        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This is the method to set the combo boxes and set the AppointmentTable from the Appointments table in the database.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * @throws SQLException If the SQL query is invalid.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactCombo.setItems(ContactDaoImpl.getAllContacts());
            countryCombo.setItems(CountryDaoImpl.getAllCountries());
            typeCombo.setItems(AppointmentDaoImpl.typeAppt());
            monthCombo.setItems(Months);
        } catch (Exception e) {
            e.printStackTrace();
        }

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        end.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTableView.setItems(Appointment);
    }
}
