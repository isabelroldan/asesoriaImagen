package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentController {
    @FXML
    private TextField idAppointmentField;

    @FXML
    private ComboBox endTimeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField idClientField;

    @FXML
    private TextField idSpaceField;

    @FXML
    private Button insertButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        initializeComboBox();
    }

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    private void initializeComboBox() {
        List<LocalTime> hoursList = new ArrayList<>();
        LocalTime time = LocalTime.of(10, 00);
        while (time.isBefore(LocalTime.of(20, 30))) {
            hoursList.add(time);
            time = time.plusMinutes(30);
        }
        startTimeComboBox.getItems().addAll(hoursList);
    }

    @FXML
    private void handleStartTimeComboBoxAction(ActionEvent event) {
        LocalTime selectedStartTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        updateEndTimeComboBox(selectedStartTime);
    }

    private void updateEndTimeComboBox(LocalTime selectedStartTime) {
        List<LocalTime> hoursList = new ArrayList<>();
        LocalTime time = selectedStartTime.plusMinutes(30); // Agregar 1 hora
        while (time.isBefore(LocalTime.of(20, 30))) { // Horario límite de 20:30
            hoursList.add(time);
            time = time.plusMinutes(30);
        }
        endTimeComboBox.getItems().setAll(hoursList);
    }

    /*startTimeComboBox.setOnAction(event -> {
        LocalTime selectedStartTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        updateEndTimeComboBox(selectedStartTime);
    });*/

    @FXML
    private void handleInsertAppointment(ActionEvent event) throws ParseException {
        AppointmentDAO dao = new AppointmentDAO();
        Appointment appointment = new Appointment();
        appointment.setId_appointment(Integer.parseInt(idAppointmentField.getText()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue().toString(), formatter);
        appointment.setStartTime(startTime);

        //Otra forma de hacer lo que he echo arriba
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue().toString(), formatter);
        appointment.setEndTime(endTime);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datePicker.getValue().toString());


        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant().atZone(zone).toInstant();
        LocalDate localDate = instant.atZone(zone).toLocalDate();

        appointment.setDate(localDate);

        // Recupera el objeto Client correspondiente al id_client
        int idClient = Integer.parseInt(idClientField.getText());
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        try {
            client = clientDAO.findById(idClient);
        } catch (SQLException e) {
            errorLabel.setText("Client no encontrado en la base de datos. Creele primero una ficha.");
        }

        appointment.setClient(client);


        // Recupera el objeto Space correspondiente al id_space
        int idSpace = Integer.parseInt(idSpaceField.getText());
        SpaceDAO spaceDAO = new SpaceDAO();
        Space space = null;
        try {
            space = spaceDAO.findById(idSpace);
        } catch (SQLException e) {
            errorLabel.setText("Space no encontrado. Asegurese de que es correcto o creelo primero.");
        }

        appointment.setSpace(space);

        try {
            // Comprobar si el id o el email ya existen en la base de datos
            boolean idExists = dao.checkIfIdExists(appointment.getId_appointment());

            // Si el id ya existe, mostrar un mensaje de error
            if (idExists) {
                String errorMessage = "";
                errorMessage += "ID already exists. ";
                errorLabel.setText(errorMessage);
            } else {
                dao.save(appointment);
                errorLabel.setText("Appointment inserted successfully");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting space: " + ex.getMessage());
        }
    }
}
