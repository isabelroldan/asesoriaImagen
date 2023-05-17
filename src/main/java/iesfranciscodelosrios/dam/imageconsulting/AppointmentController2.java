package iesfranciscodelosrios.dam.imageconsulting;
import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Optional;

public class AppointmentController2 {

    @FXML
    private TextField idAppointmentField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label spaceLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button deleteButton;

    private final AppointmentDAO appointmentDAO = new AppointmentDAO(); // Instancia de tu clase AppointmentDAO

    /**
     * Handles the action of the intro button.
     */
    @FXML
    private void handleIntroButtonAction() {
        // Get the entered appointment ID
        int appointmentId = Integer.parseInt(idAppointmentField.getText());

        try {
            // Use the findById method of AppointmentDAO to retrieve the appointment data
            Appointment appointment = appointmentDAO.findById(appointmentId);

            if (appointment != null) {
                // Fill the fields with the data of the found appointment
                dateLabel.setText(appointment.getDate().toString());
                startTimeLabel.setText(appointment.getStartTime().toString());
                endTimeLabel.setText(appointment.getEndTime().toString());
                spaceLabel.setText(appointment.getSpace().toString());
                clientLabel.setText(appointment.getClient().getEmail());
                errorLabel.setText(""); // Limpiar el mensaje de error si existía alguno
            } else {
                // Show an error message if the appointment was not found
                errorLabel.setText("Cita no encontrada");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            errorLabel.setText("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Handles the action of the delete button.
     */
    @FXML
    private void handleDeleteButton() {
        // Get the ID of the appointment to delete
        int appointmentId = Integer.parseInt(idAppointmentField.getText());

        try {
            // Use the findById method of AppointmentDAO to retrieve the appointment data
            Appointment appointment = appointmentDAO.findById(appointmentId);

            if (appointment != null) {
                // Show a confirmation dialog before performing the deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar borrado");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que deseas borrar la cita?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Perform the deletion of the appointment
                    appointmentDAO.delete(appointment);
                    // Clear the fields after deleting the appointment
                    dateLabel.setText("");
                    startTimeLabel.setText("");
                    endTimeLabel.setText("");
                    spaceLabel.setText("");
                    clientLabel.setText("");
                    errorLabel.setText(""); // Clear the error message if any existed
                }
            } else {
                // Show an error message if the appointment was not found
                errorLabel.setText("Cita no encontrada");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            errorLabel.setText("Error de base de datos: " + e.getMessage());
        }
    }
}
