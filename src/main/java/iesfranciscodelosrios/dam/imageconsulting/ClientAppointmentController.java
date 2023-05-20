package iesfranciscodelosrios.dam.imageconsulting;

import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ClientAppointmentController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> surnameColumn;

    @FXML
    private TableColumn<Client, String> telephoneColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> colorTestResultColumn;

    @FXML
    private TableColumn<Appointment, LocalDate> dateColumn;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeColumn;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeColumn;

    @FXML
    private TableColumn<Appointment, Integer> spaceIdColumn;


    private AppointmentDAO appointmentDAO;
    private ClientDAO clientDAO;

    public ClientAppointmentController() {
        this.appointmentDAO = new AppointmentDAO();
        this.clientDAO = new ClientDAO();
    }

    @FXML
    private void initialize() {
        configureClientTable();
        configureAppointmentTable();
        showAllClients();

    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            showAllClients();
        } else {
            searchClientsByEmail(searchTerm);
        }
    }

    private void configureClientTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        colorTestResultColumn.setCellValueFactory(new PropertyValueFactory<>("colorTestResult"));
    }

    private void showAllClients() {
        try {
            List<Client> clients = clientDAO.findAll();
            ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
            clientTable.setItems(clientList);
        } catch (SQLException e) {
            e.printStackTrace();
            clientTable.setItems(FXCollections.emptyObservableList());
        }
    }

    private void searchClientsByEmail(String email) {
        try {
            List<Client> clients = clientDAO.findByEmailWrite(email);
            ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
            clientTable.setItems(clientList);
        } catch (SQLException e) {
            e.printStackTrace();
            clientTable.setItems(FXCollections.emptyObservableList());
        }
    }


    private Client selectedClient;

    private void loadAppointments() {
        if (selectedClient != null) {
            try {
                List<Appointment> appointments = appointmentDAO.findByClientId(selectedClient.getId_person());
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(appointments);
                appointmentTable.setItems(appointmentList);
            } catch (SQLException e) {
                e.printStackTrace();
                appointmentTable.setItems(FXCollections.emptyObservableList());
            }
        } else {
            appointmentTable.setItems(FXCollections.emptyObservableList());
        }
    }

    @FXML
    private void handleClientSelection(MouseEvent event) {
        if (event.getClickCount() == 2) {
            selectedClient = clientTable.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                loadAppointmentsForClient(selectedClient);
            }
        }
    }

    private void loadAppointmentsForClient(Client client) {
        try {
            List<Appointment> appointments = appointmentDAO.findByClientId(client.getId_person());
            // Limpia la tabla de citas
            appointmentTable.getItems().clear();
            // Agrega las citas del cliente a la tabla
            appointmentTable.getItems().addAll(appointments);
        } catch (SQLException e) {
            // Maneja la excepción
            e.printStackTrace();
            // O muestra un mensaje de error al usuario
        }
    }

    private void configureAppointmentTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        spaceIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSpace().getId_space()).asObject());
        spaceIdColumn.setCellFactory(column -> new TableCell<Appointment, Integer>() {
            @Override
            protected void updateItem(Integer spaceId, boolean empty) {
                super.updateItem(spaceId, empty);
                if (empty) {
                    setText(null);
                } else {
                    // Retrieve the space name using SpaceDAO
                    SpaceDAO spaceDAO = new SpaceDAO();
                    try {
                        Space space = spaceDAO.findById(spaceId);
                        setText(space.getName());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
