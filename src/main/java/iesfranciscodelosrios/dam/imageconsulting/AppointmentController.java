package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentController {
    @FXML
    private ComboBox<Space> spaceComboBox;

    @FXML
    private Label idAppointmentLabel;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField idSpaceField;

    @FXML
    private Button insertButton;

    @FXML
    private Label errorLabel;

    @FXML
    private LocalDate selectedDate;

    @FXML
    private ComboBox<Client> clientComboBox;

    private ObservableList<Client> clientData;

    private ClientDAO clientDAO;

    /**
     * Initializes the controller and sets up the initial state of the UI components.
     */
    public void initialize() {
        try {
            // Get the last appointment ID from the database
            int lastAppointmentId = new AppointmentDAO().findLastId();
            // Auto-complete the field with the next available number
            idAppointmentLabel.setText(String.valueOf(lastAppointmentId + 1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // Retrieve data for all Spaces and add them to the ComboBox
            spaceComboBox.getItems().addAll(new SpaceDAO().findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create an instance of the date formatter used to display disabled dates
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd")
                .toFormatter();

        // Create a Callback that will be called for each cell in the DatePicker
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable cells for weekends and holidays
                        DayOfWeek day = DayOfWeek.from(item);
                        boolean weekend = day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
                        boolean holiday = isHoliday(item); // Define aquí tu lógica para identificar los festivos
                        setDisable(weekend || holiday);

                        // If the cell is disabled, set a style to indicate it
                        if (weekend || holiday) {
                            setStyle("-fx-background-color: #ffc0cb;");
                            setTooltip(new Tooltip("Día no disponible"));
                            setText(item.format(formatter));
                        }
                    }
                };
            }
        };

        // Set the dayCellFactory Callback to the dayCellFactory property of the DatePicker
        datePicker.setDayCellFactory(dayCellFactory);




        // Configure the CLIENT ComboBox
        clientComboBox.setConverter(new ClientStringConverter());
        clientComboBox.setCellFactory(TextFieldListCell.forListView(new ClientStringConverter()));

        // Load client data
        loadClientData();

    }

    /**
     * Checks if the given date is a holiday.
     *
     * @param date The date to check.
     * @return {@code true} if the date is a holiday, {@code false} otherwise.
     */
    private boolean isHoliday(LocalDate date) {
        // If it's Saturday or Sunday, it's a holiday
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Handles the action when a space is selected from the spaceComboBox.
     *
     * @param event The action event.
     */
    @FXML
    private void handleSpaceComboBoxAction(ActionEvent event) {
        Space selectedSpace = spaceComboBox.getSelectionModel().getSelectedItem();
        if (selectedSpace != null) {
            int selectedSpaceId = selectedSpace.getId_space();
            String name = selectedSpace.getName();
            String serviceType = selectedSpace.getServiceType();
        }
    }

    @FXML
    private Button hoursAvailable;

    /**
     * Handles the action when the hours available button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    private void handleHoursAvailableButton(ActionEvent event) {
        // Get the selected space and chosen date
        Space selectedSpace = spaceComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = datePicker.getValue();

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
                startTimeComboBox.getItems().clear();

                // Add all the times to the start time ComboBox
                LocalTime currentTime = LocalTime.of(10, 0);
                while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
                    startTimeComboBox.getItems().add(currentTime);
                    currentTime = currentTime.plusMinutes(30);
                }

                // Customize the appearance of items in the start time ComboBox
                startTimeComboBox.setCellFactory(listView -> new ListCell<LocalTime>() {
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
        LocalTime selectedTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        updateEndTimeComboBox(selectedTime);
    }

    /**
     * Updates the end time ComboBox based on the selected start time.
     *
     * @param selectedTime The selected start time.
     */
    private void updateEndTimeComboBox(LocalTime selectedTime) {
        // Clear the end time ComboBox
        endTimeComboBox.getItems().clear();

        // Check if a time is selected
        if (selectedTime != null) {
            // Get the selected space and date
            Space selectedSpace = spaceComboBox.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedSpace != null && selectedDate != null) {
                try {
                    // Get the occupied appointments for the selected space and date
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    List<Appointment> occupiedAppointments = appointmentDAO.findByDateAndSpace(selectedSpace.getId_space(), selectedDate);

                    // Get all the available and occupied times in the time range
                    List<LocalTime> allTimes = getAllTimes(selectedTime);
                    List<LocalTime> availableTimes = getAvailableTimes(selectedTime, occupiedAppointments);

                    // Customize the appearance of the items in the ComboBox
                    endTimeComboBox.setCellFactory(listView -> new ListCell<LocalTime>() {
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
                    endTimeComboBox.getItems().addAll(allTimes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
     * Loads client data from the data source and populates the client ComboBox.
     * Handles any SQLException that may occur.
     */
    private void loadClientData() {
        try {
            // Retrieve client data from the data source
            ClientDAO clientDAO = new ClientDAO();
            List<Client> clients = clientDAO.findAll();

            // Convert the list of clients into an ObservableList
            clientData = FXCollections.observableArrayList(clients);

            // Set the data to the client ComboBox
            clientComboBox.setItems(clientData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception in an appropriate way for your application
        }
    }

    /**
     * Custom StringConverter for Client objects.
     * Overrides the toString method to display a desired attribute, such as email.
     * Provides an empty implementation for the fromString method.
     */
    private class ClientStringConverter extends StringConverter<Client> {
        private final DefaultStringConverter defaultConverter = new DefaultStringConverter();

        @Override
        public String toString(Client client) {
            if (client == null) {
                return "";
            }

            // Display the desired attribute, email
            return client.getEmail() ;
        }

        @Override
        public Client fromString(String string) {
            // Implement the reverse conversion logic if necessary
            return null;
        }
    }

    /**
     * Handles the event of inserting an appointment.
     * @param event The event triggered by the insert appointment action.
     * @throws ParseException If there is an error in parsing the date or time.
     */
    @FXML
    private void handleInsertAppointment(ActionEvent event) throws ParseException {
        AppointmentDAO dao = new AppointmentDAO();
        Appointment appointment = new Appointment();
        appointment.setId_appointment(Integer.parseInt(idAppointmentLabel.getText()));

        // Parsing start time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue().toString(), formatter);
        appointment.setStartTime(startTime);

        // Parsing end time
        LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue().toString(), formatter);
        appointment.setEndTime(endTime);

        // Parsing date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datePicker.getValue().toString());

        // Converting date to LocalDate
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant().atZone(zone).toInstant();
        LocalDate localDate = instant.atZone(zone).toLocalDate();
        appointment.setDate(localDate);

        // Retrieving the selected client by email
        Client selectedClient = clientComboBox.getValue();
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
        Space selectedSpace = spaceComboBox.getValue();
        SpaceDAO spaceDAO = new SpaceDAO();
        Space space = null;

        try {
            space = spaceDAO.findById(selectedSpace.getId_space());
        } catch (SQLException e) {
            errorLabel.setText("Space no encontrado. Asegúrese de que es correcto o créelo primero.");
        }

        // Setting the space for the appointment
        appointment.setSpace(space);

        try {
            // Checking if the appointment ID already exists
            boolean idExists = dao.checkIfIdExists(appointment.getId_appointment());

            // Displaying an error message if the ID already exists
            if (idExists) {
                String errorMessage = "";
                errorMessage += "ID already exists. ";
                errorLabel.setText(errorMessage);
            } else {
                dao.save(appointment);
                errorLabel.setText("Appointment inserted successfully");
                errorLabel.setStyle("-fx-text-fill: green;");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting space: " + ex.getMessage());
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
