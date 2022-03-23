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
import pl.knap.libsma.database.dao.BookDaoImpl;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.database.models.enums.BookType;
import pl.knap.libsma.database.models.enums.Update;
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
        BookDaoImpl bookDaoImpl = new BookDaoImpl();
        allBooksInfo = bookDaoImpl.getBooksInfo(BookType.ALL_BOOKS);
        booksTableView.setEditable(true);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumnViewAndEdit(bookDaoImpl);
        authorNameColumnViewAndEdit(bookDaoImpl);
        authorSurnameColumnViewAndEdit(bookDaoImpl);
        categoryColumnViewAndEdit(bookDaoImpl);
        releaseDateColumnViewAndEdit(bookDaoImpl);
        isbnDateColumnViewAndEdit(bookDaoImpl);
        statusDateColumnViewAndEdit(bookDaoImpl);
        userNameDateColumnViewAndEdit(bookDaoImpl);

        booksTableView.setItems(allBooksInfo);
    }

    private void titleColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setTitle(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.TITLE, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorNameColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        authorNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorName(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.AUTHOR_NAME, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void authorSurnameColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        authorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("authorSurname"));
        authorSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorSurnameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setAuthorSurname(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.AUTHOR_SURNAME, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void categoryColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setCategory(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.CATEGORY, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void releaseDateColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                book.setReleaseDate(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.RELEASE_DATE, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void isbnDateColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                Book book = bookStringCellEditEvent.getRowValue();
                String oldIsbn = bookStringCellEditEvent.getOldValue();
                book.setIsbn(bookStringCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.ISBN, book, oldIsbn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void statusDateColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        statusColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> bookIntegerCellEditEvent) {
                Book book = bookIntegerCellEditEvent.getRowValue();
                book.setStatus(bookIntegerCellEditEvent.getNewValue());
                try {
                    bookDaoImpl.updateData(Update.STATUS, book, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userNameDateColumnViewAndEdit(BookDaoImpl bookDaoImpl) {
        userIdBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userIdBorrowedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void deleteBook() throws SQLException {
        try {
            Book book = (Book) booksTableView.getSelectionModel().getSelectedItem();
            BookDaoImpl bookDaoImpl = new BookDaoImpl();
            if (bookDaoImpl.deleteBook(book)) {
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
