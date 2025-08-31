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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Cliente> findAllByMaxFacturacion() throws SQLException {
        String sql = "SELECT * FROM cliente";
        ResultSet rs = null;
        List<Cliente> listClientes = new ArrayList<>();

        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        rs = statement.executeQuery();
        while(rs.next()) {
            Cliente cliente = new Cliente(rs.getInt("idCliente"),
                                            rs.getString("nombre"),
                                            rs.getString("email"));
            listClientes.add(cliente);
        }
        return listClientes;
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

                Cliente cliente = new Cliente(Integer.valueOf(id), nombre, email);
                insert(cliente);  // cambia el método insert para recibir un solo Cliente
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        /*ConnectionManagerMySQL.getInstance().closeConn();*/
    }

    private void insert(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, c.getIdCliente());
            statement.setString(2, c.getNombre());
            statement.setString(3, c.getEmail());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }


}
