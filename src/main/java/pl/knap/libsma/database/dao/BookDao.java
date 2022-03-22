package pl.knap.libsma.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.knap.libsma.controllers.LoginFormController;
import pl.knap.libsma.database.dbutils.DatabaseManager;
import pl.knap.libsma.database.models.Book;
import pl.knap.libsma.utils.DialogUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BookDao {
    public void addBook(Book book) throws SQLException {
        if (!(checkExistingBook(book))) {
            Connection connection = DatabaseManager.getConnection();
            String query = " insert into books (isbn, authorName, authorSurname, category, releaseDate, title)" + " values (?,?,?,?,?,?)";
            PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);

            String isbn = book.getIsbn();
            String authorName = book.getAuthorName();
            String authorSurname = book.getAuthorSurname();
            String category = book.getCategory();
            String title = book.getTitle();
            String releaseDate = book.getReleaseDate();

            statement.setString(1, isbn);
            statement.setString(2, authorName);
            statement.setString(3, authorSurname);
            statement.setString(4, category);
            statement.setString(5, releaseDate);
            statement.setString(6, title);

            statement.executeUpdate();

            connection.close();

            DialogUtils.addBookDialog();
        } else {
            DialogUtils.existingBookDialog();
        }
    }

    public void borrowBook(Book book) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "SELECT userID from users WHERE login = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);

        String login = LoginFormController.loginInfo.get(LoginFormController.loginInfo.size() - 1);
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();

        int userID = getUserId(rs);
        String isbn = book.getIsbn();

        statement.close();

        query = "UPDATE books set userID = ?, status = 0 where books.isbn = ?;";
        statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setInt(1, userID);
        statement.setString(2, isbn);

        statement.executeUpdate();
        statement.close();

        query = "SELECT login FROM users INNER JOIN books ON users.userID = books.UserID where books.userID = ?;";
        statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setInt(1, userID);

        rs = statement.executeQuery();
        while (rs.next()) {
            String userName = rs.getString("login");
            book.setUserName(userName);
        }
        connection.close();

        DialogUtils.borrowBookDialog();
    }

    public void returnBook(Book book) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String isbn = book.getIsbn();
        String query = "UPDATE books set userID = null, status = 1 where books.isbn = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setString(1, isbn);

        statement.executeUpdate();

        connection.close();
        DialogUtils.returnBookDialog();
    }

    public boolean deleteBook(Book book) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "DELETE FROM books WHERE isbn = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        String isbn = book.getIsbn();
        statement.setString(1, isbn);

        Optional<ButtonType> result = DialogUtils.confirmationDeleteBookDialog();
        if (result.get().equals(ButtonType.OK)) {
            statement.executeUpdate();
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public ObservableList<Book> getBooksInfo(String key) throws SQLException {
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        Connection connection = DatabaseManager.getConnection();
        String login = LoginFormController.loginInfo.get(LoginFormController.loginInfo.size() - 1);
        switch (key) {
            case "borrowed" -> {
                int userID = getUserLoginByUserId(connection, login);
                ResultSet rs = getBorrowedBooksByUserId(connection, userID);
                while (rs.next()) {
                    Book book = createBook(rs);
                    booksList.add(book);
                }
                return booksList;
            }

            case "available" -> {
                String query = "SELECT * FROM books where status = 1";
                ResultSet rs = DatabaseManager.getResultSet(connection, query);
                while (rs.next()) {
                    Book book = createBook(rs);
                    booksList.add(book);
                }
                return booksList;
            }

            case "allBooks" -> {
                String query = "SELECT * FROM books";
                ResultSet rs = DatabaseManager.getResultSet(connection, query);
                while (rs.next()) {
                    Book book = createBook(rs);
                    setUserName(book, book.getUserID());
                    booksList.add(book);
                }
                return booksList;
            }
        }
        connection.close();
        return booksList;
    }

    public void updateData(String key, Book book, String other) throws SQLException {
        switch (key) {
            case "updateTitle" -> {
                DatabaseManager.executeUpdate("title", book.getTitle(), book.getIsbn());
            }

            case "updateAuthorName" -> {
                DatabaseManager.executeUpdate("authorName", book.getAuthorName(), book.getIsbn());
            }

            case "updateAuthorSurname" -> {
                DatabaseManager.executeUpdate("authorSurname", book.getAuthorSurname(), book.getIsbn());
            }

            case "updateCategory" -> {
                DatabaseManager.executeUpdate("category", book.getCategory(), book.getIsbn());
            }

            case "updateReleaseDate" -> {
                DatabaseManager.executeUpdate("releaseDate", book.getReleaseDate(), book.getIsbn());
            }

            case "updateIsbn" -> {
                DatabaseManager.executeUpdate("isbn", book.getIsbn(), other);
            }

            case "updateStatus" -> {
                DatabaseManager.executeUpdate("status", String.valueOf(book.getStatus()), book.getIsbn());
            }
        }
    }

    private boolean checkExistingBook(Book book) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "SELECT isbn FROM books where isbn = ? and title =?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);

        String isbn = book.getIsbn();
        String title = book.getTitle();

        statement.setString(1, isbn);
        statement.setString(2, title);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    private int getUserId(ResultSet rs) throws SQLException {
        int userID = 0;
        while (rs.next()) {
            userID = rs.getInt("userID");
        }
        return userID;
    }

    private void setUserName(Book book, int userID) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        String query = "SELECT login FROM users INNER JOIN books ON users.userID = books.UserID where books.userID =?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String userName = rs.getString("login");
            book.setUserName(userName);
        }
    }

    private ResultSet getBorrowedBooksByUserId(Connection connection, int userID) throws SQLException {
        String query = "SELECT * FROM books where userID = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setInt(1, userID);
        return statement.executeQuery();
    }

    private int getUserLoginByUserId(Connection connection, String login) throws SQLException {
        String query = "SELECT userID from users WHERE login = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();
        int userID = getUserId(rs);
        return userID;
    }

    private Book createBook(ResultSet rs) throws SQLException {
        int bookID = rs.getInt("bookID");
        String title = rs.getString("title");
        String authorName = rs.getString("authorName");
        String authorSurname = rs.getString("authorSurname");
        String category = rs.getString("category");
        String releaseDate = rs.getString("releaseDate");
        String isbn = rs.getString("isbn");
        int status = rs.getInt("status");
        int userID = rs.getInt("userID");
        Book book = new Book(bookID, title, authorName, authorSurname, category, releaseDate, isbn, status, userID);
        return book;
    }
}

