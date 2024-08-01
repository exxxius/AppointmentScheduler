package Controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Appointment;
import Model.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.utils.*;

/**
 * This is the controller class for the Customers screen. This class contains methods to initialize the
 * customer table with data from the Customer DAO class. It also contains methods to load the CustomersAdd
 * screen when the user clicks the Add button and to delete a customer when the user clicks the Delete button.
 *
 * @author Mehdi Rahimi
 */
public class Customers implements Initializable {
    /**
     * List of customers to be displayed in the customer table.
     */
    ObservableList<Model.Customer> CustomerList = FXCollections.observableArrayList();
    /**
     * The Customers table.
     */
    @FXML
    private TableView<Customer> CustomerTable;
    /**
     * The Customer ID column.
     */
    @FXML
    private TableColumn<Customer, Integer> Customer_ID;
    /**
     * The Customer Name column.
     */
    @FXML
    private TableColumn<Customer, String> Customer_Name;
    /**
     * The Address column.
     */
    @FXML
    private TableColumn<Customer, String> Address;
    /**
     * The Postal Code column.
     */
    @FXML
    private TableColumn<Customer, String> Postal_Code;
    /**
     * The Phone Number column.
     */
    @FXML
    private TableColumn<Customer, String> Phone_Number;
    /**
     * The Create Date column.
     */
    @FXML
    private TableColumn<Customer, String> Create_Date;
    /**
     * The Created By column.
     */
    @FXML
    private TableColumn<Customer, String> Create_By;
    /**
     * The Last Update column.
     */
    @FXML
    private TableColumn<Customer, String> Last_Update;
    /**
     * The Last Updated By column.
     */
    @FXML
    private TableColumn<Customer, String> Last_Updated_By;
    /**
     * The Division ID column.
     */
    @FXML
    private TableColumn<Customer, Integer> Division_ID;


    /**
     * When the user clicks the "Add Customer" button, switch to the "CustomersAdd.fxml" screen.
     *
     * @param event The event that triggered the switch.
     * @throws IOException If the file cannot be found.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        switchToScene(event, "/view/CustomersAdd.fxml", null);
    }

    /**
     * If a customer is selected, confirm that the user wants to delete the customer, then if the customer has
     * appointments, confirm that the user wants to delete the appointments, then delete the appointments, then delete the
     * customer
     *
     * @param event The event that triggered the method.
     *              {@code @LAMBDA} expression used to delete the customer and appointments. It facilitates the use of the foreach
     *              loop to delete the appointments inside a try/catch block to catch any SQLExceptions.
     * @throws Exception If the customer cannot be deleted.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws Exception {
        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: No customer selected", null);
            return;
        }
        showConfirmationAlert("Confirmation Dialog", "Are you sure you want to delete this customer?",
                null,
                () -> {
                    try {
                        ObservableList<Appointment> apptsList = AppointmentDaoImpl.getAppointmentsByCustomerId(selectedCustomer.getCustomerId());
                        if (!apptsList.isEmpty()) {
                            showConfirmationAlert("Confirmation Dialog", "Must delete all appointments first. Are you sure you want to delete all appointments for this customer?",
                                    null,
                                    () -> {
                                        apptsList.forEach(appointment -> {
                                            try {
                                                AppointmentDaoImpl.deleteAppointment(appointment);
                                            } catch (SQLException e) {
                                                showAlert(Alert.AlertType.ERROR, "Error Dialog", "Error deleting appointment", e.getMessage());
                                            }
                                        });
                                        deleteCustomer(selectedCustomer);
                                    },
                                    null);
                        } else {
                            deleteCustomer(selectedCustomer);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                null);
    }

    /**
     * The function takes a customer object as a parameter, and then calls the deleteCustomer function in the
     * CustomerDaoImpl class. If the function is successful, it will show an information dialog box, and if it fails, it
     * will show an error dialog box
     *
     * @param customer The customer object to be deleted.
     */
    private void deleteCustomer(Customer customer) {
        try {
            CustomerDaoImpl.deleteCustomerById(customer);
            showAlert(Alert.AlertType.INFORMATION, "Information Dialog", "Customer has been successfully deleted", null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error Dialog", "Error deleting customer", e.getMessage());
        }
    }

    /**
     * When the user clicks the "Modify Customer" button, switch to the Customers Modify screen.
     * If no customer is selected, shows an error dialog box.
     *
     * @param event The event that triggered the switch.
     * @throws IOException If the file cannot be found.
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: No customer selected", null);
        } else {
            CustomersModify.passedinSelectedCustomer(CustomerTable.getSelectionModel().getSelectedItem());
            switchToScene(event, "/view/CustomersModify.fxml", null);
        }
    }


    /**
     * Theis function is the initialize function. It is called when the FXML file is loaded. It sets the cell value
     * factory for each column in the Customers table view. It also sets the items in the table view to the list of
     * customers returned by the getAllCustomers function in the CustomerDaoImpl class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle This is a ResourceBundle object that contains the resources for the application.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        Phone_Number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        Create_Date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        Last_Update.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastUpdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        Create_By.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        Last_Updated_By.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        try {
            CustomerList.addAll(CustomerDaoImpl.getAllCustomers());
        } catch (Exception ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
        CustomerTable.setItems(CustomerList);
    }
}