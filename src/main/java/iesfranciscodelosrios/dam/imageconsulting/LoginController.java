package iesfranciscodelosrios.dam.imageconsulting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import iesfranciscodelosrios.dam.model.domain.Professional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class LoginController {


    @FXML
    private Label labelUser;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;


    /**
     * Handles the validation of the user and password in the home button action.
     * It checks if the entered user and password are correct.
     */
    @FXML
    private void btnHomeValidate() { //Controlador de usuario y contraseña
        Connection conn = Connect.getConnect();
        ProfessionalDAO dao = new ProfessionalDAO(conn);

        boolean result = false;
        try {
            // Check if the entered user and password are valid
            result = dao.professionalLogin(userField.getText(), passField.getText());

            if (result == true) {
                // If the login is successful
                labelUser.setText("Correct user and password!");
                labelUser.setTextFill(Color.GREEN);
                // Retrieve the current professional from the DAO
                Professional currentProfessional = dao.getProfessional(userField.getText());
                // Set the current professional in the application
                App.setCurrentProfessional(currentProfessional);
                // Navigate to the menu screen
                App.setRoot("menu");
            } else {
                // If the login is unsuccessful
                labelUser.setText("Wrong username or password!");
                labelUser.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*--------------------------------------MENU OPTION-----------------------------------------*/
    /**
     * Handles the action of reading a client.
     * Navigates to the "readClient" screen.
     */
    @FXML
    void handleReadClient() {
        try {
            // Navigate to the "readClient" screen
            App.setRoot("readClient");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Handles the action of navigating to the "space" screen.
     */
    @FXML
    void handleSpace() {
        try {
            App.setRoot("space");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the action of navigating to the "professional" screen.
     */
    @FXML
    void handleProfessional() {
        try {
            App.setRoot("professional");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the action of navigating to the "insertAppointment" screen.
     */
    @FXML
    void handleInsertAppointment() {
        try {
            App.setRoot("insertAppointment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the action of navigating to the "appointment" screen.
     */
    @FXML
    void handleReadAppointment() {
        try {
            App.setRoot("appointment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the "Citas" button is clicked.
     * Contains the logic for handling the selection of citas.
     */
    @FXML
    private void handleCitasBtn(ActionEvent event) {
        System.out.println("Selected citas");
    }

    /**
     * Handles the event when the "Exit" button is clicked.
     * Contains the logic for handling the exit action.
     */
    @FXML
    private void handleExitClick(ActionEvent event) {
        // Lógica para manejar la selección de salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Está seguro de que desea salir?");
        alert.setContentText("Si selecciona 'Aceptar', la aplicación se cerrará.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}
