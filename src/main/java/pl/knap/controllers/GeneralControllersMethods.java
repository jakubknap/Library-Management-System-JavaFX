package pl.knap.controllers;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import pl.knap.Main;
import pl.knap.utils.DialogUtils;

import java.util.Optional;

public class GeneralControllersMethods {
    private static final String LOGIN_FORM_FXML = "/pl/knap/fxml/LoginForm.fxml";
    private static final String ADMIN_MAIN_BORDER_PANE_FXML = "/pl/knap/fxml/AdminMainBorderPane.fxml";
    private static final String USER_MAIN_BORDER_PANE_FXML = "/pl/knap/fxml/UserMainBorderPane.fxml";

    public static void setRoot(String path) {
        try {
            Main.setRoot(path);
        } catch (Exception e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    public static void backToAdminMenu() {
        try {
            Main.setRoot(ADMIN_MAIN_BORDER_PANE_FXML);
        } catch (Exception e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    public static void backToUserMenu() {
        try {
            Main.setRoot(USER_MAIN_BORDER_PANE_FXML);
        } catch (Exception e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    public static void logout() {
        Optional<ButtonType> result = DialogUtils.confirmationLogoutDialog();
        if (result.get() == ButtonType.OK) {
            try {
                Main.setRoot(LOGIN_FORM_FXML);
            } catch (Exception e) {
                DialogUtils.errorDialog(e.getMessage());
            }
        }
    }

    public static void exit() {
        Optional<ButtonType> result = DialogUtils.confirmationExitDialog();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }
}
