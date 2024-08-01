package Controller;

import DAO.AppointmentDaoImpl;
import DAO.UserDaoImpl;
import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.JDBC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Stream;

import static Controller.Main.LoginStage;
import static utils.utils.*;

/**
 * This class creates the Login controller.
 *
 * @author Mehdi Rahimi
 */
public class Login implements Initializable {
    /**
     * This is the current user.
     */
    public static User currentUser;
    /**
     * This is the current language.
     */
    public Label languageLbl;
    /**
     * This is the root pane.
     */
    @FXML
    private Pane rootPane;
    /**
     * This is the login stage.
     * @deprecated This is deprecated.
     */
    private Stage stage;
    /**
     * Loading the resource bundle from the bundle/lang folder.
     */
    ResourceBundle myBundle = ResourceBundle.getBundle("bundle/lang");
    /**
     * This is the current time zone of the user.
     */
    TimeZone defaultTimeZone = TimeZone.getDefault();
    /**
     * Getting the ID of the default time zone.
     */
    String timeZoneLbl = defaultTimeZone.getID();

    /**
     * This is the username text field.
     */
    @FXML
    private TextField logInUsernameTextField;
    /**
     * This is the password text field.
     */
    @FXML
    private PasswordField logInPasswordTextField;
    /**
     * This is the username label.
     */
    @FXML
    private Label userNameLabel;
    /**
     * This is the password label.
     */
    @FXML
    private Label passwordLabel;
    /**
     * This is the location text.
     */
    @FXML
    private Label locationText;
    /**
     * This is the location label.
     */
    @FXML
    private Label locationLabel;
    /**
     * This is the time zone label.
     */
    @FXML
    private Button logInButton;
    /**
     * This is the exit button.
     */
    @FXML
    private Button exitButton;
    /**
     * This is the language selector combobox.
     */
    @FXML
    private ComboBox<String> langSelector;


    /**
     * This method exits the program. If the user clicks the exit button, show a confirmation alert asking if they want
     * to exit, and if they click yes, close the database connection and exit the program.
     * LAMBDA expression for the onConfirm parameter closes the JDBC connection and exits the program. The onCancel
     * parameter is set as null since nothing needs to be done if the user cancels the confirmation dialog.
     *
     * @param event The event that triggered the action.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        showConfirmationAlert(myBundle.getString("ExitButton"), myBundle.getString("Exit"), null,
                () -> {
                    JDBC.closeConnection();
                    System.exit(0);
                },
                null
        );
    }

    /**
     * This method handles the log in button action event. Validates the user's input username and password, writes a login activity
     * record to a file, and displays a warning alert if the input is invalid or if the login fails. If the login is
     * successful, sets the global username variable, checks for any upcoming appointments for the user, writes a login
     * activity record indicating a successful login, and switches to the main menu scene.
     *
     * @param event the action event triggered by the log in button.
     * @throws Exception if an error occurs while checking for upcoming appointments or switching to the main menu screen.
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws Exception {
        String userName = logInUsernameTextField.getText().trim();
        String password = logInPasswordTextField.getText().trim();
        try {
            if (userName.isEmpty() || password.isEmpty() || !UserDaoImpl.validateUserName(userName) || !UserDaoImpl.validatePassword(password)) {
                writeLoginActivity(userName, false);
                showAlert(Alert.AlertType.ERROR, "LOGINERROR", "LOGINERROR", "", myBundle);
            } else if (UserDaoImpl.validateLogIn(userName, password)) {
                currentUser = UserDaoImpl.getUser(userName);
                checkUpcomingAppointments();
                writeLoginActivity(userName, true);
                switchToScene(event, "/view/MainMenu.fxml", "Customers");
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeLoginActivity(userName, false);
        }
    }

    /**
     * Writes login activity to a file with the given user name and success status.
     *
     * @param userName the username to include in the login activity message
     * @param success  the success status of the login attempt
     * @throws IOException if there is an error writing to the file
     */
    private void writeLoginActivity(String userName, boolean success) throws IOException {
        String filename = "login_activity.txt";
        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime utcNow = now.atZone(ZoneId.of("Etc/UTC"));
        String message;
        if (success) {
            message = "User " + userName + " successfully logged in at " + utcNow;
        } else {
            message = "User " + userName + " failed login at " + utcNow;
        }
        outputFile.println(message);
        outputFile.close();
    }

