package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pl.knap.libsma.database.dao.BookDao;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AddBookController {

    private final String horror = FxmlUtils.getResourceBundle().getString("category.horror");
    private final String sf = FxmlUtils.getResourceBundle().getString("category.sf");
    private final String crime = FxmlUtils.getResourceBundle().getString("category.crime");
    private final String romance = FxmlUtils.getResourceBundle().getString("category.romance");

    private final ObservableList<String> categories = FXCollections.observableArrayList(horror, sf, crime, romance);

    @FXML
    private TextField titleInput;
    @FXML
    private TextField authorSurnameInput;
    @FXML
    private ComboBox categoryInput;
    @FXML
    private TextField isbnInput;
    @FXML
    private DatePicker releaseDateInput;
    @FXML
    private TextField authorNameInput;

    @FXML
    private void initialize() {
        setComboBox();
    }

    private void setComboBox() {
        categoryInput.setItems(categories);
    }

    @FXML
    private void addNewBook() throws SQLException {
        try {
            Book book = createBook();

            BookDao bookDao = new BookDao();
            bookDao.addBook(book);
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("empty"));
        }
    }

    private Book createBook() {
        String title = titleInput.getText();
        String authorName = authorNameInput.getText();
        String authorSurname = authorSurnameInput.getText();
        String category = categoryInput.getValue().toString();
        String isbn = isbnInput.getText();
        String releaseDate = releaseDateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Book book = new Book(authorName, authorSurname, category, title, releaseDate, isbn);
        return book;
    }

    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToAdminMenu();
    }
}
