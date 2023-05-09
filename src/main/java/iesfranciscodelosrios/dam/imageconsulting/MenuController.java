package iesfranciscodelosrios.dam.imageconsulting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MenuController {

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
