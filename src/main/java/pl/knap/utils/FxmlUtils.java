package pl.knap.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pl.knap.NewMain;

import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlUtils {
    public static Parent loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader(NewMain.class.getResource(fxml));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("pl.knap.bundles.messages");
    }
}
