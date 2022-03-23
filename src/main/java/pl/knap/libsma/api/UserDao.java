package pl.knap.libsma.api;

import javafx.collections.ObservableList;
import pl.knap.libsma.database.models.User;
import pl.knap.libsma.database.models.enums.Update;

import java.sql.SQLException;

public interface UserDao {
    void addUser(User user) throws SQLException;
    ObservableList<User> getMembersInfo(String key) throws SQLException;
    void updateData(Update key, User user, String other) throws SQLException;
    boolean deleteUser(User user) throws SQLException;

}
