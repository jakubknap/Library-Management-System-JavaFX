package pl.knap.libsma.database.dbutils;

import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

import java.sql.*;

public class DatabaseManager {

    private static final String DATA_BASE_LOCATION = "jdbc:mysql://localhost/library";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";

    public static Connection createConnectionSource() {
        try {
            Connection conn = DriverManager.getConnection(DATA_BASE_LOCATION, LOGIN, PASSWORD);
            return conn;

        } catch (Exception e) {
            DialogUtils.errorDialog(DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("connFailed")));
            return null;
        }
    }

    public static Connection getConnection() {
        return DatabaseManager.createConnectionSource();
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public static PreparedStatement getPreparedStatementToUpdate(String value, String idBy, Connection connection, String query) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, value);
        statement.setString(2, idBy);
        return statement;
    }

    public static ResultSet getResultSet(Connection connection, String query) throws SQLException {
        Statement statement = getStatement(connection);
        return statement.executeQuery(query);
    }

    public static void executeUpdate(String key, String value, String idBy) throws SQLException {
        Connection connection = DatabaseManager.createConnectionSource();
        PreparedStatement statement = null;
        String query;
        switch (key) {
            // Books
            case "title" -> {
                query = "UPDATE books set title = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "authorName" -> {
                query = "UPDATE books set authorName = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "authorSurname" -> {
                query = "UPDATE books set authorSurname = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "category" -> {
                query = "UPDATE books set category = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "releaseDate" -> {
                query = "UPDATE books set releaseDate = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "isbn" -> {
                query = "UPDATE books set isbn = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "status" -> {
                query = "UPDATE books set status = ? where isbn = ?;";
                statement = getPreparedStatementToUpdate(String.valueOf(Integer.parseInt(value)), idBy, connection, query);
            }
            // Books ^
            //Users
            case "login" -> {
                query = "UPDATE users set login = ? where login = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "password" -> {
                query = "UPDATE users set password = ? where login = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

            case "type" -> {
                query = "UPDATE users set type = ? where login = ?;";
                statement = getPreparedStatementToUpdate(value, idBy, connection, query);
            }

        }
        statement.executeUpdate();
        connection.close();

        DialogUtils.successfullyUpdatedDialog();
    }
}
