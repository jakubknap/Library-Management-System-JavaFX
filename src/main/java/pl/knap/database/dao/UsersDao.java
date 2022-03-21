package pl.knap.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.knap.database.dbutils.DbManager;
import pl.knap.database.models.User;
import pl.knap.utils.DialogUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UsersDao {
    public ObservableList<User> allAdminsInfo() throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        ObservableList<User> adminInfoList = FXCollections.observableArrayList();
        String query = "SELECT login,password FROM users WHERE type = \"administrator\"";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String login = rs.getString("login");
            String password = rs.getString("password");
            User admin = new User(login, password);
            adminInfoList.add(admin);
        }
        connection.close();
        return adminInfoList;
    }

    public ObservableList<User> allUsersInfo() throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        ObservableList<User> userInfoList = FXCollections.observableArrayList();
        String query = "SELECT login,password FROM users WHERE type = \"user\"";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String login = rs.getString("login");
            String password = rs.getString("password");
            User user = new User(login, password);
            userInfoList.add(user);
        }
        connection.close();
        return userInfoList;
    }

    public void addUser(User user) throws SQLException {
        if (!(checkExistingUser(user))) {
            Connection connection = DbManager.createConnectionSource();
            Statement statement = connection.createStatement();
            String login = user.getLogin();
            String password = user.getPassword();
            String type = user.getType();
            String query = " insert into users (login, password, type)" + " values (" + "" + "\"" + login + "\"" + "," + "\"" + password + "\"" + "," + "\"" + type + "\"" + ")";
            statement.executeUpdate(query);
            connection.close();
            DialogUtils.addUserDialog();
        } else {
            DialogUtils.existingUserDialog();
        }
    }

    private boolean checkExistingUser(User user) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String login = user.getLogin();
        String type = user.getType();
        String query = "SELECT login,type FROM users where login = " + "" + "\"" + login + "\"" + " and type = " + "\"" + type + "\"" + ";";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public ObservableList<User> allUsersAndAdminsInfo() throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        ObservableList<User> userAndAdminInfoList = FXCollections.observableArrayList();
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
        connection.close();
        return userAndAdminInfoList;
    }

    public void updateAdminLogin(User user, String oldLogin) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newLogin = user.getLogin();
        String query = "UPDATE users set login = " + "\"" + newLogin + "\"" + " where login =" + "\"" + oldLogin + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateAdminPassword(User user) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newPassword = user.getPassword();
        String login = user.getLogin();
        String query = "UPDATE users set password = " + "\"" + newPassword + "\"" + " where login =" + "\"" + login + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public void updateAdminType(User user) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String newType = user.getType();
        String login = user.getLogin();
        String query = "UPDATE users set type = " + "\"" + newType + "\"" + " where login =" + "\"" + login + "\"" + ";";
        statement.executeUpdate(query);
        connection.close();
        DialogUtils.successfullyUpdatedDialog();
    }

    public boolean deleteUser(User user) throws SQLException {
        Connection connection = DbManager.createConnectionSource();
        Statement statement = connection.createStatement();
        String login = user.getLogin();
        String password = user.getPassword();
        String type = user.getType();
        String query = "DELETE FROM users WHERE login = " + "\"" + login + "\"" + " and password = " + "\"" + password + "\"" + " and type =" + "\"" + type + "\"" + ";";
        return confirmDialog(connection, statement, query);
    }

    private boolean confirmDialog(Connection connection, Statement statement, String query) throws SQLException {
        Optional<ButtonType> result = DialogUtils.confirmationDeleteUserDialog();
        if (result.get() == ButtonType.OK) {
            statement.executeUpdate(query);
            connection.close();
            return true;
        }
        return false;
    }


}
