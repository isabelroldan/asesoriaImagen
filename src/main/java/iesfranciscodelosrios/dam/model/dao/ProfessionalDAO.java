package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Professional;
import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Space;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessionalDAO implements DAO<Professional> {

    private final static String FINDALL = "SELECT * FROM professional";
    private final static String FINDBYID = "SELECT * FROM professional WHERE id_professional = ?";
    private final static String INSERT = "INSERT INTO professional(id_professional, name, surname, telephone, email, password, dni, nPersonnel, nSocialSecurity, id_space) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE professional SET name = ?, surname = ?, telephone = ?, email = ?, password = ?, dni = ?, nPersonnel = ?, nSocialSecurity = ?, id_space = ? WHERE id_professional = ?";
    private final static String DELETE = "DELETE FROM professional WHERE id_professional = ?";
    private final static String PROFESSIONALLOGIN = "SELECT * FROM professional WHERE email = ? AND password = ?";
    private final static String GETPROFESSIONAL = "SELECT * FROM professional WHERE email = ?";

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

    public boolean checkIfIdExists(int id) throws SQLException {
        String sql = "SELECT * FROM professional WHERE id_professional = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean checkIfEmailExists(String email) throws SQLException {
        String sql = "SELECT * FROM professional WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Professional save(Professional entity) throws SQLException {
        Professional result = new Professional();
        if(entity != null) {
            Professional professional = findById(entity.getId_person());
            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());
            if(professional == null) {
                if(mySpace == null)
                    sdao.save(entity.getSpace());
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId_person());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getSurname());
                    pst.setString(4, entity.getTelephone());
                    pst.setString(5, entity.getEmail());
                    pst.setString(6, entity.getPassword());
                    pst.setString(7, entity.getDni());
                    pst.setInt(8, entity.getnPersonnel());
                    pst.setInt(9, entity.getnSocialSecurity());
                    pst.setInt(10, entity.getSpace().getId_space());
                    pst.executeUpdate();
                }

            }
            result = entity;
        }
        return result;
    }

    public Professional update(Professional entity) throws SQLException {
        Professional result = new Professional();
        if(entity != null) {
            Professional professional = findById(entity.getId_person());
            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());
            if(professional != null) {
                if(mySpace == null)
                    sdao.save(entity.getSpace());
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setString(2, entity.getSurname());
                    pst.setString(3, entity.getTelephone());
                    pst.setString(4, entity.getEmail());
                    pst.setString(5, entity.getPassword());
                    pst.setString(6, entity.getDni());
                    pst.setInt(7, entity.getnPersonnel());
                    pst.setInt(8, entity.getnSocialSecurity());
                    pst.setInt(9, entity.getSpace().getId_space());
                    pst.setInt(10, entity.getId_person());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    @Override
    public void delete(Professional entity) throws SQLException {
        if(entity != null) {
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_person());
                pst.executeUpdate();
            }
        }
    }

    /**
     * Method that checks in the login if there is a professional with an email and a password equal to the one entered
     * @param email to check
     * @param password to check
     * @return boolean true if it has been found and false if not
     * @throws SQLException
     */
    public boolean professionalLogin(String email, String password) throws SQLException {
        boolean result = false;
        try(PreparedStatement pst = this.conn.prepareStatement(PROFESSIONALLOGIN)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    result = true;
                }
            }
        }
        return result;
    }

    /*public Professional getProfessional(String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM professionals WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String telephone = rs.getString("telephone");
            email = rs.getString("email");
            String password = rs.getString("password");
            String dni = rs.getString("dni");
            int nPersonnel = rs.getInt("nPersonnel");
            int nSocialSecurity = rs.getInt("nSocialSecurity");
            Space space = rs.getString("space");

            Professional professional = new Professional(id, name, surname, telephone, email, password, dni, nPersonnel, nSocialSecurity, space);
            return professional;
        }

        return null;
    }*/

    public Professional getProfessional(String email) throws SQLException {
        Professional result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(GETPROFESSIONAL)) {
            pst.setString(1, email);
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
    public void close() throws Exception {

    }
}
