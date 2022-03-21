package pl.knap.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {
    private static final ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static void addUserDialog() {
        informationAlert("addUser.header");
    }

    public static void addBookDialog() {
        informationAlert("addBook.header");
    }

    public static void returnBookDialog() {
        informationAlert("returnBook.header");
    }

    public static void successfullyUpdatedDialog() {
        informationAlert("sUpdate.header");
    }

    public static void existingUserDialog() {
        informationAlert("existingUser.header");
    }

    public static void existingBookDialog() {
        informationAlert("existingBook.header");
    }

    public static void incorrectDataDialog() {
        informationAlert("incorrectData.header");
    }

    public static void correctDataDialog() {
        informationAlert("correctData.header");
    }

    public static void borrowBookDialog() {
        informationAlert("borrowBook.header");
    }

    public static Optional<ButtonType> confirmationExitDialog() {
        return confirmationDialog("exitConfirmation.title", "exitConfirmation.header");
    }

    public static Optional<ButtonType> confirmationLogoutDialog() {
        return confirmationDialog("logoutConfirmation.title", "logoutConfirmation.header");
    }

    public static Optional<ButtonType> confirmationDeleteUserDialog() {
        return confirmationDialog("confirmationDialog.title", "deleteUserConfirmation.header");
    }

    public static Optional<ButtonType> confirmationDeleteBookDialog() {
        return confirmationDialog("confirmationDialog.title", "deleteBookConfirmation.header");
    }

    public static String errorDialog(String error) {
        return errorAlert(error, "error.title", "error.header");
    }

    private static String errorAlert(String error, String title, String header) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString(title));
        errorAlert.setHeaderText(bundle.getString(header));
        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
        return error;
    }


    private static Optional<ButtonType> confirmationDialog(String title, String header) {
        Alert confirmationExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationExit.setTitle(bundle.getString(title));
        confirmationExit.setHeaderText(bundle.getString(header));
        return confirmationExit.showAndWait();
    }

    private static void informationAlert(String header) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString(header));
        informationAlert.showAndWait();
    }
}
