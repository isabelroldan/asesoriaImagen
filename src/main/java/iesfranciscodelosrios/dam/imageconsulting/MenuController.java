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

    @FXML
    private Menu menuButton;

    @FXML
    private MenuItem readClient;

    @FXML
    void handleReadClient() {
        try {
            App.setRoot("readClient");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleInsertClient() {
        try {
            App.setRoot("readClient");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleUpdateClient() {
        try {
            App.setRoot("readClient");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleDeleteClient() {
        try {
            App.setRoot("readClient");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void initialize() {
        Professional professional = App.getCurrentProfessional();


        //Rellenar los campos correspondientes

        id_personLabel.setText(Integer.toString(professional.getId_person()));
        nameLabel.setText(professional.getName());
        surnameLabel.setText(professional.getSurname());
        telephoneLabel.setText((professional.getTelephone()));
        emailLabel.setText(professional.getEmail());
        passwordLabel.setText(professional.getPassword());
        dniLabel.setText(professional.getDni());
        nPersonnelLabel.setText(Integer.toString(professional.getnPersonnel()));
        nSocialSecurityLabel.setText(Integer.toString(professional.getnSocialSecurity()));
        spaceLabel.setText(professional.getSpace().getName());
    }


    @FXML
    private void handleClientesBtn(ActionEvent event) {
        // Lógica para manejar la selección de clientes
        System.out.println("Selected clientes");
    }

    @FXML
    private void handleProfesionalesBtn(ActionEvent event) {
        // Lógica para manejar la selección de profesionales
        System.out.println("Selected profesionales");
    }

    @FXML
    private void handleEspaciosBtn(ActionEvent event) {
        // Lógica para manejar la selección de espacios
        System.out.println("Selected espacios");
    }

    @FXML
    private void handleCitasBtn(ActionEvent event) {
        // Lógica para manejar la selección de citas
        System.out.println("Selected citas");
    }

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
