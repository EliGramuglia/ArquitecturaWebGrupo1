package org.example.dao;

import org.example.entity.Cliente;
import org.example.entity.Factura;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {
    void insert(Factura f) throws SQLException;
    void insertAll(List<Factura> f) throws SQLException;
    void update(Factura f) throws SQLException;
    void deleteById(int f) throws SQLException;
    List<Factura> findAll() throws SQLException;
    Factura findById(int id) throws SQLException;

}
