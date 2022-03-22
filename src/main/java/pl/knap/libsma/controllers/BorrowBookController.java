package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.knap.libsma.database.dao.BookDao;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.SQLException;

public class BorrowBookController {

    private ObservableList<Book> allBooksInfo = FXCollections.observableArrayList();
    private final BookDao bookDao = new BookDao();

    @FXML
    private TableView<Book> borrowBookView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorNameColumn;
    @FXML
    private TableColumn<Book, String> authorSurnameColumn;
    @FXML
    private TableColumn<Book, String> categoryColumn;
    @FXML
    private TableColumn<Book, String> releaseDateColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;


    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToUserMenu();
    }

    @FXML
    private void borrowBook() throws SQLException {
        try {
            Book book = borrowBookView.getSelectionModel().getSelectedItem();
            bookDao.borrowBook(book);
            borrowBookView.getItems().removeAll(borrowBookView.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("noBookSelected"));
        }
    }

    @FXML
    private void initialize() throws SQLException {
        TableViewCreator();
    }

    private void TableViewCreator() throws SQLException {
        BookDao bookDao = new BookDao();
        allBooksInfo = bookDao.getBooksInfo("available");

        titleColumnView();
        authorNameColumnView();
        authorSurnameColumnView();
        categoryColumnView();
        releaseDateColumnView();
        isbnDateColumnView();

        borrowBookView.setItems(allBooksInfo);
    }

    private void titleColumnView() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void authorNameColumnView() {
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        authorNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void authorSurnameColumnView() {
        authorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("authorSurname"));
        authorSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void categoryColumnView() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void releaseDateColumnView() {
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void isbnDateColumnView() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}