    /**
     * This method checks for upcoming appointments within the next 15 minutes for the logged in user.
     * If there are any appointments within the next 15 minutes, a warning dialog is displayed with the appointment information.
     * If there are no upcoming appointments, a warning dialog is displayed indicating so.
     *
     * @throws Exception if there is an error while retrieving appointments data from the database.
     */
    private void checkUpcomingAppointments() throws Exception {
        int userID = UserDaoImpl.getUserIDFromUserName(currentUser.getUserName());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus15 = now.plusMinutes(15); //To check for appointments within 15 minutes.
        ObservableList<Appointment> apptsIn15 = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = AppointmentDaoImpl.getAppointmentByUserId(userID);
        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                if ((appointment.getStart().isEqual(now)) || appointment.getStart().isAfter(now) &&
                        (appointment.getStart().isBefore(plus15)) || appointment.getStart().isEqual(plus15)) {
                    apptsIn15.add(appointment);
                    showAlert(Alert.AlertType.INFORMATION, "ReminderAppt", "Attention", (myBundle.getString("ReminderAppt")
                            + appointment.getAppointmentId() + " " + myBundle.getString("At")
                            + appointment.getStart() + " " + myBundle.getString("StartsSoon")), myBundle);
                }
            }
            if (apptsIn15.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "NoAppointments", "NoAppointments", "", myBundle);
            }
        }
    }

    /**
     * This method loads the MainMenu.fxml file and switches the current scene to the Main Menu.
     *
     * @param event An ActionEvent representing the user clicking the "Log In" button.
     * @throws IOException if there is an error loading the MainMenu.fxml file.
     */
    private void switchToMainMenu(ActionEvent event) throws IOException {
        try {
            // Load the MainMenu.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
            Parent root = loader.load();
            // Create a new scene with the loaded file
            Scene scene = new Scene(root);
            // Get the current stage and set its scene to the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the login page with the current language bundle.
     * Loads the appropriate text for the exit button, username and password labels,
     * login button, location text field and language label.
     */
    @FXML
    public void updateLoginPageLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());
        exitButton.setText(bundle.getString("ExitButton"));
        userNameLabel.setText(bundle.getString("Username"));
        passwordLabel.setText(bundle.getString("Password"));
        logInButton.setText(bundle.getString("Login"));
        locationText.setText(bundle.getString("Location"));
        languageLbl.setText(bundle.getString("Language"));
    }

    /**
     * This method initializes the Login page, and sets the text on the Login page in either English or French depending
     * on the user's location.
     * LAMBDA EXPRESSION: This method uses a lambda expressions to set the action for the language selector combo box.
     * It loads the appropriate language bundle and sets the text on the login page to the appropriate language.
     * Second lambda expression: This method adds a listener to the language selector combo box. When the user selects a
     * language, the login page texts are updated with the appropriate language bundle.
     * Third lambda expression: This method adds a listener to the username and password text fields. When the user presses
     * the enter key, the login button is clicked.
     *
     * @param url           The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the login page, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLoginPageLanguage();

        ObservableList<String> options = FXCollections.observableArrayList("English", "French");
        langSelector.setItems(options);
        langSelector.setOnAction(event -> {
            int selectedIndex = langSelector.getSelectionModel().getSelectedIndex();
            Locale locale = selectedIndex == 0 ? Locale.ENGLISH : Locale.FRENCH;
            Locale.setDefault(locale);
            System.out.println("Selected locale: " + locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundle/lang", locale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"), bundle);
            try {
                Node loginNode = loader.load();
                // Replace the existing login page with the newly loaded one
                rootPane.getChildren().setAll(loginNode);
                LoginStage.setTitle(bundle.getString("Title"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        langSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("this is locale now: " + Locale.getDefault());
                updateLoginPageLanguage();
            }
        });
        langSelector.setPromptText(Locale.getDefault().getDisplayLanguage());
        locationLabel.setText(timeZoneLbl);


/**
 * Adds an "Enter" key press event listener to the provided username and password text fields.
 * When the "Enter" key is pressed, the onActionLogIn method is called with an ActionEvent object
 * created using the logInButton as the source.
 * Uses lambda expressions to create the event handlers.
 *
 * @param logInUsernameTextField the text field for the username
 * @param logInPasswordTextField the text field for the password
 * @throws Exception if an error occurs while calling onActionLogIn method
 */
        Stream.of(logInUsernameTextField, logInPasswordTextField)
                .forEach(textField -> textField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        try {
                            onActionLogIn(new ActionEvent(logInButton, null));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }
}