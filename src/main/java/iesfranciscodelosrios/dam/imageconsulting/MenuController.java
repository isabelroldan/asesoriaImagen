package iesfranciscodelosrios.dam.imageconsulting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    private Label profileLabel;

    @FXML
    private void handleProfileClick(MouseEvent event) {
        // Lógica para manejar la selección del perfil
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Perfil");
        alert.setHeaderText(null);
        alert.setContentText("Ha seleccionado la opción 'Perfil'");
        alert.showAndWait();
    }

    @FXML
    private void handleExitClick(ActionEvent event) {
        // Lógica para manejar la selección de salir
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
