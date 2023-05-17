package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Space;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpaceDAO implements DAO<Space> {
    private final static String FINDALL = "SELECT * FROM space";
    private final static String FINDBYID = "SELECT * FROM space WHERE id_space = ?";
    private final static String INSERT = "INSERT INTO space(id_space, name, serviceType) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE space SET name = ?, serviceType = ? WHERE id_space = ?";
    private final static String DELETE = "DELETE FROM space WHERE id_space = ?";
    private Connection conn;

    public SpaceDAO(Connection conn) {
        this.conn = conn;
    }

    public SpaceDAO() {
        this.conn = Connect.getConnect();
    }

    /**
     * Retrieves all spaces from the database.
     *
     * @return A list of Space objects representing the spaces.
     * @throws SQLException if there is an error executing the SQL query.
     */
    @Override
    public List<Space> findAll() throws SQLException {
        List<Space> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
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

    /**
     * Retrieves a space from the database based on its ID.
     *
     * @param id_space The ID of the space to retrieve.
     * @return A Space object representing the space with the specified ID, or null if not found.
     * @throws SQLException if there is an error executing the SQL query.
     */
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

    /**
     * Checks if a space with the given ID exists in the database.
     *
     * @param id The ID of the space to check.
     * @return true if a space with the specified ID exists, false otherwise.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public boolean checkIfIdExists(int id) throws SQLException {
        String sql = "SELECT * FROM space WHERE id_space = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Saves a Space entity in the database.
     *
     * @param entity The Space entity to save.
     * @return The saved Space entity.
     * @throws SQLException if there is an error executing the SQL query.
     */
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

    /**
     * Updates a Space entity in the database.
     *
     * @param entity The Space entity to update.
     * @return The updated Space entity.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public Space update(Space entity) throws SQLException {
        Space result = new Space();
        if(entity != null) {
            Space space = findById(entity.getId_space());
            if(space != null) {
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getServiceType());
                    pst.setInt(3, entity.getId_space());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    /**
     * Deletes a Space entity from the database.
     *
     * @param entity The Space entity to delete.
     * @throws SQLException if there is an error executing the SQL query.
     */
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
