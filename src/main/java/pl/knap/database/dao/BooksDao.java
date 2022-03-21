package pl.knap.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.knap.controllers.LoginFormController;
import pl.knap.database.dbutils.DbManager;
import pl.knap.database.models.Book;
import pl.knap.utils.DialogUtils;

import java.sql.*;
import java.util.Optional;

public class BooksDao {
    public void addBook(Book book) throws SQLException {
        if (!(checkExistingBook(book))) {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String isbn = book.getIsbn();
            String authorName = book.getAuthorName();
            String authorSurname = book.getAuthorSurname();
            String category = book.getCategory();
            String title = book.getTitle();
            String releaseDate = book.getReleaseDate();

            String query = " insert into books (isbn, authorName, authorSurname, category, releaseDate, title)" + " values (" + "" + "\"" + isbn + "\"" + "," + "\"" + authorName + "\"" + "," + "\"" + authorSurname + "\"" + "," + "\"" + category + "\"" + "," + "\"" + releaseDate + "\"" + "," + "\"" + title + "\"" + ")";
            statement.executeUpdate(query);
            connection.close();
            DialogUtils.addBookDialog();
        } else {
            DialogUtils.existingBookDialog();
        }
    }

    public void borrowBook(Book book) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String login = LoginFormController.loginInfo.get(LoginFormController.loginInfo.size() - 1);
        String query = "SELECT userID from users WHERE login = " + "\"" + login + "\"";
        ResultSet rs = statement.executeQuery(query);
        int userID = getUserId(rs);
        String isbn = book.getIsbn();
        query = "UPDATE books set userID = " + userID + ", status = 0 where books.isbn = " + "\"" + isbn + "\"" + ";";
        statement.executeUpdate(query);
        query = "SELECT login FROM users INNER JOIN books ON users.userID = books.UserID where books.userID =" + userID;
        rs = statement.executeQuery(query);
        while (rs.next()) {
            String userName = rs.getString("login");
            book.setUserName(userName);
        }
        connection.close();
        DialogUtils.borrowBookDialog();
    }

    public void returnBook(Book book) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "UPDATE books set userID = null" + ", status = 1 where books.isbn = " + "\"" + book.getIsbn() + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.returnBookDialog();
    }

    public boolean deleteBook(Book book) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String isbn = book.getIsbn();
        String query = "DELETE FROM books WHERE isbn = " + "\"" + isbn + "\"" + ";";
        Optional<ButtonType> result = DialogUtils.confirmationDeleteBookDialog();
        if (result.get().equals(ButtonType.OK)) {
            statement.executeUpdate(query);
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public ObservableList<Book> getBooksInfo(String key) throws SQLException {
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        switch (key) {
            case "borrowed" -> {
                String login = LoginFormController.loginInfo.get(LoginFormController.loginInfo.size() - 1);
                int userID = getUserLoginByUserId(connection, login);
                ResultSet rs = getBorrowedBooksByUserId(connection, userID);
                while (rs.next()) {
                    Book book = getBook(rs);
                    booksList.add(book);
                }
                return booksList;
            }

            case "available" -> {
                String query = "SELECT * FROM books where status = 1";
                ResultSet rs = getResultSet(connection, query);
                while (rs.next()) {
                    Book book = getBook(rs);
                    booksList.add(book);
                }
                return booksList;
            }

            case "allBooks" -> {
                String query = "SELECT * FROM books";
                ResultSet rs = getResultSet(connection, query);
                while (rs.next()) {
                    Book book = getBook(rs);
                    setUserName(book, book.getUserID());
                    booksList.add(book);
                }
                return booksList;
            }
        }
        connection.close();
        return booksList;
    }

    private Connection getConnection() {
        Connection connection = DbManager.createConnectionSource();
        return connection;
    }

    private ResultSet getBorrowedBooksByUserId(Connection connection, int userID) throws SQLException {
        String query = "SELECT * FROM books where userID = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    private int getUserLoginByUserId(Connection connection, String login) throws SQLException {
        String query = "SELECT userID from users WHERE login = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();
        int userID = getUserId(rs);
        return userID;
    }

    private ResultSet getResultSet(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    private Book getBook(ResultSet rs) throws SQLException {
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

    public void updateTitle(Book book) throws SQLException {
        executeUpdate("title", book.getTitle(), book.getIsbn());
    }

    public void updateAuthorName(Book book) throws SQLException {
        executeUpdate("authorName", book.getAuthorName(), book.getIsbn());
    }

    public void updateAuthorSurname(Book book) throws SQLException {
        executeUpdate("authorSurname", book.getAuthorSurname(), book.getIsbn());
    }

    public void updateCategory(Book book) throws SQLException {
        executeUpdate("category", book.getCategory(), book.getIsbn());
    }

    public void updateReleaseDate(Book book) throws SQLException {
        executeUpdate("releaseDate", book.getReleaseDate(), book.getIsbn());
    }

    public void updateIsbn(Book book, String oldIsbn) throws SQLException {
        executeUpdate("isbn", book.getIsbn(), oldIsbn);
    }

    public void updateStatus(Book book) throws SQLException {
        executeUpdate("status", String.valueOf(book.getStatus()), book.getIsbn());
    }

    private void executeUpdate(String key, String value, String isbn) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        PreparedStatement statement = null;

        switch (key) {
            case "title" -> {
                String query = "UPDATE books set title = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "authorName" -> {
                String query = "UPDATE books set authorName = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "authorSurname" -> {
                String query = "UPDATE books set authorSurname = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "category" -> {
                String query = "UPDATE books set category = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "releaseDate" -> {
                String query = "UPDATE books set releaseDate = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "isbn" -> {
                String query = "UPDATE books set isbn = ? where isbn = ?;";
                statement = getPreparedStatement(value, isbn, connection, query);
            }

            case "status" -> {
                String query = "UPDATE books set status = ? where isbn = ?;";
                statement = getPreparedStatement(String.valueOf(Integer.parseInt(value)), isbn, connection, query);
            }

        }

        statement.executeUpdate();
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    private PreparedStatement getPreparedStatement(String value, String isbn, Connection connection, String query) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, value);
        statement.setString(2, isbn);
        return statement;
    }

    private boolean checkExistingBook(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        String query = "SELECT isbn FROM books where isbn = ? and title =?;";
        PreparedStatement statement = connection.prepareStatement(query);

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
        Connection connection = DbManager.createConnectionSource();
        String query = "SELECT login FROM users INNER JOIN books ON users.userID = books.UserID where books.userID =?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String userName = rs.getString("login");
            book.setUserName(userName);
        }
    }
}

