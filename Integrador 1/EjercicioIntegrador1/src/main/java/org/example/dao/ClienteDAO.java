package org.example.dao;

import org.example.entity.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAllByMaxFacturacion() throws SQLException;
    void createTable() throws SQLException;
    void add() throws IOException;
    void insert(Cliente c) throws SQLException;
    void update(Cliente c) throws SQLException;
    void deleteById(int id) throws SQLException;
    List<Cliente> findAll() throws SQLException;
    Cliente findById(int id) throws SQLException;

}
