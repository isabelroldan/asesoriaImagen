package iesfranciscodelosrios.dam.imageconsulting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import iesfranciscodelosrios.dam.model.domain.Professional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginController {

    @FXML
    private Button btn_home;

    @FXML
    private Label labelUser;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;


    /**
     * Handles the validation of the user and password in the home button action.
     * It checks if the entered user and password are correct.
     */
    @FXML
    private void btnHomeValidate() { //Controlador de usuario y contraseña
        Connection conn = Connect.getConnect();
        ProfessionalDAO dao = new ProfessionalDAO(conn);

        boolean result = false;
        try {
            // Check if the entered user and password are valid
            result = dao.professionalLogin(userField.getText(), passField.getText());

            if (result == true) {
                // If the login is successful
                labelUser.setText("Correct user and password!");
                labelUser.setTextFill(Color.GREEN);
                // Retrieve the current professional from the DAO
                Professional currentProfessional = dao.getProfessional(userField.getText());
                // Set the current professional in the application
                App.setCurrentProfessional(currentProfessional);
                // Navigate to the menu screen
                App.setRoot("menu");
            } else {
                // If the login is unsuccessful
                labelUser.setText("Wrong username or password!");
                labelUser.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
