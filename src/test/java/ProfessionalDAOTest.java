import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.ProfessionalDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Professional;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfessionalDAOTest {
    public static void main(String[] args) {
        /*Prueba FINDALL*/
        Connection conn = Connect.getConnect();
        ProfessionalDAO dao = new ProfessionalDAO(conn);
        /*List<Professional> professionals = null;
        try {
            professionals = dao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de todos los profesionales:");
        for (Professional professional : professionals) {
            System.out.println(professional);
        }*/

        /*PRUEBA FINDBYID*/
        /*int id_professional = 6; // Aquí puedes cambiar el id que quieres buscar
        Professional professional = null;
        try {
            professional = dao.findById(id_professional);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Profesional con id " + id_professional + ":");
        System.out.println(professional);*/

            /*Professional p = new Professional();
            p.setId_person(1);
            p.setName("John");
            p.setSurname("Doe");
            p.setTelephone("555-1234");
            p.setEmail("johndoe@example.com");
            p.setPassword("password");
            p.setDni("12345678A");
            p.setnPersonnel(12345);
            p.setnSocialSecurity(67890);

            Space s = new Space();
            s.setId_space(1);
            s.setName("Office");

            p.setSpace(s);

            // Insertar el registro
        try {
            dao.save(p);
            System.out.println("Insertado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        /*PRUEBA UPDATE*/
        /*Professional profesional = new Professional();
        profesional.setId_person(1);
        profesional.setName("Juan");
        profesional.setSurname("Pérez");
        profesional.setTelephone("123456789");
        profesional.setEmail("juan.perez@ejemplo.com");
        profesional.setPassword("12345");
        profesional.setDni("12345678A");
        profesional.setnPersonnel(1);
        profesional.setnSocialSecurity(123456789);
        SpaceDAO sdao = new SpaceDAO(conn);
        Space space = new Space();
        space.setId_space(1);
        profesional.setSpace(space);

        // Guardar objeto en la base de datos
        try {
            dao.update(profesional);
            System.out.println("Modificado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        /*PRUEBA DELETE*/
        /*try {
            Professional professional = dao.findById(1);
            dao.delete(professional);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        boolean result = false;
        try {
            result = dao.professionalLogin("du@gmail.com", "5632");
            System.out.println(result);

            boolean result2 = dao.professionalLogin("invalid@example.com", "invalidpassword");
            System.out.println(result2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
