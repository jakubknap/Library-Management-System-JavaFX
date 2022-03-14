package pl.knap.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.knap.controllers.LoginFormController;
import pl.knap.database.dbutils.DbManager;
import pl.knap.database.models.Book;
import pl.knap.utils.DialogUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class BooksDao {
    public void addBook(Book book) throws SQLException {
        if (!(checkExistingBook(book))) {
            Connection connection = DbManager.createConnectionSource();
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

    private boolean checkExistingBook(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String isbn = book.getIsbn();
        String title = book.getTitle();
        String query = "SELECT isbn FROM books where isbn = " + "" + "\"" + isbn + "\"" + "and title = " + "\"" + title + "\"" + ";";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public void borrowBook(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
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


    private int getUserId(ResultSet rs) throws SQLException {
        int userID = 0;
        while (rs.next()) {
            userID = rs.getInt("userID");
        }
        return userID;
    }

    public ObservableList<Book> getAllBooksInfo() throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM books";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
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
            setUserName(book, userID);
            booksList.add(book);
        }
        connection.close();
        return booksList;
    }

    private void setUserName(Book book, int userID) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String query = "SELECT login FROM users INNER JOIN books ON users.userID = books.UserID where books.userID =" + userID;
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String userName = rs.getString("login");
            book.setUserName(userName);
        }
    }

    public ObservableList<Book> getAllBooksAvailableInfo() throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM books where status = 1";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
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
            booksList.add(book);
        }
        connection.close();
        return booksList;
    }

    public void updateTitle(Book book, String oldTitle) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newTitle = book.getTitle();
        String query = "UPDATE books set title = " + "\"" + newTitle + "\"" + " where title =" + "\"" + oldTitle + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateAuthorName(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newAuthorName = book.getAuthorName();
        String query = "UPDATE books set authorName = " + "\"" + newAuthorName + "\"" + " where isbn =" + "\"" + book.getIsbn() + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateAuthorSurname(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newAuthorSurname = book.getAuthorSurname();
        String query = "UPDATE books set authorSurname = " + "\"" + newAuthorSurname + "\"" + " where isbn =" + "\"" + book.getIsbn() + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateCategory(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newCategory = book.getCategory();
        String query = "UPDATE books set category = " + "\"" + newCategory + "\"" + " where isbn =" + "\"" + book.getIsbn() + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateReleaseDate(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newReleaseDate = book.getReleaseDate();
        String query = "UPDATE books set releaseDate = " + "\"" + newReleaseDate + "\"" + " where isbn =" + "\"" + book.getIsbn() + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateIsbn(Book book, String oldIsbn) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newIsbn = book.getIsbn();
        String query = "UPDATE books set isbn = " + "\"" + newIsbn + "\"" + " where isbn =" + "\"" + oldIsbn + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateStatus(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        int newStatus = book.getStatus();
        String query = "UPDATE books set status = " + newStatus + " where isbn =" + "\"" + book.getIsbn() + "\"" + ";";
        System.out.println(query);
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public ObservableList<Book> getAllBooksBorrowedInfo() throws SQLException {
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String login = LoginFormController.loginInfo.get(LoginFormController.loginInfo.size() - 1);
        String query = "SELECT userID from users WHERE login = " + "\"" + login + "\"";
        ResultSet rs = statement.executeQuery(query);
        int userID = getUserId(rs);
        query = "SELECT * FROM books where userID = " + "\"" + userID + "\" ";
        rs = statement.executeQuery(query);
        while (rs.next()) {
            int bookID = rs.getInt("bookID");
            String title = rs.getString("title");
            String authorName = rs.getString("authorName");
            String authorSurname = rs.getString("authorSurname");
            String category = rs.getString("category");
            String releaseDate = rs.getString("releaseDate");
            String isbn = rs.getString("isbn");
            int status = rs.getInt("status");
            userID = rs.getInt("userID");
            Book book = new Book(bookID, title, authorName, authorSurname, category, releaseDate, isbn, status, userID);
            booksList.add(book);
        }
        connection.close();
        return booksList;
    }

    public void returnBook(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String query = "UPDATE books set userID = null" + ", status = 1 where books.isbn = " + "\"" + book.getIsbn() + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.returnBookDialog();
    }

    public boolean deleteBook(Book book) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String isbn = book.getIsbn();
        String query = "DELETE FROM books WHERE isbn = " + "\"" + isbn + "\"" + ";";
        return confirmDialog(connection, statement, query);
    }

    private boolean confirmDialog(Connection connection, Statement statement, String query) throws SQLException {
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
}

