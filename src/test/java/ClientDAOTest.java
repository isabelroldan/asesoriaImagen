

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.ColorTestResult;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientDAOTest {
    public static void main(String[] args) {
        System.out.println("Prueba FINDALL");
        try {
            ClientDAO clientDAO = new ClientDAO();

            List<Client> clients = clientDAO.findAll();

            for(Client client : clients) {
                System.out.println(client);
            }
        }catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener spaces: " + e.getMessage());
        }

        System.out.println("Prueba FINDBYID");

        try {
            Connection conn = Connect.getConnect();
            ClientDAO clientDAO = new ClientDAO(conn);

            // Prueba de findById
            Client client = clientDAO.findById(1);
            System.out.println(client);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



        /*System.out.println("Prueba de save");

        Connection conn = Connect.getConnect();
        Client client = new Client();
        client.setId_person(15);
        client.setName("Juan");
        client.setSurname("Pérez");
        client.setTelephone("123456789");
        client.setEmail("juan.perez@example.com");
        client.setPassword("secreto");
        client.setColorTestResult(ColorTestResult.autoum);

        try {
            ClientDAO dao = new ClientDAO(conn); // Crear un objeto ClientDao con la conexión
            dao.save(client);
        } catch (SQLException e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
        }*/

        /*System.out.println("Prueba UPDATE");
        Connection conn = Connect.getConnect();
        ClientDAO dao = new ClientDAO(conn);
        Client client = new Client();
        client.setId_person(15);
        client.setName("María");
        client.setColorTestResult(ColorTestResult.summer);
        try {
            Client updatedClient = dao.update(client);
            System.out.println("Actualizado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        /*System.out.println("Prueba delete");
        Connection conn = Connect.getConnect();
        ClientDAO dao = new ClientDAO(conn);
        Client clientToDelete = new Client();
        clientToDelete.setId_person(15);
        try {
            dao.delete(clientToDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Client deletedClient = null;
        try {
            deletedClient = dao.findById(15);
            if (deletedClient == null) {
                System.out.println("Cliente eliminado correctamente");
            } else {
                System.out.println("Error al eliminar el cliente");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

    }
}
