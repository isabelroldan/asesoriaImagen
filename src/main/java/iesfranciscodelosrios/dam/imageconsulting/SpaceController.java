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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

    private static final Logger logger = LogManager.getLogger(SpaceController.class);

    /**
     * Handles the event when the "Intro" button is clicked.
     * Contains the logic for retrieving and displaying space information based on the entered space ID.
     */
    @FXML
    public void handleIntroButton() {
        try {
            int id_space = Integer.parseInt(idField.getText());
            Space space = spaceDAO.findById(id_space);
            if (space != null) {
                nameField.setText(space.getName());
                serviceTypeField.setText(space.getServiceType());
            } else {
                logger.error("No se encontró un space con ese ID");
                errorLabel.setText("No se encontró un space con ese ID");
            }
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al buscar space: " + e.getMessage());
        }
    }

    /**
     * Handles the event when the "Delete" button is clicked.
     * Contains the logic for deleting a space based on the entered space ID.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            spaceDAO.delete(new Space(id, null, null));
            errorLabel.setText("Space deleted successfully.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error deleting the space. Please delete the client's appointments before removing it.");
            logger.error("Error deleting space: " + e.getMessage(), e);
        }
    }

    @FXML
    private Button updateButton;

    /**
     * Handles the event when the "Update" button is clicked.
     * Contains the logic for updating a space with the entered values from the form.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        try {
            // Retrieve the values from the form
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String serviceType = serviceTypeField.getText();

            // Create a Space object with the retrieved values
            Space space = new Space(id, name, serviceType);

            // Update the space in the database
            SpaceDAO spaceDAO = new SpaceDAO();
            spaceDAO.update(space);

            // Display a success message
            errorLabel.setText("Space updated successfully");
        } catch (SQLException e) {
            // Display an error message if an exception occurs
            errorLabel.setText("Error updating space: " + e.getMessage());
            logger.error("Error updating space: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the event when the "Insert" button is clicked.
     * Contains the logic for inserting a new space with the entered values from the form.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void handleInsertButton(ActionEvent event) {
        SpaceDAO dao = new SpaceDAO();
        Space space = new Space();
        space.setId_space(Integer.parseInt(idField.getText()));
        space.setName(nameField.getText());
        space.setServiceType(serviceTypeField.getText());

        try {
            // Check if the ID already exists in the database
            boolean idExists = dao.checkIfIdExists(space.getId_space());

            // If the ID already exists, display an error message
            if (idExists) {
                String errorMessage = "";
                errorMessage += "ID already exists. ";
                errorLabel.setText(errorMessage);

                logger.error("ID already exists for space: " + space.getId_space());
            } else {
                dao.save(space);
                errorLabel.setText("Space inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting space: " + ex.getMessage());

            logger.error("Error inserting space: " + ex.getMessage(), ex);
        }
    }
}
