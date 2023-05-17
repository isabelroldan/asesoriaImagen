package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.domain.Professional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App extends Application {
    private static Scene scene;
    private static Professional currentProfessional;

    /**
     * The start method is the entry point for JavaFX applications.
     * It is called when the application is launched.
     *
     * @param stage The primary stage for the application.
     * @throws IOException if there is an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Create a new Scene object with the FXML content loaded from "login.fxml",
        // and set its size to 640x480 pixels.
        scene = new Scene(loadFXML("login"), 640, 480);

        // Set the created scene as the scene for the primary stage.
        stage.setScene(scene);

        // Show the primary stage.
        stage.show();
    }

    /**
     * Sets the root of the current scene to a new content loaded from an FXML file.
     *
     * @param fxml The filename of the FXML file.
     * @throws IOException if there is an error loading the FXML file.
     */
    static void setRoot(String fxml) throws IOException {
        // Load the content of the FXML file and set it as the root of the current scene.
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file and returns the root element of the loaded FXML file as a Parent object.
     *
     * @param fxml The filename of the FXML file to load.
     * @return The root element of the loaded FXML file as a Parent object.
     * @throws IOException if there is an error loading the FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        // Create an FXMLLoader object and specify the location of the FXML file to load.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+ ".fxml"));

        // Load the FXML file and return the root element as a Parent object.
        return fxmlLoader.load();
    }

    public static void setCurrentProfessional(Professional professional) {
        currentProfessional = professional;
    }

    public static Professional getCurrentProfessional() {
        return currentProfessional;
    }

    private static final Logger logger = LogManager.getLogger(App.class);
    public static void main(String[] args) {
        launch();
        org.apache.logging.log4j.core.config.Configurator.initialize(null, "classpath:log4j2.xml");
    }
}