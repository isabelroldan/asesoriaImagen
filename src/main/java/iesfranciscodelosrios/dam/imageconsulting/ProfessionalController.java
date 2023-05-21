package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.imageconsulting.utils.PasswordAuthentication;
import iesfranciscodelosrios.dam.imageconsulting.utils.ValidatorUtils;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import iesfranciscodelosrios.dam.model.domain.Professional;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class ProfessionalController {
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
    private TextField dniField;

    @FXML
    private TextField nPersonnelField;

    @FXML
    private TextField nSocialSecurityField;

    @FXML
    private TextField id_spaceField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button introButton;

    @FXML
    private Button deleteButton;

    private ProfessionalDAO professionalDAO;

    public ProfessionalController() {
        this.professionalDAO = new ProfessionalDAO();
    }

    private static final Logger logger = LogManager.getLogger(ProfessionalController.class);


    /**
     * Handles the event when the "Intro" button is clicked.
     * Contains the logic for retrieving and displaying professional data based on the entered ID.
     */
    @FXML
    public void handleIntroButton() {
        try {
            int id_professional = Integer.parseInt(idField.getText());
            Professional professional = professionalDAO.findById(id_professional);
            if (professional != null) {
                nameField.setText(professional.getName());
                surnameField.setText(professional.getSurname());
                telephoneField.setText(professional.getTelephone());
                emailField.setText(professional.getEmail());
                passwordField.setText(professional.getPassword());
                dniField.setText(professional.getDni());
                nPersonnelField.setText(String.valueOf(professional.getnPersonnel()));
                nSocialSecurityField.setText(String.valueOf(professional.getnSocialSecurity()));
                id_spaceField.setText(String.valueOf(professional.getSpace().getId_space()));
            } else {
                errorLabel.setText("No se encontró un professional con ese ID");
            }
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al buscar professional: " + e.getMessage());

            logger.error("Error al buscar professional: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the event when the "Delete" button is clicked.
     * Contains the logic for deleting a professional based on the entered ID.
     */
    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            professionalDAO.delete(new Professional(id, null, null, null, null, null, null, 0, 0, null));
            errorLabel.setText("Professional eliminado correctamente.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al eliminar el professional. ...");

            logger.error("Error al eliminar el professional: " + e.getMessage(), e);
        }
    }

    @FXML
    private Button updateButton;

    /**
     * Handles the event when the "Update" button is clicked.
     * Contains the logic for updating a professional based on the entered values in the form.
     */
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        try {
            // Retrieve the values from the form
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String surname = surnameField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String dni = dniField.getText();
            int nPersonnel = Integer.parseInt(nPersonnelField.getText());
            int nSocialSecurity = Integer.parseInt(nSocialSecurityField.getText());
            int id_space = Integer.parseInt(id_spaceField.getText());

            // Create a SpaceDAO object and retrieve the Space corresponding to the id_space
            SpaceDAO spaceDAO = new SpaceDAO();
            Space space = spaceDAO.findById(id_space);

            // Create a Professional object with the retrieved values
            Professional professional = new Professional(id, name, surname, telephone, email, password, dni, nPersonnel, nSocialSecurity, space);

            // Update the professional in the database
            ProfessionalDAO professionalDAO = new ProfessionalDAO();
            professionalDAO.update(professional);

            // Display a success message
            errorLabel.setText("Professional updated successfully");
        } catch (SQLException e) {
            // Display an error message if an exception occurs
            errorLabel.setText("Error updating professional: " + e.getMessage());

            logger.error("Error updating professional: " + e.getMessage(), e);
        }
    }


    /**
     * Handles the event when the "Insert" button is clicked.
     * Contains the logic for inserting a new professional based on the entered values in the form.
     *
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    private void handleInsertButton(ActionEvent event) throws SQLException {
        ProfessionalDAO dao = new ProfessionalDAO();
        Professional professional = new Professional();

        // Obtener los valores de los campos
        String idFieldText = idField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String dni = dniField.getText();
        String nPersonnelFieldText = nPersonnelField.getText();
        String nSocialSecurityFieldText = nSocialSecurityField.getText();
        String idSpaceFieldText = id_spaceField.getText();

        // Validar los campos usando los métodos de ValidatorUtils
        boolean isValid = true;
        String errorMessage = "";

        if (idFieldText.isEmpty()) {
            isValid = false;
            errorMessage += "ID is required. ";
        }

        if (name.isEmpty()) {
            isValid = false;
            errorMessage += "Name is required. ";
        }

        if (surname.isEmpty()) {
            isValid = false;
            errorMessage += "Surname is required. ";
        }

        if (!ValidatorUtils.isValidPhone(telephone)) {
            isValid = false;
            errorMessage += "Telephone is invalid. ";
        }

        if (!ValidatorUtils.isValidEmail(email)) {
            isValid = false;
            errorMessage += "Email is invalid. ";
        }

        if (password.isEmpty()) {
            isValid = false;
            errorMessage += "Password is required. ";
        }

        if (!ValidatorUtils.isValidDni(dni)) {
            isValid = false;
            errorMessage += "DNI is invalid. ";
        }

        if (nPersonnelFieldText.isEmpty()) {
            isValid = false;
            errorMessage += "nPersonnel is required. ";
        }

        /*if (!ValidatorUtils.isValidSocialSecurityNumber(nSocialSecurityFieldText)) {
            isValid = false;
            errorMessage += "nSocialSecurity is invalid. ";
        }*/

        if (idSpaceFieldText.isEmpty()) {
            isValid = false;
            errorMessage += "ID Space is required. ";
        }

        if (!isValid) {
            // Mostrar el mensaje de error en el errorLabel
            errorLabel.setText(errorMessage);
            return;
        }

        // Los campos son válidos, crear el objeto Professional y guardarlo en la base de datos
        professional.setId_person(Integer.parseInt(idFieldText));
        professional.setName(name);
        professional.setSurname(surname);
        professional.setTelephone(telephone);
        professional.setEmail(email);
        professional.setPassword(password);
        professional.setDni(dni);
        professional.setnPersonnel(Integer.parseInt(nPersonnelFieldText));
        professional.setnSocialSecurity(Integer.parseInt(nSocialSecurityFieldText));

        // Obtener el objeto Space correspondiente al id_space
        int idSpace = Integer.parseInt(idSpaceFieldText);
        SpaceDAO spaceDAO = new SpaceDAO();
        Space space = spaceDAO.findById(idSpace);

        professional.setSpace(space);
        try {
            // Check if the id or email already exist in the database
            boolean idExists = dao.checkIfIdExists(professional.getId_person());
            boolean emailExists = dao.checkIfEmailExists(professional.getEmail());

            // If the id or email already exist, display an error message
            if (idExists || emailExists) {
                errorMessage = "";
                if (idExists) {
                    errorMessage += "ID already exists. ";
                }
                if (emailExists) {
                    errorMessage += "Email already exists.";
                }
                errorLabel.setText(errorMessage);
            } else {
                dao.save(professional);
                errorLabel.setText("Professional inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting professional: " + ex.getMessage());

            logger.error("Error inserting professional: " + ex.getMessage(), ex);
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

    @FXML
    void handleAppointmentClient() {
        try {
            App.setRoot("clientAppointment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
