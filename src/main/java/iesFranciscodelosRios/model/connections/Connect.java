package iesFranciscodelosRios.model.connections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String file = "conexion.xml";
    private static Connect _newInstance;
    private static Connection con;

    private Connect() {
        ConectionData cd = loadXML();

        try {
            con = DriverManager.getConnection(cd.getServer()+"/"+cd.getDatabase(), cd.getUsername(), cd.getPassword());
        } catch (SQLException e) {
            con = null;
            e.printStackTrace();
        }
    }

    public static Connection getConnect() {
        if(_newInstance == null) {
            _newInstance = new Connect();
        }

        return con;
    }

    public ConectionData loadXML() {
        ConectionData con = new ConectionData();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ConectionData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            con = (ConectionData) jaxbUnmarshaller.unmarshal(new File(file));
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        return con;
    }
}
