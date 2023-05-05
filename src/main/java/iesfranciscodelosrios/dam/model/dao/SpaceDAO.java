package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpaceDAO implements DAO<Space> {
    private final static String FINDBYID = "SELECT * FROM space WHERE id_space = ?";
    private final static String INSERT = "INSERT INTO space(id_space, name, serviceType) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE space SET name = ?, serviceType = ?";
    private final static String DELETE = "DELETE FROM space WHERE id_space = ?";
    private Connection conn;

    public SpaceDAO(Connection conn) {
        this.conn = conn;
    }

    public SpaceDAO() {
        this.conn = Connect.getConnect();
    }

    @Override
    public List<Space> findAll() throws SQLException {
        List<Space> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            try(ResultSet res = pst.executeQuery()) {
                while(res.next()) {
                    Space space = new Space();
                    space.setId_space(res.getInt("id_space"));
                    space.setName(res.getString("name"));
                    space.setServiceType(res.getString("serviceType"));
                    result.add(space);
                }
            }
        }
        return result;
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

    @Override
    public Space save(Space entity) throws SQLException {
        Space result = new Space();
        if (entity != null) {
            Space space = findById(entity.getId_space());
            if(space == null) {
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId_space());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getServiceType());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    public Space update(Space entity) throws SQLException {
        Space result = new Space();
        if(entity != null) {
            Space space = findById(entity.getId_space());
            if(space != null) {
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getServiceType());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    @Override
    public void delete(Space entity) throws SQLException {
        if(entity != null) {
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_space());
                pst.executeUpdate();
            }
        }
    }


    @Override
    public void close() throws Exception {

    }
}
