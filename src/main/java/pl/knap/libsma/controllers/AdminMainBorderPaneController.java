package pl.knap.libsma.controllers;

import javafx.fxml.FXML;

public class AdminMainBorderPaneController {
    private static final String ADD_USER_FXML = "/pl/knap/libsma/fxml/AddUser.fxml";
    private static final String ADD_BOOK_FXML = "/pl/knap/libsma/fxml/AddBook.fxml";
    private static final String MANAGE_USERS_FXML = "/pl/knap/libsma/fxml/ManageUsers.fxml";
    private static final String MANAGE_BOOKS_FXML = "/pl/knap/libsma/fxml/ManageBooks.fxml";

    @FXML
    private void addUser() {
        GeneralControllersMethods.setRoot(ADD_USER_FXML);
    }

    @FXML
    private void ManageMembers() {
        GeneralControllersMethods.setRoot(MANAGE_USERS_FXML);
    }

    @FXML
    private void addBook() {
        GeneralControllersMethods.setRoot(ADD_BOOK_FXML);
    }

    @FXML
    private void ManageBooks() {
        GeneralControllersMethods.setRoot(MANAGE_BOOKS_FXML);
    }

    @FXML
    private void logout() {
        GeneralControllersMethods.logout();
    }

    @FXML
    private void exit() {
        GeneralControllersMethods.exit();
    }
}
