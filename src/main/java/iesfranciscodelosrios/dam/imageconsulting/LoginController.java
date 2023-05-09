package iesfranciscodelosrios.dam.imageconsulting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.xml.bind.JAXBException;


public class LoginController {

    @FXML
    private Button btn_home;

    @FXML
    private Label labelUser;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private void btnHomeValidate() { //Controlador de usuario y contraseña
        Connection conn = Connect.getConnect();
        ProfessionalDAO dao = new ProfessionalDAO(conn);

        boolean result = false;
        try {
            result = dao.professionalLogin(userField.getText(), passField.getText());

            if (result == true) {
                labelUser.setText("Correct user and password!");
                labelUser.setTextFill(Color.GREEN);
                /*App.setRoot("");*/
            } else {
                labelUser.setText("Wrong username or password!");
                labelUser.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }





        /*if (userField.getText().equals("admin") && passField.getText().equals("admin")){
            labelUser.setText("Correct user and password!");
            labelUser.setTextFill(Color.GREEN);
            App.setRoot("primary");
        }else {
            if (userField.getText().equals("user") && passField.getText().equals("user")){
                App.setRoot("users");
            } else {
                labelUser.setText("Wrong username or password!");
                labelUser.setTextFill(Color.RED);
            }
        }*/
    }
}
