package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.domain.Professional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuController {
    @FXML
    private Label id_personLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label dniLabel;

    @FXML
    private Label nPersonnelLabel;

    @FXML
    private Label nSocialSecurityLabel;

    @FXML
    private Label spaceLabel;


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
     * Handles the event when the "Appointment Client" button is clicked.
     * Navigates to the "clientAppointment" view.
     * @throws IOException if an I/O error occurs during the navigation.
     */
    @FXML
    void handleAppointmentClient() {
        try {
            App.setRoot("clientAppointment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initializes the controller after its root element has been completely processed.
     * Retrieves the current professional from the application context and populates the corresponding fields.
     */
    public void initialize() {
        Professional professional = App.getCurrentProfessional();

        // Populate the corresponding fields
        id_personLabel.setText(Integer.toString(professional.getId_person()));
        nameLabel.setText(professional.getName());
        surnameLabel.setText(professional.getSurname());
        telephoneLabel.setText((professional.getTelephone()));
        emailLabel.setText(professional.getEmail());
        /*passwordLabel.setText(professional.getPassword());*/
        dniLabel.setText(professional.getDni());
        nPersonnelLabel.setText(Integer.toString(professional.getnPersonnel()));
        nSocialSecurityLabel.setText(Integer.toString(professional.getnSocialSecurity()));
        spaceLabel.setText(professional.getSpace().getName());
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
