module pl.knap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pl.knap.libsma to javafx.fxml;
    exports pl.knap.libsma;
    exports pl.knap.libsma.controllers;
    opens pl.knap.libsma.controllers to javafx.fxml;
    exports pl.knap.libsma.database.models;
    opens pl.knap.libsma.database.models to javafx.fxml;

}
