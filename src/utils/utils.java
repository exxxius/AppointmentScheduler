package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides utility methods for working with JavaFX controls. The methods are used to display alerts, confirmations,
 * and other dialogs.
 * @author Mehdi Rahimi
 */
public class utils {

    /**
     * Shows a custom alert dialog with the given title, header, content text and alert type.
     * @param title the title of the alert dialog.
     * @param header the header text of the alert dialog.
     * @param contentText the content text of the alert dialog.
     * @param alertType the alert type of the alert dialog.
     */
    public static void showAlert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        if (contentText != null && !contentText.isEmpty()) {
            alert.setContentText(contentText);
        }        alert.showAndWait();
    }

    /**
     * Shows an alert dialog with the specified title and content text.
     *
     * @param titleKey the key for the title in the resource bundle
     * @param headerTextKey the key for the header text in the resource bundle
     * @param contentText the content text to display in the alert dialog (optional)
     * @param alertType the type of alert dialog to display
     * @param bundle the resource bundle containing the title and header text
     */
    public static void showAlert(Alert.AlertType alertType, String titleKey, String headerTextKey, String contentText,
                                 ResourceBundle bundle) {
        Alert alert = new Alert(alertType);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerTextKey));
        if (contentText != null && !contentText.isEmpty()) {
            alert.setContentText(contentText);
        }
        alert.showAndWait();
    }

    /**
     * Show a confirmation alert with the given title, header text, and content text, and run the given onConfirm Runnable
     * if the user clicks the confirm button, and run the given onCancel Runnable if the user clicks the cancel button.
     *
     * @param title The title of the alert.
     * @param headerText The text to display in the header area of the dialog.
     * @param contentText The text to display in the alert.
     * @param onConfirm The code to run if the user clicks the "Confirm" button.
     * @param onCancel The action to be performed when the user clicks the cancel button.
     */
    public static void showConfirmationAlert(String title, String headerText, String contentText, Runnable onConfirm, Runnable onCancel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancelButton, confirmButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            if (onConfirm != null) {
                onConfirm.run();
            }
        } else {
            if (onCancel != null) {
                onCancel.run();
            }
        }
    }

    /**
     * It takes an ActionEvent, an fxml file, and a tab name, and switches to the scene defined by the fxml file, and
     * selects the tab with the given name
     *
     * @param event The event that triggered the switch.
     * @param fxml The name of the fxml file to switch to.
     * @param tabName The name of the tab to switch to.
     */
    public static void switchToScene(ActionEvent event, String fxml, String tabName) {
        try {
            FXMLLoader loader = new FXMLLoader(utils.class.getResource(fxml));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Find the appropriate Tab and set it as the selected Tab
            TabPane tabPane = (TabPane) parent.lookup("#tabPane");
            if (tabPane != null && tabName != null) {
                for (Tab tab : tabPane.getTabs()) {
                    if (tab.getText().equals(tabName)) {
                        tabPane.getSelectionModel().select(tab);
                        break;
                    }
                }
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}