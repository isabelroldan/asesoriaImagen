package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDAOTest {
    static ClientDAO clientDAO;
    @BeforeAll
    public static void setUpClass(){
        clientDAO = new ClientDAO();
    }

    @Test
    @DisplayName("Verificación de que la lista de clientes no sea nula y que no esté vacía")
    void findAll() throws SQLException {
        List<Client> clients = clientDAO.findAll();

        assertNotNull(clients); // Verifica que la lista de clientes no sea nula
        assertFalse(clients.isEmpty()); // Verifica que la lista de clientes no esté vacía
    }

    @Test
    @DisplayName("Verificación que el cliente no sea nulo y que el id del cliente sea el esperado")
    void findById() throws SQLException {
        int clientId = 1; // ID del cliente existente en la base de datos

        Client client = clientDAO.findById(clientId);

        assertNotNull(client); // Verifica que el cliente no sea nulo
        assertEquals(clientId, client.getId_person()); // Verifica que el ID del cliente sea el esperado
    }

    @Test
    @DisplayName("Verificación que el cliente no sea nulo y que el email de este sea el esperado")
    void findByEmail() throws SQLException {
        String email = "isabelroldancordoba@hotmail.com"; // Email del cliente existente en la base de datos

        Client client = clientDAO.findByEmail(email);

        assertNotNull(client); // Verifica que el cliente no sea nulo
        assertEquals(email, client.getEmail()); // Verifica que el email del cliente sea el esperado
    }

    @Test
    @DisplayName("Verifica que el id exista en la bbdd")
    void checkIfIdExists() throws SQLException {
        int clientId = 1;

        boolean idExists = clientDAO.checkIfIdExists(clientId);

        assertTrue(idExists); // Verifica que el ID exista en la base de datos
    }

    @Test
    @DisplayName("Verificación que el email exista en la bbdd")
    void checkIfEmailExists() throws SQLException {
        String email = "isabelroldancordoba@hotmail.com";

        boolean emailExists = clientDAO.checkIfEmailExists(email);

        assertTrue(emailExists); // Verifica que el email exista en la base de datos
    }

    @Test
    @DisplayName("Verificación que el cliente guardado no sea nulo y que el id del cliente sea el esperado")
    void save() throws SQLException {
        Client client = new Client();
        client.setId_person(75); // Definir un ID que no exista en la base de datos
        client.setName("ejemplo");
        client.setSurname("ejemplo..");
        client.setTelephone("123456789");
        client.setEmail("ejemploejemplo@example.com");
        client.setPassword("contraseña");
        client.setColorTestResult(ColorTestResult.SUMMER);

        Client savedClient = clientDAO.save(client);

        assertNotNull(savedClient); // Verifica que el cliente guardado no sea nulo
        assertEquals(client.getId_person(), savedClient.getId_person()); // Verifica que el ID del cliente sea el esperado
    }

    @Test
    @DisplayName("Verificación que el cliente actualizado no sea nulo y que el nombre del cliente sea el esperado")
    void update() throws SQLException {
        Client client = new Client();
        client.setId_person(75); // Definir un ID existente en la base de datos
        client.setName("Updated Name");
        client.setSurname("Updated Surname");
        client.setTelephone("987654321");
        client.setEmail("updatedemail@example.com");
        client.setPassword("newpassword");
        client.setColorTestResult(ColorTestResult.SPRINT);

        Client updatedClient = clientDAO.update(client);

        assertNotNull(updatedClient); // Verifica que el cliente actualizado no sea nulo
        assertEquals(client.getName(), updatedClient.getName()); // Verifica que el nombre del cliente sea el esperado
    }

    @Test
    @DisplayName("Verificación que el cliente no se encuentre en la bbdd una vez ha sido borrado")
    void delete() throws SQLException {
        Client client = new Client();
        client.setId_person(75); // Definir un ID existente en la base de datos

        clientDAO.delete(client);

        Client deletedClient = clientDAO.findById(client.getId_person());
        assertNull(deletedClient); // Verifica que el cliente no se encuentre en la base de datos
    }
}