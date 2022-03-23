package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.knap.libsma.database.dao.UserDaoImpl;
import pl.knap.libsma.database.models.User;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.SQLException;

public class AddUserController {
    private final String administrator = FxmlUtils.getResourceBundle().getString("administrator");
    private final String user = FxmlUtils.getResourceBundle().getString("user");
    private final ObservableList<String> types = FXCollections.observableArrayList(administrator, user);

    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ComboBox typeAccountComboBox;

    @FXML
    private void initialize() {
        setComboBox();
    }

    private void setComboBox() {
        typeAccountComboBox.setItems(types);
    }

    @FXML
    private void addUser() throws SQLException {
        try {
            User user = createUser();
            UserDaoImpl userDaoImpl = new UserDaoImpl();
            userDaoImpl.addUser(user);
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("empty"));
        }


    }

    private User createUser() {
        String login = loginInput.getText();
        String password = passwordInput.getText();
        String type = typeConverter();
        User user = new User(login, password, type);
        return user;
    }

    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToAdminMenu();
    }

    private String typeConverter() {
        String type = typeAccountComboBox.getValue().toString();
        return switch (type) {
            case "Administrator" -> "administrator";
            case "Użytkownik", "User" -> "user";
            default -> null;
        };
    }
}
