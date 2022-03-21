package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.knap.libsma.database.dao.UserDao;
import pl.knap.libsma.database.models.User;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.SQLException;

public class ManageUsersController {
    private static final String ADD_USER_FXML = "/pl/knap/libsma/fxml/AddUser.fxml";
    private ObservableList<User> allUserAndAdminsInfo = FXCollections.observableArrayList();

    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> typeColumn;
    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private void initialize() throws SQLException {
        TableViewCreator();
    }

    private void TableViewCreator() throws SQLException {
        UserDao userDao = new UserDao();
        allUserAndAdminsInfo = userDao.getMembersInfo("all");
        usersTableView.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumnViewAndEdit(userDao);
        passwordColumnViewAndEdit(userDao);
        typeColumnViewAndEdit(userDao);

        usersTableView.setItems(allUserAndAdminsInfo);
    }

    private void typeColumnViewAndEdit(UserDao userDao) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> usersStringCellEditEvent) {
                User user = usersStringCellEditEvent.getRowValue();
                user.setType(usersStringCellEditEvent.getNewValue());
                try {
                    userDao.updateData("type", user, null);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void passwordColumnViewAndEdit(UserDao userDao) {
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> usersStringCellEditEvent) {
                User user = usersStringCellEditEvent.getRowValue();
                user.setPassword(usersStringCellEditEvent.getNewValue());
                try {
                    userDao.updateData("password", user, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginColumnViewAndEdit(UserDao userDao) {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> usersStringCellEditEvent) {
                User user = usersStringCellEditEvent.getRowValue();
                String oldLogin = usersStringCellEditEvent.getOldValue();
                user.setLogin(usersStringCellEditEvent.getNewValue());
                try {
                    userDao.updateData("login", user, oldLogin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToAdminMenu();
    }

    @FXML
    private void addUser() {
        GeneralControllersMethods.setRoot(ADD_USER_FXML);
    }

    @FXML
    private void deleteUser() throws SQLException {
        try {
            User user = usersTableView.getSelectionModel().getSelectedItem();
            UserDao userDao = new UserDao();
            if (userDao.deleteUser(user)) {
                usersTableView.getItems().removeAll(usersTableView.getSelectionModel().getSelectedItem());
            }
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("noUserSelected"));
        }
    }
}
