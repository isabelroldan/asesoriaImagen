package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements DAO<Appointment> {

    private final static  String FINDALL = "SELECT * FROM appointment";
    private final static  String FINDBYID = "SELECT * FROM appointment WHERE id_appointment = ?";
    private final static  String INSERT = "INSERT INTO appointment(id_appointment, startTime, endTime, date, id_client, id_space) VALUES(?, ?, ?, ?, ?, ?)";
    private final static  String UPDATE = "UPDATE appointment SET startTime = ?, endTime = ?, date = ?, id_client= ?, id_space = ?";
    private final static  String DELETE = "DELETE FROM appointment WHERE id_appointment = ?";

    private Connection conn;

    public AppointmentDAO(Connection conn) {
        this.conn = conn;
    }

    public AppointmentDAO() {
        this.conn = Connect.getConnect();
    }

    @Override
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try(ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId_appointment(res.getInt("id_appointment"));
                    appointment.setStartTime(res.getTime("startTime").toLocalTime());
                    appointment.setEndTime(res.getTime("endTime").toLocalTime());
                    appointment.setDate(res.getDate("date").toLocalDate());
                    ClientDAO cdao = new ClientDAO(this.conn);
                    Client client = cdao.findById(res.getInt("id_client"));
                    appointment.setClient(client);

                    SpaceDAO adao = new SpaceDAO(this.conn);
                    Space space = adao.findById(res.getInt("id_space"));
                    appointment.setSpace(space);

                    result.add(appointment);
                }
            }
        }
        return result;
    }

    @Override
    public Appointment findById(int id_appointment) throws SQLException {
        Appointment result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id_appointment);
            try(ResultSet res = pst.executeQuery()) {
                if(res.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId_appointment(res.getInt("id_appointment"));
                    appointment.setStartTime(res.getTime("startTime").toLocalTime());
                    appointment.setEndTime(res.getTime("endTime").toLocalTime());
                    appointment.setDate(res.getDate("date").toLocalDate());

                    ClientDAO cdao = new ClientDAO(this.conn);
                    Client client = cdao.findById(res.getInt("id_client"));
                    appointment.setClient(client);

                    SpaceDAO sdao = new SpaceDAO(this.conn);
                    Space space = sdao.findById(res.getInt("id_space"));
                    appointment.setSpace(space);
                    result = appointment;
                }
            }
        }
        return result;
    }

    @Override
    public Appointment save(Appointment entity) throws SQLException {
        Appointment result = new Appointment();
        if(entity != null) {
            Appointment appointment = findById(entity.getId_appointment());

            ClientDAO cdao = new ClientDAO(this.conn);
            Client myClient = cdao.findById(entity.getClient().getId_person());

            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());

            if(appointment == null) {
                if(myClient == null & mySpace == null){
                    cdao.save(entity.getClient());
                    sdao.save(entity.getSpace());
                }
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getId_appointment());
                    pst.setTime(2, Time.valueOf(entity.getStartTime()));
                    pst.setTime(3, Time.valueOf(entity.getEndTime()));
                    pst.setDate(4, Date.valueOf(entity.getDate()));
                    pst.setInt(5, entity.getClient().getId_person());
                    pst.setInt(6, entity.getSpace().getId_space());
                    pst.executeUpdate();
                }


            }
            result = entity;
        }
        return result;
    }

    @Override
    public Appointment update(Appointment entity) throws SQLException {
        Appointment result = new Appointment();
        if(entity != null) {
            Appointment appointment = findById(entity.getId_appointment());

            ClientDAO cdao = new ClientDAO(this.conn);
            Client myClient = cdao.findById(entity.getClient().getId_person());

            SpaceDAO sdao = new SpaceDAO(this.conn);
            Space mySpace = sdao.findById(entity.getSpace().getId_space());

            if(appointment != null) {
                if(myClient == null & mySpace == null){
                    cdao.save(entity.getClient());
                    sdao.save(entity.getSpace());
                }
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setTime(1, Time.valueOf(entity.getStartTime()));
                    pst.setTime(2, Time.valueOf(entity.getEndTime()));
                    pst.setDate(3, Date.valueOf(entity.getDate()));
                    pst.setInt(4, entity.getClient().getId_person());
                    pst.setInt(5, entity.getSpace().getId_space());
                    pst.executeUpdate();
                }


            }
            result = entity;
        }
        return result;
    }

    @Override
    public void delete(Appointment entity) throws SQLException {
        if(entity != null) {
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_appointment());
                pst.executeUpdate();
            }
        }
    }

    @Override
    public void close() throws Exception {

    }
}
