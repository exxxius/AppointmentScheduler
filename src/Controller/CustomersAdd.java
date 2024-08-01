package Controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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
 * This is the controller class for the CustomersAdd screen. This class contains methods to initialize the
 * country and division combo boxes with data from their respective DAO classes. It also contains methods
 * to save a new customer when the user clicks the Save button and to return to the Customers screen when
 * the user clicks the Cancel button.
 *
 * @author Mehdi Rahimi
 */
public class CustomersAdd implements Initializable {
    /**
     * The countryCombo variable is used to store the country combo box.
     */
    public ComboBox<Country> countryCombo;
    /**
     * The divisionCombo variable is used to store the division combo box.
     */
    public ComboBox<FirstLevelDivision> divisionCombo;
    /**
     * The customersAddNameLabel variable is used to store the customer name text field.
     */
    @FXML
    private TextField customersAddNameLabel;
    /**
     * The customersAddPhoneLabel variable is used to store the customer phone text field.
     */
    @FXML
    private TextField customersAddPhoneLabel;
    /**
     * The customersAddAddressLabel variable is used to store the customer address text field.
     */
    @FXML
    private TextField customersAddAddressLabel;
    /**
     * The customersAddPostalLabel variable is used to store the customer postal code text field.
     */
    @FXML
    private TextField customersAddPostalLabel;


    /**
     * The function checks if the customer information fields are empty, if they are, it will display an error message.
     * If they are not empty, it will add the customer to the database. It will then switch to the Customers screen.
     *
     * @param event The event that triggered the method.
     * @throws IOException  If the FXML file cannot be found.
     * @throws SQLException If the SQL query cannot be executed.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            String customerName = customersAddNameLabel.getText();
            String customerAddress = customersAddAddressLabel.getText();
            String postalCode = customersAddPostalLabel.getText();
            String customerPhone = customersAddPhoneLabel.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = Login.currentUser.getUserName();
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = Login.currentUser.getUserName();
            FirstLevelDivision D = divisionCombo.getValue();

            if (customerName.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The name must not be empty", null);
            } else if (customerAddress.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The address must not be empty", null);
            } else if (postalCode.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The postal code must not be empty", null);
            } else if (customerPhone.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The phone must not be empty", null);
            } else if (divisionCombo.getSelectionModel().getSelectedItem() == null) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The division must not be empty", null);
            } else if (countryCombo.getSelectionModel().getSelectedItem() == null) {
                showAlert(Alert.AlertType.WARNING, "Warning Dialog", "ERROR: The country must not be empty", null);
            } else {
                int divisionId = D.getDivisionId();
                CustomerDaoImpl.addCustomer(customerName, customerAddress, postalCode, customerPhone, createDate,
                        createdBy, lastUpdate, lastUpdateBy, divisionId);
                switchToScene(event, "/view/MainMenu.fxml", "Customers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the method to switch to the Customers screen when the user clicks the Cancel button.
     *
     * @param event the user clicks the Cancel button
     * @throws IOException If the FXML file cannot be found.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchToScene(event, "/view/MainMenu.fxml", "Customers");
    }

    /**
     * This is the method to set the divisionCombo combo box. It gets the divisionId from the countryCombo combo box
     * and uses it to get the division name from the FirstLevelDivisionDaoImpl class.
     *
     * @param event The event that triggered the method.
     * @throws SQLException If the SQL query cannot be executed.
     */
    @FXML
    void OnActionCountryCombo(ActionEvent event) throws SQLException {
        Country C = countryCombo.getValue();
        divisionCombo.setItems(FirstLevelDivisionDaoImpl.getDiv(C.getCountryId()));
    }

    /**
     * This is the method to set the divisionCombo combo box.
     *
     * @param event The event that triggered the method.
     */
    @FXML
    void onActionDivisionCombo(ActionEvent event) {
    }


    /**
     * The initialize function is called when the FXML file is loaded. It sets the items of the countryCombo to the list of
     * countries returned by the getAllCountries function of the CountryDaoImpl class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle This is a resource bundle that contains the resources for the controller.
     * @throws Exception    If the FXML file cannot be found.
     * @throws SQLException If the SQL query cannot be executed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryCombo.setItems(CountryDaoImpl.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}