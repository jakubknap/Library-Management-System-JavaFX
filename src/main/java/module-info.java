module pl.knap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pl.knap to javafx.fxml;
    exports pl.knap;
    exports pl.knap.controllers;
    opens pl.knap.controllers to javafx.fxml;
    exports pl.knap.database.models;
    opens pl.knap.database.models to javafx.fxml;
}
