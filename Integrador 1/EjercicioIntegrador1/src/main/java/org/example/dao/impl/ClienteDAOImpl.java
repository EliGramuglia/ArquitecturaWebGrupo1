package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.ClienteDAO;
import org.example.entity.Cliente;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ClienteDAOImpl implements ClienteDAO {

    private ConnectionManagerMySQL conn;
    private final List<Cliente> listClientes;

    public ClienteDAOImpl(List<Cliente> listClientes) {
        this.listClientes = listClientes;
        this.conn = ConnectionManagerMySQL.getInstance();
    }

    @Override
    public List<Cliente> findAllByMaxFacturacion() {
        return null;
    }

    @Override
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

                //insert clientes
                listClientes.add(new Cliente(Integer.valueOf(id), nombre, email));
            }
        }
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "idCliente INT, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "PRIMARY KEY (idCliente)) ";
        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
        ConnectionManagerMySQL.getInstance().closeConn();
    }

    private void insert(List<Cliente> listClientes) throws SQLException {

        for (Cliente c : listClientes) {
            String sql = "INSERT INTO cliente (id, nombre, email) VALUES (?, ?, ?)";
            try {
                PreparedStatement statement = conn.getConex().prepareStatement(sql);
                statement.setInt(1, c.getIdCliente());
                statement.setString(2, c.getNombre());
                statement.setString(3, c.getEmail());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }

    }

}
