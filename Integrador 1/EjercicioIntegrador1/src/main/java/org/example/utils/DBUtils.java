package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
import org.example.dao.impl.FacturaDAOImpl;
import org.example.dao.impl.FacturaProductoDAOImpl;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.entity.FacturaProducto;
import org.example.entity.Producto;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DBUtils {
    private static ConnectionManagerMySQL conn;
    private ClienteDAOImpl clienteDAO;
    private FacturaDAOImpl facturaDAO;
    private ProductoDAOImpl productoDAO;
    private FacturaProductoDAOImpl facturaProductoDAO;

    public DBUtils(ConnectionManagerMySQL conn) {
        DBUtils.conn = conn;
    }


    /* Método para crear la tabla Cliente */
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



    /* Método para leer y cargar los archivos cvs en la tabla de la bdd */
    public void addCliente() throws IOException {
        try (CSVParser client = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/clientes.csv"))))) {

            for (CSVRecord record : client) {
                String id = record.get("idCliente");
                String nombre = record.get("nombre");
                String email = record.get("email");

                Cliente cliente = new Cliente(Integer.valueOf(id), nombre, email);
                clienteDAO.insert(cliente);  // cambia el método insert para recibir un solo Cliente
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   // ----------------FacturaProducto-----------

   public void createTableFacturaProducto() throws SQLException {
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


    public void addFacturaProducto() throws IOException {
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
                facturaProductoDAO.insert(facturaProducto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------Producto-------


    public void createTableProducto() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS producto (" +
                "idProducto INT PRIMARY KEY, " +
                "nombre VARCHAR(45)," +
                "valor FLOAT " +
                ")";
        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }


    public void addProducto() throws IOException {
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
                productoDAO.insert(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //-----------Factura--------
    /* Método para crear la tabla Factura */

    public void createTableFactura() throws SQLException {
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


    /* Método para leer y cargar los archivos cvs en la tabla de la bdd */

    public void addFactura() throws IOException {
        try (CSVParser facturas = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/facturas.csv"))))) {

            for (CSVRecord record : facturas) {
                String idFactura = record.get("idFactura");
                String idCliente = record.get("idCliente");

                Factura factura = new Factura(Integer.valueOf(idFactura), Integer.valueOf(idCliente));
                facturaDAO.insert(factura);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}