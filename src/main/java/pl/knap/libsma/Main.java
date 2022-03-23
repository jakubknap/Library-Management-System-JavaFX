package pl.knap.libsma;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.knap.libsma.database.dbutils.DatabaseManager;
import pl.knap.libsma.utils.DialogUtils;
import pl.knap.libsma.utils.FxmlUtils;

public class Main extends Application {

    private static final String LOGIN_FORM_FXML = "/pl/knap/libsma/fxml/LoginForm.fxml";
    private static Scene scene;

    public static void setRoot(String fxml) {
        scene.setRoot(FxmlUtils.loadFXML(fxml));
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        if (DatabaseManager.createConnectionSource() != null) {
            scene = new Scene(FxmlUtils.loadFXML(LOGIN_FORM_FXML), 1000, 500);
            stage.setScene(scene);
            stage.setTitle(FxmlUtils.getResourceBundle().getString("title.app"));
            stage.setResizable(false);
            stage.show();
        } else {
            DialogUtils.errorDialog(FxmlUtils.getResourceBundle().getString("connFailed"));

        }
    }
}
