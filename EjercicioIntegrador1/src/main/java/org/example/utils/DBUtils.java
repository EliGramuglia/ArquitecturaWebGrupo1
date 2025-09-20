package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.entity.FacturaProducto;
import org.example.entity.Producto;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtils {
    private static ConnectionManagerMySQL conn;

    public DBUtils(ConnectionManagerMySQL conn) {
        DBUtils.conn = conn;
    }


    /* ---------------------------- CREACIÓN DE TABLAS --------------------------- */
    public static void createTableCliente() throws SQLException {
        if (conn == null) {
            throw new IllegalStateException("Conexión no inicializada");
        }
        String sql = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "idCliente INT, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "PRIMARY KEY (idCliente)) ";
        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }

    public static void createTableFacturaProducto() throws SQLException {
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

    public static void createTableProducto() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS producto (" +
                "idProducto INT PRIMARY KEY, " +
                "nombre VARCHAR(45)," +
                "valor FLOAT " +
                ")";
        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }

    public static void createTableFactura() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS factura (" +
                "idFactura INT, " +
                "idCliente INT, " +
                "PRIMARY KEY (idFactura), " +
                "CONSTRAINT fk_factura_cliente " +
                "FOREIGN KEY (idCliente) " +
                "REFERENCES cliente(idCliente) " +
                "ON DELETE CASCADE" +   // Si borro un cliente, se tienen que borrar sus facturas asociadas
                ")";

        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }


    /* ------------------------ MÉTODOS PARA CARGAR LOS CSV ----------------------- */
    public void addCliente(ClienteDAO clienteDAO) throws IOException {
        List<Cliente> listClientes = new ArrayList<>();
        try (CSVParser client = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/clientes.csv"))))) {

            for (CSVRecord record : client) {
                String id = record.get("idCliente");
                String nombre = record.get("nombre");
                String email = record.get("email");

                listClientes.add(new Cliente(Integer.valueOf(id), nombre, email));
            }
            clienteDAO.insertAll(listClientes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFacturaProducto(FacturaProductoDAO facturaProductoDAO) throws IOException {
        List<FacturaProducto> listaFacturasProducto = new ArrayList<>();
        try (CSVParser facturaProduct = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ProductoDAOImpl.class.getResourceAsStream("/facturas-productos.csv"))))) {

            for (CSVRecord record : facturaProduct) {
                String idFactura = record.get("idFactura");
                String idProducto = record.get("idProducto");
                String cantidad = record.get("cantidad");

                listaFacturasProducto.add(new FacturaProducto(Integer.valueOf(idFactura), Integer.valueOf(idProducto), Integer.valueOf(cantidad)));
            }
            facturaProductoDAO.insertAll(listaFacturasProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProducto(ProductoDAO productoDAO) throws IOException {
        List<Producto> listaProductos = new ArrayList<>();
        try (CSVParser product = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ProductoDAOImpl.class.getResourceAsStream("/productos.csv"))))) {

            for (CSVRecord record : product) {
                String id = record.get("idProducto");
                String nombre = record.get("nombre");
                String valor = record.get("valor");

                listaProductos.add(new Producto(Integer.valueOf(id), nombre, Float.valueOf(valor)));
            }
            productoDAO.insertAll(listaProductos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFactura(FacturaDAO facturaDAO) throws IOException {
        List<Factura> listaFacturas = new ArrayList<>();
        try (CSVParser facturas = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/facturas.csv"))))) {

            for (CSVRecord record : facturas) {
                String idFactura = record.get("idFactura");
                String idCliente = record.get("idCliente");

                listaFacturas.add(new Factura(Integer.valueOf(idFactura), Integer.valueOf(idCliente)));
            }
            facturaDAO.insertAll(listaFacturas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}