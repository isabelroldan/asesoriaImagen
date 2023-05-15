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


    public void initialize() {
        try {
            // Obtener el último ID de cita de la base de datos
            int lastAppointmentId = new AppointmentDAO().findLastId();
            // Autocompletar el campo con el siguiente número disponible
            idAppointmentLabel.setText(String.valueOf(lastAppointmentId + 1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // Recuperar los datos de todos los Space y añadirlos al ComboBox
            spaceComboBox.getItems().addAll(new SpaceDAO().findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Crear una instancia del formateador de fecha que se utilizará para mostrar las fechas deshabilitadas
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd")
                .toFormatter();

        // Crear un Callback que se llamará para cada celda del DatePicker
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Deshabilitar las celdas para fines de semana y festivos
                        DayOfWeek day = DayOfWeek.from(item);
                        boolean weekend = day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
                        boolean holiday = isHoliday(item); // Define aquí tu lógica para identificar los festivos
                        setDisable(weekend || holiday);

                        // Si la celda está deshabilitada, establecer un estilo para mostrarlo
                        if (weekend || holiday) {
                            setStyle("-fx-background-color: #ffc0cb;");
                            setTooltip(new Tooltip("Día no disponible"));
                            setText(item.format(formatter));
                        }
                    }
                };
            }
        };

        // Establecer el Callback dayCellFactory en la propiedad dayCellFactory del DatePicker
        datePicker.setDayCellFactory(dayCellFactory);




        // Configurar el ComboBox CLIENT
        clientComboBox.setConverter(new ClientStringConverter());
        clientComboBox.setCellFactory(TextFieldListCell.forListView(new ClientStringConverter()));

        // Cargar los datos de los clientes
        loadClientData();

    }

    private boolean isHoliday(LocalDate date) {
        // Si es sábado o domingo, es festivo
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

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
    @FXML
    private void handleHoursAvailableButton(ActionEvent event) {
        // Obtener el espacio seleccionado y la fecha elegida
        Space selectedSpace = spaceComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedSpace != null && selectedDate != null) {
            try {
                // Obtener las horas ocupadas para el espacio y la fecha seleccionada
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                List<Appointment> occupiedAppointments = appointmentDAO.findByDateAndSpace(selectedSpace.getId_space(), selectedDate);

                // Crear una lista de horas disponibles
                List<LocalTime> availableTimes = new ArrayList<>();

                // Definir la hora de inicio y la hora de fin para los horarios disponibles
                LocalTime startTime = LocalTime.of(10, 0);
                LocalTime endTime = LocalTime.of(20, 30);

                // Agregar las horas disponibles a la lista
                while (startTime.isBefore(endTime) || startTime.equals(endTime)) {
                    final LocalTime currentTime = startTime; // Crear una variable final efectiva

                    // Verificar si la hora está ocupada
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

                    // Añadir intervalo de 30 minutos
                    startTime = startTime.plusMinutes(30);
                }

                // Limpiar ComboBox de horas de inicio
                startTimeComboBox.getItems().clear();

// Agregar todas las horas al ComboBox de horas de inicio
                LocalTime currentTime = LocalTime.of(10, 0);
                while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
                    startTimeComboBox.getItems().add(currentTime);
                    currentTime = currentTime.plusMinutes(30);
                }

// Personalizar la apariencia de los elementos en el ComboBox
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

    public void updateStartTimeComboBox() {
        LocalTime selectedTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        updateEndTimeComboBox(selectedTime);
    }

    private void updateEndTimeComboBox(LocalTime selectedTime) {
        // Limpiar ComboBox de horas de fin
        endTimeComboBox.getItems().clear();

        // Verificar si se seleccionó una hora
        if (selectedTime != null) {
            // Obtener el espacio seleccionado y la fecha elegida
            Space selectedSpace = spaceComboBox.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedSpace != null && selectedDate != null) {
                try {
                    // Obtener las horas ocupadas para el espacio y la fecha seleccionada
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    List<Appointment> occupiedAppointments = appointmentDAO.findByDateAndSpace(selectedSpace.getId_space(), selectedDate);

                    // Obtener todas las horas disponibles y ocupadas en el rango de tiempo
                    List<LocalTime> allTimes = getAllTimes(selectedTime);
                    List<LocalTime> availableTimes = getAvailableTimes(selectedTime, occupiedAppointments);

                    // Personalizar la apariencia de los elementos en el ComboBox
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

                    // Agregar todas las horas al ComboBox
                    endTimeComboBox.getItems().addAll(allTimes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

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





    private void loadClientData() {
        try {
            // Obtener los datos de los clientes desde tu fuente de datos
            ClientDAO clientDAO = new ClientDAO();
            List<Client> clients = clientDAO.findAll();

            // Convertir la lista de clientes en una ObservableList
            clientData = FXCollections.observableArrayList(clients);

            // Asignar los datos al ComboBox
            clientComboBox.setItems(clientData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción de alguna manera apropiada para tu aplicación
        }
    }

    private class ClientStringConverter extends StringConverter<Client> {
        private final DefaultStringConverter defaultConverter = new DefaultStringConverter();

        @Override
        public String toString(Client client) {
            if (client == null) {
                return "";
            }

            // Mostrar el atributo deseado, por ejemplo, el correo electrónico
            return client.getEmail() ;
        }

        @Override
        public Client fromString(String string) {
            // Implementar la lógica de conversión inversa si es necesario
            return null;
        }
    }

    @FXML
    private void handleInsertAppointment(ActionEvent event) throws ParseException {
        AppointmentDAO dao = new AppointmentDAO();
        Appointment appointment = new Appointment();
        appointment.setId_appointment(Integer.parseInt(idAppointmentLabel.getText()));

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

// Resto del código para configurar los datos de la cita
        appointment.setClient(client);


        // Recupera el objeto Space correspondiente al id_space
        Space selectedSpace = spaceComboBox.getValue();
        SpaceDAO spaceDAO = new SpaceDAO();
        Space space = null;

        try {
            space = spaceDAO.findById(selectedSpace.getId_space());
        } catch (SQLException e) {
            errorLabel.setText("Space no encontrado. Asegúrese de que es correcto o créelo primero.");
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
                errorLabel.setStyle("-fx-text-fill: green;");
            }
        } catch (SQLException ex) {
            errorLabel.setText("Error inserting space: " + ex.getMessage());
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }




    /*// Limpiar ComboBox de horas de fin
        endTimeComboBox.getItems().clear();

        // Verificar si se seleccionó una hora
        if (selectedTime != null) {
            LocalTime endTimeValue = selectedTime.plusMinutes(30); // Agregar 30 minutos a la hora seleccionada

            // Agregar todas las horas al ComboBox de horas de fin
            while (endTimeValue.isBefore(LocalTime.of(21, 0)) || endTimeValue.equals(LocalTime.of(21, 0))) {
                endTimeComboBox.getItems().add(endTimeValue);
                endTimeValue = endTimeValue.plusMinutes(30);
            }
        }*/
 /*   private List<LocalTime> availableTimes = new ArrayList<>();e.printStackTrace();

    @FXML
    private void initialize() {
        initializeComboBox();
    }


    private void initializeComboBox() {
        LocalTime time = LocalTime.of(10, 00);
        while (time.isBefore(LocalTime.of(20, 30))) {
            availableTimes.add(time);
            time = time.plusMinutes(30);
        }
        startTimeComboBox.getItems().addAll(availableTimes);


    }**************/

    /*private void updateStartTimeComboBox() {
        // Obtener la fecha y la sala seleccionadas
        LocalDate selectedDate = datePicker.getValue();
        int selectedSpace = Integer.parseInt(idSpaceField.getText());

        // Obtener la lista de citas existentes para la fecha y la sala seleccionadas
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = null;
        try {
            appointments = appointmentDAO.findByDateAndSpace(selectedSpace, selectedDate);
        } catch (SQLException e) {
            errorLabel.setText("Error getting appointments: " + e.getMessage());
            return;
        }

        // Crear una lista de horas disponibles para la fecha y la sala seleccionadas
        List<LocalTime> availableTimes = new ArrayList<>();
        LocalTime time = LocalTime.of(10, 00);
        while (time.isBefore(LocalTime.of(20, 30))) {
            boolean available = true;
            for (Appointment appointment : appointments) {
                if (appointment.getStartTime().equals(time)) {
                    available = false;
                    break;
                }
            }
            if (available) {
                availableTimes.add(time);
            }
            time = time.plusMinutes(30);
        }

        // Actualizar la lista de horas disponibles en el primer ComboBox
        startTimeComboBox.getItems().setAll(availableTimes);
    }*/

   /* @FXML
    private void handleDatePickerAction(ActionEvent event) {
        updateAvailableTimes();
    }

    @FXML
    private void handleIdSpaceFieldAction(ActionEvent event) {
        updateAvailableTimes();
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

    private void updateAvailableTimes() {
        // Obtener la fecha y la sala seleccionadas
        LocalDate selectedDate = datePicker.getValue();
        int selectedSpace = Integer.parseInt(idSpaceField.getText());

        // Obtener la lista de citas existentes para la fecha y la sala seleccionadas
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = null;
        try {
            appointments = appointmentDAO.findByDateAndSpace(selectedSpace, selectedDate);
        } catch (SQLException e) {
            errorLabel.setText("Error getting appointments: " + e.getMessage());
            return;
        }

        // Crear una lista de horas disponibles para la fecha y la sala seleccionadas
        List<LocalTime> availableTimes = new ArrayList<>();
        LocalTime time = LocalTime.of(10, 00);
        while (time.isBefore(LocalTime.of(20, 30))) {
            boolean available = true;
            for (Appointment appointment : appointments) {
                if (appointment.getStartTime().equals(time)) {
                    available = false;
                    break;
                }
            }
            if (available) {
                availableTimes.add(time);
            }
            time = time.plusMinutes(30);
        }

        // Actualizar la lista de horas disponibles en el primer ComboBox
        startTimeComboBox.getItems().setAll(availableTimes);
    }

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
    }*/
}
