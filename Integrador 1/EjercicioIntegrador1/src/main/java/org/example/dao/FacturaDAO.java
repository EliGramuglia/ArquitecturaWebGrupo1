package org.example.dao;

import org.example.entity.Cliente;
import org.example.entity.Factura;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {
    void createTable() throws SQLException;
    void add() throws IOException;
    void insert(Factura f) throws SQLException;
    void updateFactura(Factura f) throws SQLException;
    void deleteByIdFactura(Factura f) throws SQLException;
    List<Factura> findAllFacturas() throws SQLException;
    Factura findById(int id) throws SQLException;


}
