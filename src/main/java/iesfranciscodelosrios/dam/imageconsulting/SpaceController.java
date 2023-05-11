package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SpaceController {
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField serviceTypeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button introButton;

    @FXML
    private Button deleteButton;

    private SpaceDAO spaceDAO;

    public SpaceController() {
        this.spaceDAO = new SpaceDAO();
    }

    @FXML
    public void handleIntroButton() {
        try {
            int id_space = Integer.parseInt(idField.getText());
            Space space = spaceDAO.findById(id_space);
            if (space != null) {
                nameField.setText(space.getName());
                serviceTypeField.setText(space.getServiceType());
            } else {
                errorLabel.setText("No se encontró un space con ese ID");
            }
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al buscar space: " + e.getMessage());
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            spaceDAO.delete(new Space(id, null, null));
            errorLabel.setText("Space eliminado correctamente.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al eliminar el space. Debe eliminar las citas del cliente antes de borrarlo.");
        }
    }

    @FXML
    private Button updateButton;

    @FXML
    private void handleUpdateButton(ActionEvent event) {
        try {
            // Recupera los valores del formulario
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String serviceType = serviceTypeField.getText();

            // Crea un objeto Space con los valores recuperados
            Space space = new Space(id, name, serviceType);

            // Actualiza el cliente en la base de datos
            SpaceDAO spaceDAO = new SpaceDAO();
            spaceDAO.update(space);

            // Muestra un mensaje de éxito
            errorLabel.setText("Space updated successfully");
        } catch (SQLException e) {
            // Muestra un mensaje de error si ocurre una excepción
            errorLabel.setText("Error updating space: " + e.getMessage());
        }
    }

    @FXML
    private void handleInsertButton(ActionEvent event) {
        SpaceDAO dao = new SpaceDAO();
        Space space = new Space();
        space.setId_space(Integer.parseInt(idField.getText()));
        space.setName(nameField.getText());
        space.setServiceType(serviceTypeField.getText());

        try {
            // Comprobar si el id o el email ya existen en la base de datos
            boolean idExists = dao.checkIfIdExists(space.getId_space());

            // Si el id ya existe, mostrar un mensaje de error
            if (idExists) {
                String errorMessage = "";
                errorMessage += "ID already exists. ";
                errorLabel.setText(errorMessage);
            } else {
                dao.save(space);
                errorLabel.setText("Space inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting space: " + ex.getMessage());
        }
    }
}
