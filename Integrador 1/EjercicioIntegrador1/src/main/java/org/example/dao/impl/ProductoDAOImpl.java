package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Producto;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductoDAOImpl {
    private ConnectionManagerMySQL conn;

    public ProductoDAOImpl() {
        this.conn= ConnectionManagerMySQL.getInstance();
    }

    public void createTable() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS productos (" +
                "idProducto INT, " +
                "nombre VARCHAR(45)" +
                "valor FLOAT " +
                "PRIMARY KEY (idProducto))";
        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }

    public void add() throws IOException {
        try (CSVParser product = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ProductoDAOImpl.class.getResourceAsStream("/productos.csv"))))) {

            for (CSVRecord record : product) {
                String id = record.get("idProducto");
                String nombre = record.get("nombre");
                String valor = record.get("valor");

                Producto producto = new Producto(Integer.valueOf(id), nombre, Float.valueOf(valor));
                insert(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Producto findProductMaxFacturacion() throws SQLException {
        String sql = "SELECT p.idProducto, SUM(t.cantidadProducto * p.valor) AS total_recaudado " +
                "FROM factura-productos fp " +
                "JOIN productos p ON fp.idProducto = p.idProducto " +
                "GROUP BY p.idProducto " +
                "ORDER BY total_recaudado DESC" +
                "LIMIT 1;";
        Producto productoById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                productoById = findByIdProducto(rs.getInt("idProducto")
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


    private void insert(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, p.getIdProducto());
            statement.setString(2, p.getNombre());
            statement.setFloat(3, p.getValor());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    public void update(Producto p) throws SQLException {
        String sql = "UPDATE productos SET nombre=?, valor=? WHERE idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setString(1, p.getNombre());
            statement.setFloat(2, p.getValor());
            statement.setInt(3, p.getIdProducto());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    public void deleteByIdProducto(Producto p) throws SQLException {
        String sql = "DELETE FROM productos WHERE idProducto=?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, p.getIdProducto());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }

    public List<Producto> findAll() throws SQLException {
        String sql = "SELECT * FROM productos";
        //ResultSet rs = null;
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

    public Producto findByIdProducto(int idProducto) throws SQLException {
        String sql = "SELECT * FROM productos WHERE idProducto=?";
        ResultSet rs = null;
        Producto productoById = null;
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            rs = statement.executeQuery();
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
