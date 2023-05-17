package iesfranciscodelosrios.dam.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends AutoCloseable {
    /**
     * Retrieves all entities of type T from the database.
     *
     * @return A list containing all entities of type T.
     * @throws SQLException if there is an error executing the SQL query.
     */
    List<T> findAll() throws SQLException;

    /**
     * Retrieves an entity of type T from the database based on the provided ID.
     *
     * @param id The ID used to identify the entity.
     * @return The entity of type T matching the provided ID, or null if not found.
     * @throws SQLException if there is an error executing the SQL query.
     */
    T findById(int id) throws SQLException;

    /**
     * Saves the provided entity of type T into the database.
     *
     * @param entity The entity of type T to be saved.
     * @return The saved entity of type T.
     * @throws SQLException if there is an error executing the SQL query.
     */
    T save(T entity) throws SQLException;

    /**
     * Updates the provided entity of type T in the database.
     *
     * @param entity The entity of type T to be updated.
     * @return The updated entity of type T.
     * @throws SQLException if there is an error executing the SQL query.
     */
    T update(T entity) throws SQLException;

    /**
     * Deletes the provided entity of type T from the database.
     *
     * @param entity The entity of type T to be deleted.
     * @throws SQLException if there is an error executing the SQL query.
     */
    void delete(T entity) throws SQLException;
}
