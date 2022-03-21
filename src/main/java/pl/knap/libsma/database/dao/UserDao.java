package pl.knap.libsma.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.knap.libsma.database.dbutils.DatabaseManager;
import pl.knap.libsma.database.models.User;
import pl.knap.libsma.utils.DialogUtils;

import java.sql.*;
import java.util.Optional;

public class UserDao {
    public void addUser(User user) throws SQLException {
        if (!(checkExistingUser(user))) {
            Connection connection = DatabaseManager.createConnectionSource();
            String query = " insert into users (login, password, type)" + " values (?,?,?);";
            PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);

            String login = user.getLogin();
            String password = user.getPassword();
            String type = user.getType();

            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, type);

            statement.executeUpdate();
            connection.close();
            DialogUtils.addUserDialog();
        } else {
            DialogUtils.existingUserDialog();
        }
    }

    private boolean checkExistingUser(User user) throws SQLException {
        Connection connection = DatabaseManager.createConnectionSource();
        String query = "SELECT login,type FROM users where login = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        String login = user.getLogin();

        statement.setString(1, login);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public ObservableList<User> getMembersInfo(String key) throws SQLException {
        ObservableList<User> userAndAdminInfoList = FXCollections.observableArrayList();
        ObservableList<User> userInfoList = FXCollections.observableArrayList();
        ObservableList<User> adminInfoList = FXCollections.observableArrayList();

        Connection connection = DatabaseManager.getConnection();
        Statement statement = DatabaseManager.getStatement(connection);

        switch (key) {
            case "all" -> {
                String query = "SELECT * FROM users";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    String type = rs.getString("type");
                    Integer id = rs.getInt("UserID");
                    User user = new User(login, password, type, id);
                    userAndAdminInfoList.add(user);
                }
                return userAndAdminInfoList;
            }
            case "users" -> {
                String query = "SELECT login,password FROM users WHERE type = \"user\"";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    User user = new User(login, password);
                    userInfoList.add(user);
                }
                return userInfoList;
            }
            case "admins" -> {
                String query = "SELECT login,password FROM users WHERE type = \"administrator\"";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    User admin = new User(login, password);
                    adminInfoList.add(admin);
                }
                return adminInfoList;
            }
        }
        connection.close();
        return userAndAdminInfoList;
    }

    public void updateData(String key, User user, String other) throws SQLException {
        switch (key) {
            case "login" -> {
                String newLogin = user.getLogin();
                DatabaseManager.executeUpdate("login", newLogin, other);
            }

            case "password" -> {
                String newPassword = user.getPassword();
                String login = user.getLogin();
                DatabaseManager.executeUpdate("password", newPassword, login);
            }

            case "type" -> {
                String newType = user.getType();
                String login = user.getLogin();
                DatabaseManager.executeUpdate("type", newType, login);
            }
        }
    }

    public boolean deleteUser(User user) throws SQLException {
        Connection connection = DatabaseManager.createConnectionSource();
        String query = "DELETE FROM users WHERE login =? and password = ? and type = ?;";
        PreparedStatement statement = DatabaseManager.getPreparedStatement(connection, query);
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();

        statement.setString(1, login);
        statement.setString(2, password);
        statement.setString(3, type);

        return confirmDialog(connection, statement);
    }

    private boolean confirmDialog(Connection connection, PreparedStatement statement) throws SQLException {
        Optional<ButtonType> result = DialogUtils.confirmationDeleteUserDialog();
        if (result.get() == ButtonType.OK) {
            statement.executeUpdate();
            connection.close();
            return true;
        }
        return false;
    }
}
