package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
import org.example.dao.impl.FacturaDAOImpl;
import org.example.dao.impl.FacturaProductoDAOImpl;
import org.example.entity.Cliente;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DBUtils {
    private ConnectionManagerMySQL conn;

    public DBUtils(ConnectionManagerMySQL conn) {
        this.conn = conn;
    }


    /* Método para crear la tabla Cliente */
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "idCliente INT, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "PRIMARY KEY (idCliente)) ";
        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
        /*ConnectionManagerMySQL.getInstance().closeConn();*/
    }



    /* Método para leer y cargar los archivos cvs en la tabla de la bdd */
    public void add() throws IOException {
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
                insert(cliente);  // cambia el método insert para recibir un solo Cliente
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}