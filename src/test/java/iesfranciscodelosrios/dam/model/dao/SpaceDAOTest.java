package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Space;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpaceDAOTest {
    static SpaceDAO spaceDAO;
    @BeforeAll
    public static void setUpClass(){
        spaceDAO = new SpaceDAO();
    }
    @Test
    @DisplayName("Comprobación para numeros de espacios en la base de datos, comprobación si es cirto que devuelve List<Space> y " +
            "creamos un List de Space e introducimos todos los datos que tenemos en esa tabla y lo comprobamos por posicion en el ArrayList")
    void findAll() {
        try {
            assertEquals(2, spaceDAO.findAll().size());
            assertTrue(spaceDAO.findAll() instanceof List<Space>);
            List<Space> spaceList = new ArrayList<Space>();
            spaceList.addAll(spaceDAO.findAll());
            assertEquals("prueba", spaceList.get(1).getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Comprobamos que lo que indicamos sea igual al serviceType extraido por id y" +
            " comprobación de que al buscar un id de la base de datos no sea nulo")
    void findById(){
        int id_space = 1;
        try {
            assertEquals("pruebaArreglando", spaceDAO.findById(id_space).getServiceType());

            Space result = spaceDAO.findById(id_space);

            assertNotNull(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Verifica que el id existente se encuentra en la bbdd y verificación que el id que no existe no este en la bbdd")
    void checkIfIdExists(){
        try {
            int existingId = 1; // ID existente en la base de datos
            int nonExistingId = 100; // ID que no existe en la base de datos

            boolean existingResult = spaceDAO.checkIfIdExists(existingId);
            boolean nonExistingResult = spaceDAO.checkIfIdExists(nonExistingId);

            assertTrue(existingResult); // Verifica que el ID existente se encuentre en la base de datos
            assertFalse(nonExistingResult); // Verifica que el ID que no existe no se encuentre en la base de datos
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("Verificación para ver que se guarda un nuevo espacio")
    void save() throws SQLException {
        Space newSpace = new Space(3, "New Space", "Service B"); // Nuevo espacio a guardar

        Space savedNewSpace = spaceDAO.save(newSpace);

        assertEquals(newSpace, spaceDAO.findById(newSpace.getId_space())); // Verifica que el nuevo espacio se haya guardado correctamente en la base de datos
    }

    @Test
    @DisplayName("Verificación que el espacio actualizado no sea nulo, que el espacio actualizado sea igual al modificado y " +
            "que el espacio de la bbdd sea igual al modificado")
    void update() throws SQLException {
        Space existingSpace = new Space(3, "New Space", "Service B"); // Espacio a modificar

        // Modificamos el espacio existente
        existingSpace.setName("Updated Space");
        existingSpace.setServiceType("Service A");

        Space updatedSpace = spaceDAO.update(existingSpace);

        assertNotNull(updatedSpace); // Verifica que el espacio actualizado no sea nulo
        assertEquals(existingSpace, updatedSpace); // Verifica que el espacio actualizado sea igual al espacio modificado
        Space fetchedSpace = spaceDAO.findById(existingSpace.getId_space());
        assertEquals(existingSpace, fetchedSpace); // Verifica que el espacio en la base de datos sea igual al espacio modificado
    }

    @Test
    @DisplayName("Verificación de que el espacio haya sido eliminado en la bbdd")
    void delete() throws SQLException{
        Space existingSpace = new Space(3, "Updated Space", "Service A"); // Espacio a borrar

        spaceDAO.delete(existingSpace);

        Space deletedSpace = spaceDAO.findById(existingSpace.getId_space());
        assertNull(deletedSpace); // Verifica que el espacio haya sido eliminado de la base de datos
    }
}