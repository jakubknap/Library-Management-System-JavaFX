package pl.knap.libsma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import pl.knap.libsma.database.dao.BookDao;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.SQLException;

public class ManageBooksController {
    private static final String ADD_BOOK_FXML = "/pl/knap/libsma/fxml/AddBook.fxml";
    private ObservableList<Book> allBooksInfo = FXCollections.observableArrayList();

    @FXML
    private TableView booksTableView;
    @FXML
    private TableColumn<Book, Integer> IdColumn;
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
    private TableColumn<Book, Integer> statusColumn;

    @FXML
    private TableColumn<Book, String> userIdBorrowedColumn;


    @FXML
    private void initialize() throws SQLException {
        TableViewCreator();
    }

    private void TableViewCreator() throws SQLException {
        BookDao bookDao = new BookDao();
        allBooksInfo = bookDao.getBooksInfo("allBooks");
        booksTableView.setEditable(true);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumnViewAndEdit(bookDao);
        authorNameColumnViewAndEdit(bookDao);
        authorSurnameColumnViewAndEdit(bookDao);
        categoryColumnViewAndEdit(bookDao);
        releaseDateColumnViewAndEdit(bookDao);
        isbnDateColumnViewAndEdit(bookDao);
        statusDateColumnViewAndEdit(bookDao);
        userNameDateColumnViewAndEdit(bookDao);

        booksTableView.setItems(allBooksInfo);
    }

    private void titleColumnViewAndEdit(BookDao bookDao) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setTitle(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateTitle", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorNameColumnViewAndEdit(BookDao bookDao) {
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        authorNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorName(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateAuthorName", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorSurnameColumnViewAndEdit(BookDao bookDao) {
        authorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("authorSurname"));
        authorSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorSurnameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorSurname(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateAuthorSurname", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void categoryColumnViewAndEdit(BookDao bookDao) {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setCategory(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateCategory", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void releaseDateColumnViewAndEdit(BookDao bookDao) {
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setReleaseDate(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateReleaseDate", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void isbnDateColumnViewAndEdit(BookDao bookDao) {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                String oldIsbn = bookStringCellEditEvent.getOldValue();
                book.setIsbn(bookStringCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateIsbn", book, oldIsbn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void statusDateColumnViewAndEdit(BookDao bookDao) {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        statusColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> bookIntegerCellEditEvent) {
                Book book = bookIntegerCellEditEvent.getRowValue();
                book.setStatus(bookIntegerCellEditEvent.getNewValue());
                try {
                    bookDao.updateData("updateStatus", book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userNameDateColumnViewAndEdit(BookDao bookDao) {
        userIdBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userIdBorrowedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void deleteBook() throws SQLException {
        try {
            Book book = (Book) booksTableView.getSelectionModel().getSelectedItem();
            BookDao bookDao = new BookDao();
            if (bookDao.deleteBook(book)) {
                booksTableView.getItems().removeAll(booksTableView.getSelectionModel().getSelectedItem());
            }
        } catch (NullPointerException e) {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("noBookSelected"));
        }
    }

    @FXML
    private void addNewBook() {
        GeneralControllersMethods.setRoot(ADD_BOOK_FXML);
    }

    @FXML
    private void backToMenu() {
        GeneralControllersMethods.backToAdminMenu();
    }
}
