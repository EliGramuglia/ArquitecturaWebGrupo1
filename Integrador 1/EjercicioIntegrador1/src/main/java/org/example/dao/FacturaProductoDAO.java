package org.example.dao;

import org.example.entity.Factura;
import org.example.entity.FacturaProducto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FacturaProductoDAO {
    void insert(FacturaProducto fp) throws SQLException;
    void insertAll(List<FacturaProducto> fp) throws SQLException;
    void update(FacturaProducto fp) throws SQLException;
    void deleteById(FacturaProducto fp) throws SQLException;
    List<FacturaProducto> findAll() throws SQLException;
    FacturaProducto findByIdProducto(int idProducto) throws SQLException;
    FacturaProducto findByIdfactura(int idFactura) throws SQLException;
}
