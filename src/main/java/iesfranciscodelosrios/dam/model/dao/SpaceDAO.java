package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpaceDAO {
    private final static String FINDBYID = "SELECT * FROM space WHERE id_space = ?";
    private Connection conn;

    public SpaceDAO(Connection conn) {
        this.conn = conn;
    }

    public SpaceDAO() {
        this.conn = Connect.getConnect();
    }

    public Space findById(int id_space) throws SQLException {
        Space result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_space);
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    Space space = new Space();
                    space.setId_space(res.getInt("id_space"));
                    space.setName(res.getString("name"));
                    space.setServiceType(res.getString("serviceType"));
                    result = space;
                }

            }
        }
        return result;
    }


}
