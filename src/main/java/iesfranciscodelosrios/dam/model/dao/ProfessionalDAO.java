package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Professional;
import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalDAO implements DAO<Professional> {

    private final static String FINDALL = "SELECT * FROM professional";
    private final static String FINDBYID = "SELECT * FROM professional WHERE id_professional = ?";
    private final static String INSERT = "INSERT INTO professional(id_professional, name, surname, telephone, email, password, dni, nPersonnel, nSocialSecurity, id_space) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private Connection conn;

    public ProfessionalDAO(Connection conn) {
        this.conn = conn;
    }

    public ProfessionalDAO() {
        this.conn = Connect.getConnect();
    }

    @Override
    public List<Professional> findAll() throws SQLException {
        List<Professional> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try(ResultSet res = pst.executeQuery()) {
                while(res.next()) {
                    Professional professional = new Professional();
                    professional.setId_person(res.getInt("id_professional"));
                    professional.setName(res.getString("name"));
                    professional.setSurname(res.getString("surname"));
                    professional.setTelephone(res.getString("telephone"));
                    professional.setEmail(res.getString("email"));
                    professional.setPassword(res.getString("password"));
                    professional.setDni(res.getString("dni"));
                    professional.setnPersonnel(res.getInt("nPersonnel"));
                    professional.setnSocialSecurity(res.getInt("nSocialSecurity"));
                    SpaceDAO adao = new SpaceDAO(this.conn);
                    Space space = adao.findById(res.getInt("id_space"));
                    professional.setSpace(space);
                    result.add(professional);
                }
            }
        }
        return result;
    }

    @Override
    public Professional findById(int id_professional) throws SQLException {
        Professional result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_professional);
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    Professional professional = new Professional();
                    professional.setId_person(res.getInt("id_professional"));
                    professional.setName(res.getString("name"));
                    professional.setSurname(res.getString("surname"));
                    professional.setTelephone(res.getString("telephone"));
                    professional.setEmail(res.getString("email"));
                    professional.setPassword(res.getString("password"));
                    professional.setDni(res.getString("dni"));
                    professional.setnPersonnel(res.getInt("nPersonnel"));
                    professional.setnSocialSecurity(res.getInt("nSocialSecurity"));
                    SpaceDAO adao = new SpaceDAO(this.conn);
                    Space space = adao.findById(res.getInt("id_space"));
                    professional.setSpace(space);
                    result = professional;
                }
            }
        }
        return result;
    }

    @Override
    public Professional save(Professional entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(Professional entity) throws SQLException {

    }

    @Override
    public void close() throws Exception {

    }
}
