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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
import java.util.Optional;

public class AppointmentController2 {

    @FXML
    private TextField idAppointmentField;
    @FXML
    private DatePicker dateLabel;
    @FXML
    private ComboBox<LocalTime> startTimeLabel;
    @FXML
    private ComboBox<LocalTime> endTimeLabel;
    @FXML
    private ComboBox<Space> spaceLabel;
    @FXML
    private ComboBox<Client> clientLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button deleteButton;

    private final AppointmentDAO appointmentDAO = new AppointmentDAO(); // Instancia de tu clase AppointmentDAO

    private static final Logger logger = LogManager.getLogger(AppointmentController2.class);

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
                dateLabel.setValue(appointment.getDate());
                startTimeLabel.setValue(appointment.getStartTime());
                endTimeLabel.setValue(appointment.getEndTime());
                spaceLabel.setValue(appointment.getSpace());
                clientLabel.setValue(appointment.getClient());
                errorLabel.setText(""); // Limpiar el mensaje de error si existía alguno

                dateLabel.setDayCellFactory(datePicker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (!empty && date.equals(appointment.getDate())) {
                            setStyle("-fx-background-color: orange;");
                        }
                    }
                });


                // Get the list of spaces from the database and populate the spaceLabel ComboBox
                List<Space> spaces = new SpaceDAO().findAll(); // Replace spaceDAO with your actual DAO class
                spaceLabel.getItems().addAll(spaces);

                // Get the list of clients from the database and populate the clientLabel ComboBox
                List<Client> clients = new ClientDAO().findAll(); // Replace clientDAO with your actual DAO class

                //Only the email addresses of the clients
                for (Client client : clients) {
                    clientLabel.getItems().add(client);
                }
            } else {
                // Show an error message if the appointment was not found
                errorLabel.setText("Cita no encontrada");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            errorLabel.setText("Error de base de datos: " + e.getMessage());

            logger.error("Error retrieving appointment data", e);
        }
    }

    @FXML
    private void handleHoursAvailableButton(ActionEvent event) {
        // Get the selected space and chosen date
        Space selectedSpace = (Space) spaceLabel.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = dateLabel.getValue();

        if (selectedSpace != null && selectedDate != null) {
            try {
                // Get the occupied appointments for the selected space and date
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                List<Appointment> occupiedAppointments = appointmentDAO.findByDateAndSpace(selectedSpace.getId_space(), selectedDate);

                // Create a list of available times
                List<LocalTime> availableTimes = new ArrayList<>();

                // Define the start time and end time for the available schedules
                LocalTime startTime = LocalTime.of(10, 0);
                LocalTime endTime = LocalTime.of(20, 30);

                // Add the available times to the list
                while (startTime.isBefore(endTime) || startTime.equals(endTime)) {
                    final LocalTime currentTime = startTime; // Crear una variable final efectiva

                    // Check if the time slot is occupied
                    boolean isOccupied = false;
                    if (occupiedAppointments != null) {
                        isOccupied = occupiedAppointments.stream()
                                .anyMatch(appointment ->
                                        (currentTime.isAfter(appointment.getStartTime()) || currentTime.equals(appointment.getStartTime()))
                                                && currentTime.isBefore(appointment.getEndTime()));
                    }

                    if (!isOccupied) {
                        availableTimes.add(startTime);
                    }

                    // Add 30-minute interval
                    startTime = startTime.plusMinutes(30);
                }

                // Clear the start time ComboBox
                startTimeLabel.getItems().clear();

                // Add all the times to the start time ComboBox
                LocalTime currentTime = LocalTime.of(10, 0);
                while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
                    startTimeLabel.getItems().add(currentTime);
                    currentTime = currentTime.plusMinutes(30);
                }

                // Customize the appearance of items in the start time ComboBox
                startTimeLabel.setCellFactory(listView -> new ListCell<LocalTime>() {
                    @Override
                    protected void updateItem(LocalTime item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setDisable(false);
                        } else {
                            setText(item.toString());
                            boolean isOccupied = occupiedAppointments.stream()
                                    .anyMatch(appointment ->
                                            (item.isAfter(appointment.getStartTime()) || item.equals(appointment.getStartTime()))
                                                    && item.isBefore(appointment.getEndTime()));
                            setDisable(isOccupied);
                            setStyle("-fx-text-fill: " +
                                    (isDisable() ? "gray" : "black") + // Cambiar el color del texto según el estado de disponibilidad
                                    "; -fx-background-color: " +
                                    (isDisable() ? "lightgray" : "white")); // Cambiar el color de fondo según el estado de disponibilidad
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the start time ComboBox based on the selected time.
     */
    public void updateStartTimeComboBox() {
        LocalTime selectedTime = (LocalTime) startTimeLabel.getSelectionModel().getSelectedItem();
        updateEndTimeComboBox(selectedTime);
    }

    /**
     * Updates the end time ComboBox based on the selected start time.
     *
     * @param selectedTime The selected start time.
     */
    private void updateEndTimeComboBox(LocalTime selectedTime) {
        // Clear the end time ComboBox
        endTimeLabel.getItems().clear();

        // Check if a time is selected
        if (selectedTime != null) {
            // Get the selected space and date
            Space selectedSpace = (Space) spaceLabel.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = dateLabel.getValue();

            if (selectedSpace != null && selectedDate != null) {
                try {
                    // Get the occupied appointments for the selected space and date
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    List<Appointment> occupiedAppointments = appointmentDAO.findByDateAndSpace(selectedSpace.getId_space(), selectedDate);

                    // Get all the available and occupied times in the time range
                    List<LocalTime> allTimes = getAllTimes(selectedTime);
                    List<LocalTime> availableTimes = getAvailableTimes(selectedTime, occupiedAppointments);

                    // Customize the appearance of the items in the ComboBox
                    endTimeLabel.setCellFactory(listView -> new ListCell<LocalTime>() {
                        @Override
                        protected void updateItem(LocalTime item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item == null || empty) {
                                setText(null);
                                setDisable(false);
                                setStyle("-fx-background-color: white");
                            } else {
                                setText(item.toString());

                                if (availableTimes.contains(item)) {
                                    boolean isOccupied = occupiedAppointments.stream()
                                            .anyMatch(appointment ->
                                                    (item.isAfter(appointment.getStartTime()) || item.equals(appointment.getStartTime()))
                                                            && item.isBefore(appointment.getEndTime()));

                                    setDisable(isOccupied);
                                    setStyle("-fx-text-fill: " + (isDisable() ? "gray" : "black") +
                                            "; -fx-background-color: " + (isDisable() ? "lightgray" : "white"));
                                } else {
                                    setDisable(true);
                                    setStyle("-fx-background-color: lightgray");
                                }
                            }
                        }
                    });

                    // Add all the times to the ComboBox
                    endTimeLabel.getItems().addAll(allTimes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves all the times within the time range starting from the selected time, including the end time.
     *
     * @param selectedTime The selected time.
     * @return The list of all times.
     */
    private List<LocalTime> getAllTimes(LocalTime selectedTime) {
        List<LocalTime> allTimes = new ArrayList<>();

        LocalTime startTime = selectedTime.plusMinutes(30); // Ajuste: Agregar 30 minutos a la hora seleccionada
        LocalTime endTime = LocalTime.of(21, 0);

        while (startTime.isBefore(endTime) && !startTime.equals(endTime)) { // Ajuste: Excluir la última hora
            allTimes.add(startTime);
            startTime = startTime.plusMinutes(30);
        }

        return allTimes;
    }

    /**
     * Retrieves the available times within the selected time range, excluding the occupied appointments.
     *
     * @param selectedTime         The selected start time.
     * @param occupiedAppointments The list of occupied appointments.
     * @return The list of available times.
     */
    private List<LocalTime> getAvailableTimes(LocalTime selectedTime, List<Appointment> occupiedAppointments) {
        List<LocalTime> availableTimes = new ArrayList<>();

        LocalTime endTime = LocalTime.of(21, 0);
        LocalTime currentTime = selectedTime.plusMinutes(30); // Ajuste: Agregar 30 minutos

        while (currentTime.isBefore(endTime)) { // Ajuste: Comprobar si es menor que endTime
            final LocalTime time = currentTime;

            boolean isOccupied = occupiedAppointments.stream()
                    .anyMatch(appointment ->
                            (time.isAfter(appointment.getStartTime()) || time.equals(appointment.getStartTime()))
                                    && time.isBefore(appointment.getEndTime()));

            if (!isOccupied) {
                availableTimes.add(time);
            }

            currentTime = currentTime.plusMinutes(30);
        }

        return availableTimes;
    }

    @FXML
    private void updateAppointment() {
        // Get the entered appointment ID
        int appointmentId = Integer.parseInt(idAppointmentField.getText());

        try {
            // Use the findById method of AppointmentDAO to retrieve the appointment data
            Appointment appointment = appointmentDAO.findById(appointmentId);

            if (appointment != null) {
                // Update the appointment data based on the changes made in the UI
                /*appointment.setDate(dateLabel.getValue());
                appointment.setStartTime((LocalTime) startTimeLabel.getValue());
                appointment.setEndTime((LocalTime) endTimeLabel.getValue());
                appointment.setSpace((Space) spaceLabel.getSelectionModel().getSelectedItem());
                appointment.setClient((Client) clientLabel.getSelectionModel().getSelectedItem());*/

                // Parsing start time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime startTime = LocalTime.parse(startTimeLabel.getValue().toString(), formatter);
                appointment.setStartTime(startTime);

                // Parsing end time
                LocalTime endTime = LocalTime.parse(endTimeLabel.getValue().toString(), formatter);
                appointment.setEndTime(endTime);

                // Parsing date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(dateLabel.getValue().toString());

                // Converting date to LocalDate
                ZoneId zone = ZoneId.systemDefault();
                Instant instant = date.toInstant().atZone(zone).toInstant();
                LocalDate localDate = instant.atZone(zone).toLocalDate();
                appointment.setDate(localDate);

                // Retrieving the selected client by email
                Client selectedClient = clientLabel.getValue();
                String email = selectedClient.getEmail(); // Obtener el valor del campo de email
                ClientDAO clientDAO = new ClientDAO();
                Client client = null;

                try {
                    client = clientDAO.findByEmail(email); // Buscar el cliente por email en lugar de id
                } catch (SQLException e) {
                    errorLabel.setText("Cliente no encontrado en la base de datos. Crea primero una ficha.");
                    return; // Salir del método en caso de error
                }

                // Setting the client for the appointment
                appointment.setClient(client);


                // Retrieving the selected space
                Space selectedSpace = spaceLabel.getValue();
                SpaceDAO spaceDAO = new SpaceDAO();
                Space space = null;

                try {
                    space = spaceDAO.findById(selectedSpace.getId_space());
                } catch (SQLException e) {
                    errorLabel.setText("Space no encontrado. Asegúrese de que es correcto o créelo primero.");
                }

                // Setting the space for the appointment
                appointment.setSpace(space);

                // Call the update method of AppointmentDAO to update the appointment in the database
                appointmentDAO.update(appointment);
                errorLabel.setText("Update successfully");

                errorLabel.setText(""); // Clear the error message if it existed
            } else {
                // Show an error message if the appointment was not found
                errorLabel.setText("Cita no encontrada");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            errorLabel.setText("Error de base de datos: " + e.getMessage());
            logger.error("Error updating appointment", e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
                    dateLabel.setValue(null);

                    // Clear the fields after deleting the appointment
                    dateLabel.setValue(null);
                    startTimeLabel.setValue(null);
                    endTimeLabel.setValue(null);
                    spaceLabel.setValue(null);
                    clientLabel.setValue(null);
                    errorLabel.setText(""); // Clear the error message if any existed
                }
            } else {
                // Show an error message if the appointment was not found
                errorLabel.setText("Cita no encontrada");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            errorLabel.setText("Error de base de datos: " + e.getMessage());
            logger.error("Error deleting appointment", e);
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
