import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Space;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class SpaceDAOTest {
    public static void main(String[] args) {
        try {
            SpaceDAO spaceDAO = new SpaceDAO();

            List<Space> spaces = spaceDAO.findAll();
            
            for(Space space : spaces) {
                System.out.println(space.getId_space()+ " - " + space.getName() + " - " + space.getServiceType());
            }
        }catch (SQLException e) {
            System.out.println("Ha ocurrido un error al obtener spaces: " + e.getMessage());
        }
    }
        /*Space space = new Space(5, "Sala 3", "asesoria corporativa");*/

}
