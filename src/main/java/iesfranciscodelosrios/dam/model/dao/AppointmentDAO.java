package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;

import javax.xml.bind.JAXBException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements DAO<Appointment> {

    private final static  String FINDALL = "SELECT * FROM appointment";
    private final static  String FINDBYID = "SELECT * FROM appointment WHERE id_appointment = ?";
    private final static  String INSERT = "INSERT INTO appointment(id_appointment, startTime, endTime, date, id_client, id_space) VALUES(?, ?, ?, ?, ?, ?)";
    private final static  String UPDATE = "UPDATE appointment SET startTime = ?, endTime = ?, date = ?, id_client= ?, id_space = ? WHERE id_appointment = ?";
    private final static  String DELETE = "DELETE FROM appointment WHERE id_appointment = ?";

    private Connection conn;

    public AppointmentDAO(Connection conn) {
        this.conn = conn;
    }

    public AppointmentDAO() {
        this.conn = Connect.getConnect();
    }

    /**
     * Retrieves all appointments from the database.
     * @return A list of Appointment objects representing all appointments.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public List<Appointment> findAll() throws SQLException {
        // Create a new list to store the appointments
        List<Appointment> result = new ArrayList<>();

        // Prepare the SQL statement to retrieve all appointments
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            // Execute the SQL query and retrieve the results
            try(ResultSet res = pst.executeQuery()) {
                // Iterate over the result set
                while (res.next()) {
                    // Create a new Appointment object
                    Appointment appointment = new Appointment();

                    // Set the properties of the appointment using data from the result set
                    appointment.setId_appointment(res.getInt("id_appointment"));
                    appointment.setStartTime(res.getTime("startTime").toLocalTime());
                    appointment.setEndTime(res.getTime("endTime").toLocalTime());
                    appointment.setDate(res.getDate("date").toLocalDate());

                    // Retrieve the client associated with the appointment using ClientDAO
                    ClientDAO cdao = new ClientDAO(this.conn);
                    Client client = cdao.findById(res.getInt("id_client"));
                    appointment.setClient(client);

                    // Retrieve the space associated with the appointment using SpaceDAO
                    SpaceDAO adao = new SpaceDAO(this.conn);
                    Space space = adao.findById(res.getInt("id_space"));
                    appointment.setSpace(space);

                    // Add the appointment to the result list
                    result.add(appointment);
                }
            }
        }
        // Return the list of appointments
        return result;
    }

    /**
     * Retrieves an appointment from the database based on its ID.
     *
     * @param id_appointment The ID of the appointment to retrieve.
     * @return An Appointment object representing the found appointment, or null if not found.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Appointment findById(int id_appointment) throws SQLException {
        Appointment result = null;
        // Prepare the SQL statement to retrieve the appointment by its ID
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_appointment);
            // Execute the SQL query and retrieve the result
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    // Create a new Appointment object
                    Appointment appointment = new Appointment();
                    // Set the properties of the appointment using data from the result set
                    appointment.setId_appointment(res.getInt("id_appointment"));
                    appointment.setStartTime(res.getTime("startTime").toLocalTime());
                    appointment.setEndTime(res.getTime("endTime").toLocalTime());
                    appointment.setDate(res.getDate("date").toLocalDate());

                    // Retrieve the client associated with the appointment using ClientDAO
                    ClientDAO cdao = new ClientDAO(this.conn);
                    Client client = cdao.findById(res.getInt("id_client"));
                    appointment.setClient(client);

                    // Retrieve the space associated with the appointment using SpaceDAO
                    SpaceDAO sdao = new SpaceDAO(this.conn);
                    Space space = sdao.findById(res.getInt("id_space"));
                    appointment.setSpace(space);
                    result = appointment; // Set the found appointment as the result
                }
            }
        }
        return result;
    }

    /**
     * Checks if an appointment ID exists in the database.
     *
     * @param id The appointment ID to check.
     * @return true if the ID exists in the database, false otherwise.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public boolean checkIfIdExists(int id) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE id_appointment = ?";
        // Prepare the SQL statement to check if the ID exists
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            // Execute the SQL query and retrieve the result
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Return true if a result is found, indicating the ID exists
            }
        }
    }

    /**
     * Saves an Appointment entity to the database.
     *
     * @param entity The Appointment object to be saved.
     * @return The saved Appointment object.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Appointment save(Appointment entity) throws SQLException {
        Appointment result = new Appointment();
        if(entity != null) {
            Appointment appointment = findById(entity.getId_appointment());

            // Retrieve the client associated with the appointment using ClientDAO
            ClientDAO cdao = new ClientDAO(this.conn);
            Client myClient = cdao.findById(entity.getClient().getId_person());

            // Retrieve the space associated with the appointment using SpaceDAO
            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());

            if(appointment == null) {
                if(myClient == null & mySpace == null){
                    // Save the associated client and space if they do not exist in the database
                    cdao.save(entity.getClient());
                    sdao.save(entity.getSpace());
                }

                // Insert the appointment into the database
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId_appointment());
                    pst.setTime(2, Time.valueOf(entity.getStartTime()));
                    pst.setTime(3, Time.valueOf(entity.getEndTime()));
                    pst.setDate(4, Date.valueOf(entity.getDate()));
                    pst.setInt(5, entity.getClient().getId_person());
                    pst.setInt(6, entity.getSpace().getId_space());
                    pst.executeUpdate();
                }


            }
            result = entity;
        }
        return result;
    }

    /**
     * Updates an existing Appointment entity in the database.
     *
     * @param entity The updated Appointment object.
     * @return The updated Appointment object.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Appointment update(Appointment entity) throws SQLException {
        Appointment result = new Appointment();
        if(entity != null) {
            Appointment appointment = findById(entity.getId_appointment());

            // Retrieve the client associated with the appointment using ClientDAO
            ClientDAO cdao = new ClientDAO(this.conn);
            Client myClient = cdao.findById(entity.getClient().getId_person());

            // Retrieve the space associated with the appointment using SpaceDAO
            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());

            if(appointment != null) {
                if(myClient == null & mySpace == null){
                    // Save the associated client and space if they do not exist in the database
                    cdao.save(entity.getClient());
                    sdao.save(entity.getSpace());
                }
                // Update the appointment in the database
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setTime(1, Time.valueOf(entity.getStartTime()));
                    pst.setTime(2, Time.valueOf(entity.getEndTime()));
                    pst.setDate(3, Date.valueOf(entity.getDate()));
                    pst.setInt(4, entity.getClient().getId_person());
                    pst.setInt(5, entity.getSpace().getId_space());
                    pst.setInt(6, entity.getId_appointment());
                    pst.executeUpdate();
                }


            }
            result = entity;
        }
        return result;
    }

    /**
     * Deletes an Appointment entity from the database.
     *
     * @param entity The Appointment object to be deleted.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public void delete(Appointment entity) throws SQLException {
        if(entity != null) {
            // Delete the appointment from the database
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_appointment());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Retrieves a list of Appointments based on the given space ID and date.
     *
     * @param spaceId The ID of the space.
     * @param date    The date for which to retrieve the appointments.
     * @return A list of Appointments matching the given space ID and date.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public List<Appointment> findByDateAndSpace(int spaceId, LocalDate date) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE id_space = ? AND date = ?";
        // Prepare the SQL statement to select appointments based on space ID and date
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, spaceId);
            statement.setDate(2, java.sql.Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            // Iterate over the result set and create Appointment objects
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId_appointment(resultSet.getInt("id_appointment"));
                appointment.setStartTime(resultSet.getTime("startTime").toLocalTime());
                appointment.setEndTime(resultSet.getTime("endTime").toLocalTime());
                appointment.setDate(resultSet.getDate("date").toLocalDate());
                int clientId = resultSet.getInt("id_client");
                if (clientId > 0) {
                    // Retrieve the associated client using ClientDAO
                    ClientDAO clientDAO = new ClientDAO(conn);
                    Client client = clientDAO.findById(clientId);
                    appointment.setClient(client);
                }
                int spaceIdFromResultSet = resultSet.getInt("id_space");
                if (spaceIdFromResultSet > 0) {
                    // Retrieve the associated space using SpaceDAO
                    SpaceDAO spaceDAO = new SpaceDAO(conn);
                    Space space = spaceDAO.findById(spaceIdFromResultSet);
                    appointment.setSpace(space);
                }
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    /**
     * Retrieves the last ID of the Appointment from the database.
     *
     * @return The last ID of the Appointment.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public int findLastId() throws SQLException {
        String query = "SELECT MAX(id_appointment) AS max_id FROM appointment";
        // Prepare the SQL statement to retrieve the last ID of the Appointment
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the last ID from the result set and return it
                return resultSet.getInt("max_id");
            }
        }
        // If no ID is found, return 0 or a default value
        return 0;
    }

    @Override
    public void close() throws Exception {

    }
}
