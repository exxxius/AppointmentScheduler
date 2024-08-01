package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the MainMenu screen. This class contains methods to initialize the tab pane which
 * contains the Customers, Appointments, and Reports tabs. It also contains methods to load the contents of each tab.
 * This class is responsible for loading the initial tab contents and reloading the tab contents when a tab is selected.
 * This class also contains the onActionLogOut method to load the Login screen when the user clicks the Log Out button.
 */
public class MainMenu implements Initializable {
    /**
     * The anchor pane for the Customers tab.
     */
    public AnchorPane customerAnchorPane;
    /**
     * The anchor pane for the Appointments tab.
     */
    public AnchorPane appointmentAnchorPane;
    /**
     * The anchor pane for the Reports tab.
     */
    public AnchorPane reportAnchorPane;
    /**
     * The TabPane containing the Customers, Appointments, and Reports tabs.
     */
    @FXML
    public TabPane tabPane;
    /**
     * The Customers tab.
     */
    public Tab customersTab;
    /**
     * The Appointments tab.
     */
    public Tab appointmentsTab;
    /**
     * The Reports tab.
     */
    public Tab reportsTab;

    /**
     * The stage of the current scene.
     */
    Stage stage;
    /**
     * The current scene.
     */
    Parent scene;

    /**
     * This method initializes the tab pane and loads the initial tab contents.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load initial tab contents
        loadCustomersTab();
        loadAppointmentsTab();
        loadReportsTab();

        // Add listener to reload tab contents when a tab is selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            // Determine which tab was selected and reload its content
            if (newTab == customersTab) {
                loadCustomersTab();
            } else if (newTab == appointmentsTab) {
                loadAppointmentsTab();
            } else if (newTab == reportsTab) {
                loadReportsTab();
            }
        });
    }

    /**
     * Load the Customers.fxml file into the customersTab TabPane.
     *
     * @throws IOException if an error occurs while loading.
     */
    private void loadCustomersTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customers.fxml"));
        try {
            Parent customersPane = loader.load();
            customersTab.setContent(customersPane);
        } catch (IOException e) {
            System.err.println("Failed to load Customers.fxml: " + e.getMessage());
        }
    }

    /**
     * Load the Appointments.fxml file into the appointmentsTab tab.
     *
     * @throws IOException if an error occurs while loading.
     */
    private void loadAppointmentsTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointments.fxml"));
        try {
            Parent appointmentsPane = loader.load();
            appointmentsTab.setContent(appointmentsPane);
        } catch (IOException e) {
            System.err.println("Failed to load Appointments.fxml: " + e.getMessage());
        }
    }

    /**
     * Load the Reports.fxml file into the reportsTab tab.
     *
     * @throws IOException if an error occurs while loading.
     */
    private void loadReportsTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Reports.fxml"));
        try {
            Parent reportsPane = loader.load();
            reportsTab.setContent(reportsPane);
        } catch (IOException e) {
            System.err.println("Failed to load Reports.fxml: " + e.getMessage());
        }
    }

    /**
     * This is the method to load the Login screen when the user clicks the Log Out button.
     *
     * @param event The user clicks the Log Out button.
     * @throws IOException if an error occurs while loading.
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
