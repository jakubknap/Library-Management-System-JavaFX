package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.knap.libsma.database.dao.UserDao;
import pl.knap.libsma.database.models.User;
import pl.knap.libsma.utils.DialogUtils;

import java.sql.SQLException;

public class LoginFormController {

    private static final String ADMIN_MAIN_BORDER_PANE_FXML = "/pl/knap/libsma/fxml/AdminMainBorderPane.fxml";
    private static final String USER_MAIN_BORDER_PANE_FXML = "/pl/knap/libsma/fxml/UserMainBorderPane.fxml";
    public static ObservableList<String> loginInfo = FXCollections.observableArrayList();
    private ObservableList<User> allAdminsInfo = FXCollections.observableArrayList();
    private ObservableList<User> allUserInfo = FXCollections.observableArrayList();
    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void initialize() {
        try {
            UserDao userDao = new UserDao();
            allAdminsInfo = userDao.getMembersInfo("admins");
            allUserInfo = userDao.getMembersInfo("users");
        } catch (SQLException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }


    @FXML
    private void login() {
        String login = loginInput.getText();
        loginInfo.add(login);

        String password = passwordInput.getText();

        if (checkAdmin(login, password) || checkUser(login, password)) {
            DialogUtils.correctDataDialog();
        } else {
            DialogUtils.incorrectDataDialog();
        }
    }

    @FXML
    private void exit() {
        GeneralControllersMethods.exit();
    }

    private boolean checkAdmin(String login, String password) {
        for (User user : allAdminsInfo) {
            if (checkLoginData(user, login, password)) {
                GeneralControllersMethods.setRoot(ADMIN_MAIN_BORDER_PANE_FXML);
                return true;
            }
        }
        return false;
    }

    private boolean checkUser(String login, String password) {
        for (User user : allUserInfo) {
            if (checkLoginData(user, login, password)) {
                GeneralControllersMethods.setRoot(USER_MAIN_BORDER_PANE_FXML);
                return true;
            }
        }
        return false;
    }

    private boolean checkLoginData(User user, String login, String password) {
        return user.getLogin().equals(login) && user.getPassword().equals(password);
    }
}
