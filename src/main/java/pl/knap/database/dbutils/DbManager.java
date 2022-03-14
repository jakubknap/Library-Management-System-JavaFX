package pl.knap.database.dbutils;

import pl.knap.utils.DialogUtils;
import pl.knap.utils.FxmlUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {

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
}
