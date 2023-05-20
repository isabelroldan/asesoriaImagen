package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class ReadClientController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField colorTestResultField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button introButton;

    @FXML
    private Button deleteButton;

    private ClientDAO clientDAO;

    public ReadClientController() {
        this.clientDAO = new ClientDAO();
    }

    private static final Logger logger = LogManager.getLogger(ReadClientController.class);

    /**
     * Handles the event when the "Intro" button is clicked.
     * Contains the logic for retrieving and displaying client information based on the entered ID.
     *
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    public void handleIntroButton() {
        try {
            int id_client = Integer.parseInt(idField.getText());
            Client client = clientDAO.findById(id_client);
            if (client != null) {
                nameField.setText(client.getName());
                surnameField.setText(client.getSurname());
                telephoneField.setText(client.getTelephone());
                emailField.setText(client.getEmail());
                passwordField.setText(client.getPassword());
                ColorTestResult result = client.getColorTestResult();
                if (result != null) {
                    colorTestResultField.setText(result.toString());
                } else {
                    colorTestResultField.setText("No color result available for this client");
                }
            } else {
                errorLabel.setText("No client found with that ID");
            }
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error searching for client: " + e.getMessage());

            logger.error("Error searching for client: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the event when the "Delete" button is clicked.
     * Contains the logic for deleting a client based on the entered ID.
     *
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            clientDAO.delete(new Client(id, null, null, null, null, null, null));
            errorLabel.setText("Client deleted successfully.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error deleting the client. Please delete the client's appointments before removing it.");

            logger.error("Error deleting client: " + e.getMessage(), e);
        }
    }

    @FXML
    private Button updateButton;

    /**
     * Handles the event when the "Update" button is clicked.
     * Contains the logic for updating the client information based on the entered values in the form.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        try {
            // Retrieve values from the form
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String surname = surnameField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            ColorTestResult colorTestResult = ColorTestResult.valueOf(colorTestResultField.getText().toUpperCase());

            // Create a Client object with the retrieved values
            Client client = new Client(id, name, surname, telephone, email, password, colorTestResult);

            // Update the client in the database
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.update(client);

            // Display a success message
            errorLabel.setText("Client updated successfully");
        } catch (SQLException e) {
            // Display an error message if an exception occurs
            errorLabel.setText("Error updating client: " + e.getMessage());
            logger.error("Error updating client: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the event when the "Insert" button is clicked.
     * Contains the logic for inserting a new client based on the entered values in the form.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void handleInsertButton(ActionEvent event) {
        ClientDAO dao = new ClientDAO();
        Client client = new Client();
        client.setId_person(Integer.parseInt(idField.getText()));
        client.setName(nameField.getText());
        client.setSurname(surnameField.getText());
        client.setTelephone(telephoneField.getText());
        client.setEmail(emailField.getText());
        client.setPassword(passwordField.getText());
        client.setColorTestResult(ColorTestResult.valueOf(colorTestResultField.getText()));

        try {
            // Check if the ID or email already exist in the database
            boolean idExists = dao.checkIfIdExists(client.getId_person());
            boolean emailExists = dao.checkIfEmailExists(client.getEmail());

            // If the ID or email already exist, display an error message
            if (idExists || emailExists) {
                String errorMessage = "";
                if (idExists) {
                    errorMessage += "ID already exists. ";
                }
                if (emailExists) {
                    errorMessage += "Email already exists.";
                }
                errorLabel.setText(errorMessage);
            } else {
                dao.save(client);
                errorLabel.setText("Client inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting client: " + ex.getMessage());

            logger.error("Error inserting client: " + ex.getMessage(), ex);
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
