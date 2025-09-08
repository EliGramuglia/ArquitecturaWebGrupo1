package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.FacturaProductoDAO;
import org.example.entity.FacturaProducto;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS facturaproducto (" +
                "idFactura INT, " +
                "idProducto INT, " +
                "cantidad INT, " +
                "PRIMARY KEY (idFactura, idProducto), " +
                "CONSTRAINT fk_factura_producto_factura FOREIGN KEY (idFactura) REFERENCES factura(idFactura) " +
                "ON DELETE CASCADE, " +
                "CONSTRAINT fk_factura_producto_producto FOREIGN KEY (idProducto) REFERENCES producto(idProducto) "+
                "ON DELETE CASCADE" +   // Si borro un producto, se tienen que borrar sus facturas asociadas
                ")";
        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }

    @Override
    public void add() throws IOException {
        try (CSVParser facturaProduct = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ProductoDAOImpl.class.getResourceAsStream("/facturas-productos.csv"))))) {

            for (CSVRecord record : facturaProduct) {
                String idFactura = record.get("idFactura");
                String idProducto = record.get("idProducto");
                String cantidad = record.get("cantidad");

                FacturaProducto facturaProducto = new FacturaProducto(Integer.valueOf(idFactura), Integer.valueOf(idProducto), Integer.valueOf(cantidad));
                insert(facturaProducto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            statement.setInt(1, fp.getIdProducto());
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
