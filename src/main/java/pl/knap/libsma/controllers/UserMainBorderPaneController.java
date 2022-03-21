package pl.knap.libsma.controllers;

import javafx.fxml.FXML;

public class UserMainBorderPaneController {

    private static final String BORROW_BOOK_FXML = "/pl/knap/libsma/fxml/BorrowBook.fxml";
    private static final String RETURN_BOOK_FXML = "/pl/knap/libsma/fxml/ReturnBook.fxml";

    @FXML
    private void returnBook() {
        GeneralControllersMethods.setRoot(RETURN_BOOK_FXML);
    }

    @FXML
    private void borrowBook() {
        GeneralControllersMethods.setRoot(BORROW_BOOK_FXML);
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
