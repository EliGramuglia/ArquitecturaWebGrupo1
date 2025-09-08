package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.ClienteDAO;
import org.example.entity.Cliente;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteDAOImpl implements ClienteDAO {
    private ConnectionManagerMySQL conn;

    public ClienteDAOImpl(ConnectionManagerMySQL conn) {
        this.conn = conn;
    }

    /* Método para crear la tabla Cliente */
    @Override
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


    /* Función que retorna una lista de clientes, ordenada de forma descendente, segun su facturación */
    @Override
    public List<Cliente> findAllByMaxFacturacion() throws SQLException {
        String sql = "SELECT c.idCliente, c.nombre AS nombreCliente, SUM(fp.cantidad * p.valor) AS totalFacturado " +
                "FROM cliente c " +
                "JOIN factura f ON c.idCliente = f.idCliente " +
                "JOIN facturaproducto fp ON f.idFactura = fp.idFactura " +
                "JOIN producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre " +
                "ORDER BY totalFacturado DESC;";
        List<Cliente> listClientes = new ArrayList<>();

        PreparedStatement statement = conn.getConex().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Cliente cliente = findById(rs.getInt("idCliente"));
            cliente.setTotalFacturado(rs.getFloat("totalFacturado"));
            listClientes.add(cliente);
        }
        return listClientes;
    }


    /* Método para leer y cargar los archivos cvs en la tabla de la bdd */
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

    /* ------------------------------ CRUD ------------------------------ */

    // AGREGAR UN CLIENTE
    @Override
    public void insert(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, c.getIdCliente());
            statement.setString(2, c.getNombre());
            statement.setString(3, c.getEmail());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }


    // EDITAR UN CLIENTE
    @Override
    public void update(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE idCliente = ?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setString(1, c.getNombre());
            statement.setString(2, c.getEmail());
            statement.setInt(3, c.getIdCliente());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }


    // OBTENER UN CLIENTE
    @Override
    public Cliente findById(int idCliente) throws SQLException {
        /*if (idCliente <= 0) {
            throw new IllegalArgumentException("El id debe ser un número mayor a 0");
        }*/

        String sql = "SELECT * FROM cliente WHERE idCliente = ?"; // placeholders para evitar inyecciones sql
        Cliente clienteById = null;

        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) { // prepara la consulta
            statement.setInt(1, idCliente); // asigna el valor real (en el placeholder 1)
            ResultSet rs = statement.executeQuery(); // ejecuta la query
            if (rs.next()) { // si trajo algo
                clienteById = new Cliente( // guardo los valores en la variable que voy a devolver
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
            } else { // si no trae nada, lo informo
                System.out.println("No existe cliente con id " + idCliente);
            }
        } catch (SQLException e) { // por si falla la bdd
            e.printStackTrace();
            throw new SQLException("Error al buscar cliente con id " + idCliente, e);
        }

        return clienteById;
    }


    // LISTAR TODOS LOS CLIENTES
    @Override
    public List<Cliente> findAll() throws SQLException {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"));
                listaClientes.add(cliente);
            }
        }
        return listaClientes;
    }


    // ELIMINAR UN CLIENTE
    @Override
    public void deleteById(int idCliente) throws SQLException {
        /*if (idCliente <= 0) {
            throw new IllegalArgumentException("El id debe ser un número mayor a 0");
        }*/

        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idCliente);
            int filasEliminadas = statement.executeUpdate();
            conn.getConex().commit(); // confirma los cambios

            if (filasEliminadas > 0) {
                System.out.println("Cliente eliminado!");
            } else {
                System.out.println("No existe cliente con ese id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar cliente con id " + idCliente, e);
        }
    }


}
