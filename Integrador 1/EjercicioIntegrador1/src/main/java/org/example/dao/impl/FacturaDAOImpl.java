package org.example.dao.impl;

import org.example.dao.FacturaDAO;
import org.example.entity.Factura;
import org.example.factory.ConnectionManagerMySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {
    private ConnectionManagerMySQL conn;
    private final List<Factura> listFacturas;

    public FacturaDAOImpl(List<Factura> listFacturas) {
        this.listFacturas = listFacturas;
        this.conn= ConnectionManagerMySQL.getInstance();
    }

    public void createTable() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS facturas (" +
                "idFactura INT, " +
                "idCliente INT" +
                "PRIMARY KEY (idFactura))";
        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }
    @Override
    public void add(List<Factura> f) {

    }

}
