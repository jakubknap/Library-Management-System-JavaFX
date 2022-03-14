package pl.knap.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static void addBookDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("addBook.header"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmationExitDialog() {
        Alert confirmationExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationExit.setTitle(bundle.getString("exitConfirmation.title"));
        confirmationExit.setHeaderText(bundle.getString("exitConfirmation.header"));
        return confirmationExit.showAndWait();
    }

    public static String errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));
        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
        return error;
    }

    public static Optional<ButtonType> confirmationLogoutDialog() {
        Alert confirmationExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationExit.setTitle(bundle.getString("logoutConfirmation.title"));
        confirmationExit.setHeaderText(bundle.getString("logoutConfirmation.header"));
        return confirmationExit.showAndWait();
    }

    public static Optional<ButtonType> confirmationDeleteUserDialog() {
        Alert confirmationExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationExit.setTitle(bundle.getString("confirmationDialog.title"));
        confirmationExit.setHeaderText(bundle.getString("deleteUserConfirmation.header"));
        return confirmationExit.showAndWait();
    }

    public static void addUserDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("addUser.header"));
        informationAlert.showAndWait();
    }

    public static void existingUserDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("existingUser.header"));
        informationAlert.showAndWait();
    }

    public static void existingBookDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("existingBook.header"));
        informationAlert.showAndWait();
    }

    public static void incorrectDataDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("incorrectData.header"));
        informationAlert.showAndWait();
    }

    public static void correctDataDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("correctData.header"));
        informationAlert.showAndWait();
    }

    public static void borrowBookDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("borrowBook.header"));
        informationAlert.showAndWait();
    }

    public static void successfullyUpdatedDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("sUpdate.header"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDeleteBookDialog() {
        Alert confirmationExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationExit.setTitle(bundle.getString("confirmationDialog.title"));
        confirmationExit.setHeaderText(bundle.getString("deleteBookConfirmation.header"));
        Optional<ButtonType> result = confirmationExit.showAndWait();
        return result;
    }

    public static void returnBookDialog() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("informationDialog.title"));
        informationAlert.setHeaderText(bundle.getString("returnBook.header"));
        informationAlert.showAndWait();
    }
}
