package iesfranciscodelosrios.dam.model.dao;

import iesfranciscodelosrios.dam.model.domain.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientDAO implements DAO<Client> {

    private final static  String FINDBYID = "SELECT * FROM client WHERE id_client = ?";
    private final static  String INSERT = "";
    private final static  String UPDATE = "";
    private final static  String DELETE = "";

    @Override
    public List<Client> findAll() throws SQLException {
        return null;
    }

    @Override
    public Client findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Client save(Client entity) throws SQLException {
        return null;
    }

    @Override
    public Client update(Client entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(Client entity) throws SQLException {

    }

    @Override
    public void close() throws Exception {

    }
}
