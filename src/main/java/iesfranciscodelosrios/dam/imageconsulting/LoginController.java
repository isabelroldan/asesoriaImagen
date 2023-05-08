package iesfranciscodelosrios.dam.imageconsulting;

import java.io.IOException;
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

    @FXML
    private void btnHomeValidate() throws IOException { //Controlador de usuario y contraseña
        if (userField.getText().equals("admin") && passField.getText().equals("admin")){
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
        }
    }
}
