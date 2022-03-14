package pl.knap.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import pl.knap.database.dao.BooksDao;
import pl.knap.database.models.Book;
import pl.knap.utils.DialogUtils;
import pl.knap.utils.FxmlUtils;

import java.sql.SQLException;

public class ManageBooksController {
    private static final String ADD_BOOK_FXML = "/pl/knap/fxml/AddBook.fxml";
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
        BooksDao booksDao = new BooksDao();
        allBooksInfo = booksDao.getAllBooksInfo();
        booksTableView.setEditable(true);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumnViewAndEdit(booksDao);
        authorNameColumnViewAndEdit(booksDao);
        authorSurnameColumnViewAndEdit(booksDao);
        categoryColumnViewAndEdit(booksDao);
        releaseDateColumnViewAndEdit(booksDao);
        isbnDateColumnViewAndEdit(booksDao);
        statusDateColumnViewAndEdit(booksDao);
        userNameDateColumnViewAndEdit(booksDao);

        booksTableView.setItems(allBooksInfo);
    }

    private void titleColumnViewAndEdit(BooksDao booksDao) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                String oldTitle = bookStringCellEditEvent.getOldValue();
                book.setTitle(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateTitle(book, oldTitle);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorNameColumnViewAndEdit(BooksDao booksDao) {
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        authorNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorName(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateAuthorName(book);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorSurnameColumnViewAndEdit(BooksDao booksDao) {
        authorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("authorSurname"));
        authorSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorSurnameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorSurname(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateAuthorSurname(book);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void categoryColumnViewAndEdit(BooksDao booksDao) {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setCategory(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateCategory(book);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void releaseDateColumnViewAndEdit(BooksDao booksDao) {
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setReleaseDate(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateReleaseDate(book);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void isbnDateColumnViewAndEdit(BooksDao booksDao) {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                String oldIsbn = bookStringCellEditEvent.getOldValue();
                book.setIsbn(bookStringCellEditEvent.getNewValue());
                try {
                    booksDao.updateIsbn(book, oldIsbn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void statusDateColumnViewAndEdit(BooksDao booksDao) {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        statusColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> bookIntegerCellEditEvent) {
                Book book = bookIntegerCellEditEvent.getRowValue();
                book.setStatus(bookIntegerCellEditEvent.getNewValue());
                try {
                    booksDao.updateStatus(book);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userNameDateColumnViewAndEdit(BooksDao booksDao) {
        userIdBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userIdBorrowedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void deleteBook() throws SQLException {
        try {
            Book book = (Book) booksTableView.getSelectionModel().getSelectedItem();
            BooksDao booksDao = new BooksDao();
            if (booksDao.deleteBook(book)) {
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
