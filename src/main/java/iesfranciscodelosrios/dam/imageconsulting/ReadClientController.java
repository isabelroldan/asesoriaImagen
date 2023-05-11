package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private TextField passwordField;

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
                /*.setText(client.getColorTestResult().toString());*/
                ColorTestResult result = client.getColorTestResult();
                if (result != null) {
                    colorTestResultField.setText(result.toString());
                } else {
                    colorTestResultField.setText("No hay resultado de color para este cliente");
                }
            } else {
                errorLabel.setText("No se encontró un client con ese ID");
            }
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al buscar client: " + e.getMessage());
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            clientDAO.delete(new Client(id, null, null, null, null, null, null));
            errorLabel.setText("Cliente eliminado correctamente.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al eliminar el cliente. Debe eliminar las citas del cliente antes de borrarlo.");
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
            String surname = surnameField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            ColorTestResult colorTestResult = ColorTestResult.valueOf(colorTestResultField.getText().toUpperCase());

            // Crea un objeto Client con los valores recuperados
            Client client = new Client(id, name, surname, telephone, email, password, colorTestResult);

            // Actualiza el cliente en la base de datos
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.update(client);

            // Muestra un mensaje de éxito
            errorLabel.setText("Client updated successfully");
        } catch (SQLException e) {
            // Muestra un mensaje de error si ocurre una excepción
            errorLabel.setText("Error updating client: " + e.getMessage());
        }
    }

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
            // Comprobar si el id o el email ya existen en la base de datos
            boolean idExists = dao.checkIfIdExists(client.getId_person());
            boolean emailExists = dao.checkIfEmailExists(client.getEmail());

            // Si el id o el email ya existen, mostrar un mensaje de error
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
        }
    }
}
