package iesfranciscodelosrios.dam.model.connections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String file = "./conexion.xml";
    private static Connect _newInstance;
    private static Connection con;

    /**
     * Load the connection data from an XML file
     * If any error occurs during the connection the variable is set to null
     */
    private Connect() {
        ConectionData cd = loadXML();

        try {
            con = DriverManager.getConnection(cd.getServer()+"/"+cd.getDatabase(), cd.getUsername(), cd.getPassword());
        } catch (SQLException e) {
            con = null;
            e.printStackTrace();
        }
    }

    /**
     * Is the only access point to the instance of the Connect class. If the _newInstance variable is null,
     * the method creates a new instance of Connect and assigns it to the _newInstance variable
     * @return con: The variable that was set in the private constructor
     */
    public static Connection getConnect() {
        if(_newInstance == null) {
            _newInstance = new Connect();
        }

        return con;
    }

    /**
     * This method loads the connections data stored in an XML file and returns it in a ConectionData object
     * @return con: ConectionData object representing the connection information loaded from an XML file using.
     * If an error occurs during the information loading, it prints the error's and returns an empty ConectionData object
     */
    public ConectionData loadXML() {
        ConectionData con = new ConectionData();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ConectionData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            con = (ConectionData) jaxbUnmarshaller.unmarshal(new File(file));
        }catch (JAXBException e) {
            e.printStackTrace();
            /*throw new JAXBException(e);*/
        }
        return con;
    }
}
