package pl.knap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.knap.database.dao.UsersDao;
import pl.knap.database.models.Users;
import pl.knap.utils.DialogUtils;
import pl.knap.utils.FxmlUtils;

import java.sql.SQLException;

public class ManageUsersController {
    private static final String ADD_USER_FXML = "/pl/knap/fxml/AddUser.fxml";
    private ObservableList<Users> allUsersAndAdminsInfo = FXCollections.observableArrayList();

    @FXML
    private TableView<Users> usersTableView;
    @FXML
    private TableColumn<Users, String> loginColumn;
    @FXML
    private TableColumn<Users, String> passwordColumn;
    @FXML
    private TableColumn<Users, String> typeColumn;
    @FXML
    private TableColumn<Users, Integer> idColumn;

    @FXML
    private void initialize() throws SQLException {
        TableViewCreator();
    }

    private void TableViewCreator() throws SQLException {
        UsersDao usersDao = new UsersDao();
        allUsersAndAdminsInfo = usersDao.allUsersAndAdminsInfo();
        usersTableView.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumnViewAndEdit(usersDao);
        passwordColumnViewAndEdit(usersDao);
        typeColumnViewAndEdit(usersDao);

        usersTableView.setItems(allUsersAndAdminsInfo);
    }

    private void typeColumnViewAndEdit(UsersDao usersDao) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Users, String> usersStringCellEditEvent) {
                Users user = usersStringCellEditEvent.getRowValue();
                user.setType(usersStringCellEditEvent.getNewValue());
                try {
                    usersDao.updateAdminType(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void passwordColumnViewAndEdit(UsersDao usersDao) {
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Users, String> usersStringCellEditEvent) {
                Users user = usersStringCellEditEvent.getRowValue();
                user.setPassword(usersStringCellEditEvent.getNewValue());
                try {
                    usersDao.updateAdminPassword(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginColumnViewAndEdit(UsersDao usersDao) {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Users, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Users, String> usersStringCellEditEvent) {
                Users user = usersStringCellEditEvent.getRowValue();
                String oldLogin = usersStringCellEditEvent.getOldValue();
                user.setLogin(usersStringCellEditEvent.getNewValue());
                try {
                    usersDao.updateAdminLogin(user, oldLogin);
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
            Users user = usersTableView.getSelectionModel().getSelectedItem();
            UsersDao usersDao = new UsersDao();
            if (usersDao.deleteUser(user)) {
                usersTableView.getItems().removeAll(usersTableView.getSelectionModel().getSelectedItem());
            }
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("noUserSelected"));
        }
    }
}
