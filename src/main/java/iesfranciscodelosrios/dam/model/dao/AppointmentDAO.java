package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Appointment;

import java.sql.SQLException;
import java.util.List;

public class AppointmentDAO implements DAO<Appointment> {
    @Override
    public List<Appointment> findAll() throws SQLException {
        return null;
    }

    @Override
    public Appointment findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Appointment save(Appointment entity) throws SQLException {
        return null;
    }

    @Override
    public Appointment update(Appointment entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(Appointment entity) throws SQLException {

    }

    @Override
    public void close() throws Exception {

    }
}
