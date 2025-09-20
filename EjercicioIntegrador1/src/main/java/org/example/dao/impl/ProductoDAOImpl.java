package org.example.dao.impl;

import org.example.dao.ProductoDAO;
import org.example.entity.Producto;
import org.example.factory.ConnectionManagerMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private ConnectionManagerMySQL conn;
    private static ProductoDAOImpl instance;

    public ProductoDAOImpl(ConnectionManagerMySQL conn) {
        this.conn = conn;
    }

    public static ProductoDAOImpl getInstance(ConnectionManagerMySQL conn) {
        if(instance == null) {
            instance = new ProductoDAOImpl(conn);
        }
        return instance;
    }

    @Override
    public Producto findProductMaxFacturacion() throws SQLException {
        String sql = "SELECT p.*, SUM(fp.cantidad * p.valor) AS totalRecaudado " +
                "FROM facturaproducto fp " +
                "JOIN producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY p.idProducto " +
                "ORDER BY totalRecaudado DESC " +
                "LIMIT 1;";
        Producto productoById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                productoById = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("valor"),
                        rs.getFloat("totalRecaudado")
                        );
            }else{
                System.out.println("No se encontraron productos: ");
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error al buscar ", e);
        }
        return productoById;
    }

    /* ------------------------------ CRUD ------------------------------ */

    @Override
    public void insert(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, p.getIdProducto());
            statement.setString(2, p.getNombre());
            statement.setFloat(3, p.getValor());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void insertAll(List<Producto> p) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO producto (idProducto, nombre, valor) VALUES ");
        p.forEach(pr -> {
            sql.append(String.format("(%s, '%s', %s), ", pr.getIdProducto(), pr.getNombre(), pr.getValor()));
        });
        StringBuilder sqlFinal = new StringBuilder(sql.substring(0, sql.length() - 2));
        sqlFinal.append(";");
        try (PreparedStatement statement = conn.getConex().prepareStatement(sqlFinal.toString())) {
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void update(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre=?, valor=? WHERE idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setString(1, p.getNombre());
            statement.setFloat(2, p.getValor());
            statement.setInt(3, p.getIdProducto());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public void deleteById(int p) throws SQLException {
        String sql = "DELETE FROM producto WHERE idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, p);
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    @Override
    public List<Producto> findAll() throws SQLException {
        String sql = "SELECT * FROM producto";
        List<Producto> listProductos = new ArrayList<>();
        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Producto producto = new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("nombre"),
                    rs.getFloat("valor")
            );
            listProductos.add(producto);
        }
        return listProductos;
    }

    @Override
    public Producto findById(int idProducto) throws SQLException {
        String sql = "SELECT * FROM producto WHERE idProducto=?";
        Producto productoById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                productoById = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("valor")
                );
            }else{
                System.out.println("No existe el producto con el id: " + idProducto);
            }
        }catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto con el id " + idProducto, e);
        }
        return productoById;
    }
}