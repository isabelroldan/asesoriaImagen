import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Space;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SpaceDAOTest {
    public static void main(String[] args) {

        System.out.println("Prueba FINDALL");
        try {
            SpaceDAO spaceDAO = new SpaceDAO();

            List<Space> spaces = spaceDAO.findAll();

            for(Space space : spaces) {
                System.out.println(space.getId_space()+ " - " + space.getName() + " - " + space.getServiceType());
            }
        }catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener spaces: " + e.getMessage());
        }
        /*
        System.out.println("Prueba FINDBYID");

        try {
            Connection conn = Connect.getConnect();
            SpaceDAO spaceDAO = new SpaceDAO(conn);

            // Prueba de findById
            Space space = spaceDAO.findById(1);
            System.out.println(space.getId_space() + " - " + space.getName() + " - " + space.getServiceType());

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        */
        /*
        System.out.println("Prueba INSERT");

        // Aquí se obtiene la conexión a la base de datos
        Connection conn = Connect.getConnect();

        // Se crea una instancia de SpaceDAO
        SpaceDAO spaceDAO = new SpaceDAO(conn);

        // Se crea un objeto Space y se le asignan los valores
        Space space = new Space();
        space.setId_space(5);
        space.setName("Sala 3");
        space.setServiceType("asesoria corporativa");

        try {
            // Se guarda el objeto Space en la base de datos
            spaceDAO.save(space);
            System.out.println("Se ha guardado el espacio en la base de datos.");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al guardar el espacio: " + e.getMessage());
        }

         */


        /*System.out.println("Prueba UPDATE");
        try {
            // Crear una instancia de la clase DAO y establecer la conexión a la base de datos
            SpaceDAO spaceDAO = new SpaceDAO();

            // Crear un objeto Space con los nuevos valores que queremos actualizar
            Space updatedSpace = new Space();
            updatedSpace.setId_space(1);
            updatedSpace.setName("pruebaArreglando");
            updatedSpace.setServiceType("pruebaArreglando");

            // Actualizar el espacio en la base de datos
            spaceDAO.update(updatedSpace);



        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        /*System.out.println("Prueba delete");
        // Creamos un objeto Space con un id_space existente en la tabla
        Space spaceToDelete = new Space();
        SpaceDAO spaceDAO = new SpaceDAO();
        spaceToDelete.setId_space(5);

        try {
            // Llamamos al método delete pasándole el objeto creado
            spaceDAO.delete(spaceToDelete);

            // Si no ha habido excepciones, significa que el objeto ha sido eliminado correctamente
            System.out.println("El objeto con id_space " + spaceToDelete.getId_space() + " ha sido eliminado de la tabla.");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
        /*Space space = new Space(5, "Sala 3", "asesoria corporativa");*/

}
