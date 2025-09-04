package org.example.dao;

import org.example.entity.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAllByMaxFacturacion() throws SQLException;
    void add() throws IOException;
    void createTable() throws SQLException;
    void updateClient(Cliente c) throws SQLException;
    Cliente findByIdClient(int id) throws SQLException;
    void deleteByIdClient(int id) throws SQLException;
    List<Cliente> findAllClients() throws SQLException;
    void insert(Cliente c) throws SQLException;
}
