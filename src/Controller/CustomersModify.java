package Controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static utils.utils.showAlert;
import static utils.utils.switchToScene;

/**
 * This is the controller class for the CustomersModify screen. This class contains methods to initialize the
 * country and division combo boxes with data from their respective DAO classes. It also contains methods
 * to modify an existing customer when the user clicks the Save button and to return to the Customers screen when
 * the user clicks the Cancel button.
 *
 * @author Mehdi Rahimi
 */
public class CustomersModify implements Initializable {
    /**
     * The selectedCustomer variable is used to store the customer that was selected in the Customers screen.
     */
    private static Customer selectedCustomer;
    /**
     * The selectedCountryID variable is used to store the country ID of the customer.
     */
    private static int selectedCountryID;
    /**
     * The selectedCountry variable is used to store the country of the customer.
     */
    private static Country selectedCountry;
    /**
     * The selectedDivision variable is used to store the country division of the customer.
     */
    @FXML
    private ComboBox<FirstLevelDivision> divisionCombo;
    /**
     * The countryCombo variable is used to store the country combo box.
     */
    @FXML
    private ComboBox<Country> countryCombo;
    /**
     * label that is used to display the customer ID.
     */
    @FXML
    private Label customersModifyIDLabel;
    /**
     * Text field that is used to display and change the customer's name.
     */
    @FXML
    private TextField customersModifyNameLabel;
    /**
     * Text field that is used to display and change the customer's phone number.
     */
    @FXML
    private TextField customersModifyPhoneLabel;
    /**
     * Text field that is used to display and change the customer's address.
     */
    @FXML
    private TextField customersModifyAddressLabel;
    /**
     * Text field that is used to display and change the customer's postal code.
     */
    @FXML
    private TextField customersModifyPostalLabel;


    /**
     * This function is called when the user clicks the "Cancel" button. It switches to the main menu scene.
     *
     * @param event The event that triggered the method.
     * @throws IOException If the FXML file cannot be found.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchToScene(event, "/view/MainMenu.fxml", "Customers");
    }


    /**
     * This function is called when the user clicks the "Save" button. It checks if the customer information fields are
     * empty, if they are, it will display an error message. If they are not empty, it will modify the customer in the
     * database. It will then switch to the Customers screen.
     *
     * @param event The event that triggered the method.
     * @throws IOException  If the FXML file cannot be found.
     * @throws SQLException If the SQL query cannot be executed.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        try {
            int customerID = Integer.parseInt(customersModifyIDLabel.getText());
            String customerName = customersModifyNameLabel.getText();
            String customerAddress = customersModifyAddressLabel.getText();
            String postalCode = customersModifyPostalLabel.getText();
            String customerPhone = customersModifyPhoneLabel.getText();
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = Login.currentUser.getUserName();
            FirstLevelDivision D = divisionCombo.getValue();

            if (customerName.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The name must not be empty", "Please enter a name");
            } else if (customerAddress.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The address must not be empty", "Please enter an address");
            } else if (postalCode.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The postal code must not be empty", "Please enter a postal code");
            } else if (customerPhone.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The phone must not be empty", "Please enter a phone number");
            } else if (divisionCombo.getSelectionModel().getSelectedItem() == null) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The division must not be empty", "Please select a division");
            } else if (countryCombo.getSelectionModel().getSelectedItem() == null) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The country must not be empty", "Please select a country");
            } else {
                int divisionId = D.getDivisionId();
                CustomerDaoImpl.modifyCustomer(customerID, customerName, customerAddress, postalCode, customerPhone, lastUpdate, lastUpdateBy, divisionId);
                switchToScene(event, "/view/MainMenu.fxml", "Customers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the user selects a country from the country combo box. It will populate the division
     * combo box with the divisions from the selected country.
     *
     * @param event The event that triggered the method.
     * @throws SQLException If the SQL query cannot be executed.
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) throws SQLException {
        divisionCombo.setValue(null);
        Country C = countryCombo.getValue();
        divisionCombo.setItems(FirstLevelDivisionDaoImpl.getDiv(C.getCountryId()));
    }


    /**
     * This function sets the selected customer to the customer passed in from the Customers table view. This is
     * called from the Customers screen. It is called when the user clicks the Modify button.
     *
     * @param customer The customer object that was selected in the previous activity.
     */
    public static void passedinSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    /**
     * This method is called when the user clicks the Modify button in the Customers screen. It will populate the
     * country and division combo boxes with data from the database. It will also populate the text fields with the
     * customer information from the selected customer.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            divisionCombo.setValue(FirstLevelDivisionDaoImpl.getFirstLvlDivByDivID(selectedCustomer.getDivisionId()));
            FirstLevelDivision selectedDivision = FirstLevelDivisionDaoImpl.getDivision(divisionCombo.getValue());
            assert selectedDivision != null;
            selectedCountryID = selectedDivision.getCountryId();
            selectedCountry = CountryDaoImpl.getCountryFromCountryID(selectedCountryID);
            countryCombo.setValue(selectedCountry);
            Country C = countryCombo.getValue();
            divisionCombo.setItems(FirstLevelDivisionDaoImpl.getDiv(C.getCountryId()));
            countryCombo.setItems(CountryDaoImpl.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }
        customersModifyIDLabel.setText(Integer.toString(selectedCustomer.getCustomerId()));
        customersModifyNameLabel.setText(selectedCustomer.getCustomerName());
        customersModifyAddressLabel.setText(selectedCustomer.getCustomerAddress());
        customersModifyPhoneLabel.setText(selectedCustomer.getCustomerPhone());
        customersModifyPostalLabel.setText(selectedCustomer.getPostalCode());
    }
}