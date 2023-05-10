package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.domain.Professional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static Professional currentProfessional;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+ ".fxml"));
        return fxmlLoader.load();
    }

    public static void setCurrentProfessional(Professional professional) {
        currentProfessional = professional;
    }

    public static Professional getCurrentProfessional() {
        return currentProfessional;
    }


    public static void main(String[] args) {
        launch();
    }
}