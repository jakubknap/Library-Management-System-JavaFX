package pl.knap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.knap.database.dao.UsersDao;
import pl.knap.database.models.Users;
import pl.knap.utils.DialogUtils;
import pl.knap.utils.FxmlUtils;

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
            String login = loginInput.getText();
            String password = passwordInput.getText();
            String type = typeConverter();
            Users user = new Users(login, password, type);
            UsersDao usersDao = new UsersDao();
            usersDao.addUser(user);
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("empty"));
        }


    }

    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToAdminMenu();
    }

    private String typeConverter() {
        String type = typeAccountComboBox.getValue().toString();
        switch (type) {
            case "Administrator":
                return "administrator";
            case "Użytkownik", "User":
                return "user";
            default:
                return null;
        }
    }
}
