package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements DAO<Client> {

    private final static String FINDALL = "SELECT * FROM client";
    private final static  String FINDBYID = "SELECT * FROM client WHERE id_client = ?";
    private final static  String INSERT = "INSERT INTO client(id_client, name, surname, telephone, email, password, colorTestResult) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static  String UPDATE = "UPDATE client SET name = ?, surname = ?, telephone = ?, email = ?, password = ?, colorTestResult = ? WHERE id_client = ?";
    private final static  String DELETE = "DELETE FROM client WHERE id_client = ?";

    private Connection conn;

    public ClientDAO(Connection conn) { this.conn = conn; }

    public ClientDAO() { this.conn = Connect.getConnect(); }

    /**
     * Retrieves a list of all clients from the database.
     *
     * @return A list of all clients.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try(ResultSet res = pst.executeQuery()) {
                // Iterate over the result set and create Client objects
                while(res.next()) {
                    Client client = new Client();
                    client.setId_person(res.getInt("id_client"));
                    client.setName(res.getString("name"));
                    client.setSurname(res.getString("surname"));
                    client.setTelephone(res.getString("telephone"));
                    client.setEmail(res.getString("email"));
                    client.setPassword(res.getString("password"));

                    // Retrieve the color test result string from the result set
                    String resultString = res.getString("colorTestResult");
                    ColorTestResult ctr = null;
                    // Find the corresponding ColorTestResult enum value
                    for(ColorTestResult r: ColorTestResult.values()) {
                        if(r.name().equalsIgnoreCase(resultString)) {
                            ctr = r;
                            break;
                        }
                    }

                    // Set the color test result on the client if it is not null
                    if (client != null) {
                        client.setColorTestResult(ctr);

                    }
                    result.add(client);
                }
            }
        }
        return result;
    }

    /**
     * Retrieves a client by their ID from the database.
     *
     * @param id_client The ID of the client to retrieve.
     * @return The client with the specified ID, or null if not found.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Client findById(int id_client) throws SQLException {
        Client result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_client);
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    Client client = new Client();
                    client.setId_person(res.getInt("id_client"));
                    client.setName(res.getString("name"));
                    client.setSurname(res.getString("surname"));
                    client.setTelephone(res.getString("telephone"));
                    client.setEmail(res.getString("email"));
                    /*client.setPassword(res.getString("password"));*/

                    // Retrieve the color test result string from the result set
                    String resultString = res.getString("colorTestResult");
                    ColorTestResult ctr = null;
                    // Find the corresponding ColorTestResult enum value
                    for(ColorTestResult r: ColorTestResult.values()) {
                        if(r.name().equalsIgnoreCase(resultString)) {
                            ctr = r;
                            break;
                        }
                    }

                    // Set the color test result on the client if it is not null
                    if (client != null) {
                        client.setColorTestResult(ctr);
                    }

                    result = client;
                }
            }
        }
        return result;
    }

    /**
     * Retrieves a client by their email from the database.
     *
     * @param email The email of the client to retrieve.
     * @return The client with the specified email, or null if not found.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public Client findByEmail(String email) throws SQLException {
        Client result = null;
        String query = "SELECT * FROM client WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = new Client();
                    result.setId_person(resultSet.getInt("id_client"));
                    result.setName(resultSet.getString("name"));
                    result.setSurname(resultSet.getString("surname"));
                    result.setTelephone(resultSet.getString("telephone"));
                    result.setEmail(resultSet.getString("email"));
                    result.setPassword(resultSet.getString("password"));

                    // Retrieve the color test result string from the result set
                    String resultString = resultSet.getString("colorTestResult");
                    ColorTestResult ctr = null;
                    // Find the corresponding ColorTestResult enum value
                    for (ColorTestResult r : ColorTestResult.values()) {
                        if (r.name().equalsIgnoreCase(resultString)) {
                            ctr = r;
                            break;
                        }
                    }
                    // Set the color test result on the client
                    result.setColorTestResult(ctr);
                }
            }
        }
        return result;
    }

    /**
     * Checks if a client with the specified ID exists in the database.
     *
     * @param id The ID of the client to check.
     * @return {@code true} if a client with the specified ID exists, {@code false} otherwise.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public boolean checkIfIdExists(int id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id_client = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks if a client with the specified email exists in the database.
     *
     * @param email The email of the client to check.
     * @return {@code true} if a client with the specified email exists, {@code false} otherwise.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public boolean checkIfEmailExists(String email) throws SQLException {
        String sql = "SELECT * FROM client WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Saves a client entity to the database.
     *
     * @param entity The client entity to save.
     * @return The saved client entity.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Client save(Client entity) throws SQLException {
        Client result = new Client();
        if (entity != null) {
            Client client = findById(entity.getId_person());
            if (client == null) {
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId_person());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getSurname());
                    pst.setString(4, entity.getTelephone());
                    pst.setString(5, entity.getEmail());
                    pst.setString(6, entity.getPassword());
                    pst.setString(7, entity.getColorTestResult().name());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    /**
     * Updates a client entity in the database.
     *
     * @param entity The client entity to update.
     * @return The updated client entity.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public Client update(Client entity) throws SQLException {
        Client result = new Client();
        if (entity != null) {
            Client client = findById(entity.getId_person());
            if (client != null) {
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getSurname());
                    pst.setString(3, entity.getTelephone());
                    pst.setString(4, entity.getEmail());
                    pst.setString(5, entity.getPassword());
                    pst.setString(6, entity.getColorTestResult().name());
                    pst.setInt(7, entity.getId_person());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    /**
     * Deletes a client entity from the database.
     *
     * @param entity The client entity to delete.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public void delete(Client entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_person());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Retrieves a list of clients based on an email pattern.
     *
     * @param email The email pattern to search for.
     * @return A list of Client objects matching the specified email pattern.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public List<Client> findByEmailWrite(String email) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client WHERE email LIKE ?";
        // Prepare the SQL statement to select clients based on email pattern
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + email + "%");
            ResultSet resultSet = statement.executeQuery();
            // Iterate over the result set and create Client objects
            while (resultSet.next()) {
                Client client = new Client();
                client.setId_person(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("name"));
                client.setSurname(resultSet.getString("surname"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setEmail(resultSet.getString("email"));

                String resultString = resultSet.getString("colorTestResult");
                ColorTestResult ctr = null;
                // Find the corresponding ColorTestResult enum value
                for(ColorTestResult r: ColorTestResult.values()) {
                    if(r.name().equalsIgnoreCase(resultString)) {
                        ctr = r;
                        break;
                    }
                }

                // Set the color test result on the client if it is not null
                if (client != null) {
                    client.setColorTestResult(ctr);

                }
                clients.add(client);
            }
        }
        return clients;
    }

    @Override
    public void close() throws Exception {

    }
}
