package Controller;

import utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * Scheduler - Scheduling software with simple reporting system. (C195 - Software II)
 * This class starts the app from the login page, and establishes the initial database connection.
 * <p><b>
 * Lambdas expressions can be found on lines 233 and 268 of Login.java, lines 141 and 266 of Appointments.java,
 * and on lines 163 and 170 of AppointmentsAdd.java.
 * </b></p>
 * After unzipping c195-Mehdi-Rahimi.zip, the Javadocs files can be found at /javadoc
 * @author Mehdi Rahimi
 */
public class Main extends Application {
    /**
     * This is the stage that is passed from the login page to the main page.
      */
    public static Stage LoginStage;

    /** This method starts the app from the login page.
     * @param stage The stage to be displayed.
     * @throws Exception If the FXML file cannot be found.
     */
    @Override
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        LoginStage = stage;
        stage.setTitle(myBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is the ResourceBundle that is used to translate the app into different languages.
     */
    ResourceBundle myBundle = ResourceBundle.getBundle("bundle/lang");

    /** This method establishes the initial database connection and launches the app.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        JDBC.startConnection();
        //Locale.setDefault(new Locale("FR"));
        launch(args);
        JDBC.closeConnection();
    }
}