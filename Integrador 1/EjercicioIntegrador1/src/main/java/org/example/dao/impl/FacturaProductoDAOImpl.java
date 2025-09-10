package org.example.dao.impl;

import org.example.dao.FacturaProductoDAO;
import org.example.entity.FacturaProducto;
import org.example.factory.ConnectionManagerMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaProductoDAOImpl implements FacturaProductoDAO {
    private ConnectionManagerMySQL conn;
    private static FacturaProductoDAOImpl instance;

    public FacturaProductoDAOImpl(ConnectionManagerMySQL conn) {
        this.conn = conn;
    }

    public static FacturaProductoDAOImpl getInstance(ConnectionManagerMySQL conn) {
        if(instance == null) {
            instance = new FacturaProductoDAOImpl(conn);
        }
        return instance;
    }

    /* ------------------------------ CRUD ------------------------------ */

    @Override
    public void insert(FacturaProducto fp) throws SQLException {
        String sql = "INSERT INTO facturaproducto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, fp.getIdFactura());
            statement.setInt(2, fp.getIdProducto());
            statement.setInt(3, fp.getCantidad());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void insertAll(List<FacturaProducto> fp) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO facturaproducto (idFactura, idProducto, cantidad) VALUES ");
        fp.forEach(f -> {
            sql.append(String.format("(%s, %s, %s), ", f.getIdFactura(), f.getIdProducto(), f.getCantidad()));
        });
        StringBuilder sqlFinal = new StringBuilder(sql.substring(0, sql.length() - 2));
        sqlFinal.append(";");
        try (PreparedStatement statement = conn.getConex().prepareStatement(sqlFinal.toString())) {
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void update(FacturaProducto fp) throws SQLException {
        String sql = "UPDATE facturaproducto SET cantidad=? WHERE idFactura=? AND idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, fp.getCantidad());
            statement.setInt(2, fp.getIdFactura());
            statement.setInt(3, fp.getIdProducto());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void deleteById(FacturaProducto fp) throws SQLException {
        String sql = "DELETE FROM facturaproducto WHERE idFactura=? AND idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, fp.getIdFactura());
            statement.setInt(2, fp.getIdProducto());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public List<FacturaProducto> findAll() throws SQLException {
        String sql = "SELECT * FROM facturaproducto";
        List<FacturaProducto> listFacturasProductos = new ArrayList<>();
        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            FacturaProducto facturaProducto = new FacturaProducto(
                    rs.getInt("idFactura"),
                    rs.getInt("idProducto"),
                    rs.getInt("cantidad")
            );
            listFacturasProductos.add(facturaProducto);
        }
        return listFacturasProductos;
    }

//busco por idProducto
@Override
    public FacturaProducto findByIdProducto(int idProducto) throws SQLException {
        String sql = "SELECT * FROM facturaproducto WHERE idProducto=?";
        FacturaProducto productoById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                productoById = new FacturaProducto(
                        rs.getInt("idFactura"),
                        rs.getInt("idProducto"),
                        rs.getInt("cantidad")
                );
            }else{
                System.out.println("No existe el producto con el id: " + idProducto);
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto con el id " + idProducto, e);
        }
        return productoById;
    }

    // busco por idFactura
    @Override
    public FacturaProducto findByIdfactura(int idFactura) throws SQLException {
        String sql = "SELECT * FROM facturaproducto WHERE idFactura=?";
        FacturaProducto facturaById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                facturaById = new FacturaProducto(
                        rs.getInt("idFactura"),
                        rs.getInt("idProducto"),
                        rs.getInt("cantidad")
                );
            }else{
                System.out.println("No existe la factura con el id: " + idFactura);
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error al buscar la factura con el id " + idFactura, e);
        }
        return facturaById;
    }
}