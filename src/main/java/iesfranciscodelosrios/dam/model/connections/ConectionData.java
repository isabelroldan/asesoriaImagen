package iesfranciscodelosrios.dam.model.connections;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="conexion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConectionData implements Serializable {

    private static final long serialVersionID  = 1L;
    private String server;
    private String database;
    private String username;
    private String password;

    public ConectionData(){
        this.server = "";
        this.database = "";
        this.username = "";
        this.password = "";
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ConectionData{" +
                "server='" + server + '\'' +
                ", database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
