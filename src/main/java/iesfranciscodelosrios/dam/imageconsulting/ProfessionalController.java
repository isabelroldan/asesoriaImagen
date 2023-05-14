package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import iesfranciscodelosrios.dam.model.domain.Professional;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private TextField passwordField;

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
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            professionalDAO.delete(new Professional(id, null, null, null, null, null, null, 0, 0, null));
            errorLabel.setText("Professional eliminado correctamente.");
        } catch (NumberFormatException | SQLException e) {
            errorLabel.setText("Error al eliminar el professional. ...");
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
            String dni = dniField.getText();
            int nPersonnel = Integer.parseInt(nPersonnelField.getText());
            int nSocialSecurity = Integer.parseInt(nSocialSecurityField.getText());
            int id_space = Integer.parseInt(id_spaceField.getText());

            //Crea un objeto SpaceDAO y recupera el Space correspondiente al id_space
            SpaceDAO spaceDAO = new SpaceDAO();
            Space space = spaceDAO.findById(id_space);

            // Crea un objeto Professional con los valores recuperados
            Professional professional = new Professional(id, name, surname, telephone, email, password, dni, nPersonnel, nSocialSecurity, space);

            // Actualiza el professional en la base de datos
            ProfessionalDAO professionalDAO = new ProfessionalDAO();
            professionalDAO.update(professional);

            // Muestra un mensaje de éxito
            errorLabel.setText("Professional updated successfully");
        } catch (SQLException e) {
            // Muestra un mensaje de error si ocurre una excepción
            errorLabel.setText("Error updating professional: " + e.getMessage());
        }
    }

    @FXML
    private void handleInsertButton(ActionEvent event) throws SQLException {
        ProfessionalDAO dao = new ProfessionalDAO();
        Professional professional = new Professional();
        professional.setId_person(Integer.parseInt(idField.getText()));
        professional.setName(nameField.getText());
        professional.setSurname(surnameField.getText());
        professional.setTelephone(telephoneField.getText());
        professional.setEmail(emailField.getText());
        professional.setPassword(passwordField.getText());
        professional.setDni(dniField.getText());
        professional.setnPersonnel(Integer.parseInt(nPersonnelField.getText()));
        professional.setnSocialSecurity(Integer.parseInt(nSocialSecurityField.getText()));

        // Recupera el objeto Space correspondiente al id_space
        int idSpace = Integer.parseInt(id_spaceField.getText());
        SpaceDAO spaceDAO = new SpaceDAO();
        Space space = spaceDAO.findById(idSpace);

        professional.setSpace(space);

        try {
            // Comprobar si el id o el email ya existen en la base de datos
            boolean idExists = dao.checkIfIdExists(professional.getId_person());
            boolean emailExists = dao.checkIfEmailExists(professional.getEmail());

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
                dao.save(professional);
                errorLabel.setText("Professional inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting professional: " + ex.getMessage());
        }
    }
}
