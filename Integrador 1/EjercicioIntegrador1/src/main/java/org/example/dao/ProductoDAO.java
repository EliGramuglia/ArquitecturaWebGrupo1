package org.example.dao;

import org.example.entity.Producto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    Producto findProductMaxFacturacion() throws SQLException;
    void createTable() throws SQLException;
    void add() throws IOException;
    void insert(Producto p) throws SQLException;
    void update(Producto p) throws SQLException;
    void deleteById(Producto p) throws SQLException;
    List<Producto> findAll() throws SQLException;
    Producto findById(int id) throws SQLException;

}
