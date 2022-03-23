package pl.knap.libsma.api;

import javafx.collections.ObservableList;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.database.models.enums.BookType;
import pl.knap.libsma.database.models.enums.Update;

import java.sql.SQLException;

public interface BookDao {
    void addBook(Book book) throws SQLException;
    void borrowBook(Book book) throws SQLException;
    void returnBook(Book book) throws SQLException;
    boolean deleteBook(Book book) throws SQLException;
    ObservableList<Book> getBooksInfo(BookType key) throws SQLException;
    void updateData(Update key, Book book, String other) throws SQLException;

}
