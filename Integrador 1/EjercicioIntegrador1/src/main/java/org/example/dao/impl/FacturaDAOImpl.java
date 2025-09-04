package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.FacturaDAO;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.factory.ConnectionManagerMySQL;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FacturaDAOImpl implements FacturaDAO {
    private ConnectionManagerMySQL conn;

    public FacturaDAOImpl() {
        this.conn= ConnectionManagerMySQL.getInstance();
    }

    /* Método para crear la tabla Factura */
    public void createTable() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS factura (" +
                "idFactura INT, " +
                "idCliente INT, " +
                "PRIMARY KEY (idFactura), " +
                "CONSTRAINT fk_factura_cliente " +
                "FOREIGN KEY (idCliente) " +
                "REFERENCES cliente(idCliente) " +
                "ON DELETE CASCADE" +
                ")";

        PreparedStatement statement= conn.getConex().prepareStatement(sql);
        statement.execute();
        conn.getConex().commit();
    }


    /* Método para leer y cargar los archivos cvs en la tabla de la bdd */
    @Override
    public void add() throws IOException {
        try (CSVParser facturas = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/facturas.csv"))))) {

            for (CSVRecord record : facturas) {
                String idFactura = record.get("idFactura");
                String idCliente = record.get("idCliente");

                Factura factura = new Factura(Integer.valueOf(idFactura), Integer.valueOf(idCliente));
                insert(factura);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* ------------------------------ CRUD ------------------------------ */

    // AGREGAR UNA FACTURA
    @Override
    public void insert(Factura f) throws SQLException {
        String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, f.getIdFactura());
            statement.setInt(2, f.getIdCliente());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }


    // EDITAR UNA FACTURA
    public void updateFactura(Factura f) throws SQLException {
        String sql = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, f.getIdCliente());
            statement.setInt(2, f.getIdFactura());
            statement.executeUpdate();
            conn.getConex().commit();
        }
    }


    // OBTENER UNA FACTURA
    @Override
    public Factura findById(int idFactura) throws SQLException {
        String sql = "SELECT * FROM factura WHERE idFactura = ?"; // placeholders para evitar inyecciones sql
        Factura facturaById = null;

        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) { // prepara la consulta
            statement.setInt(1, idFactura); // asigna el valor real (en el placeholder 1)
            ResultSet rs = statement.executeQuery(); // ejecuta la query
            if (rs.next()) { // si trajo algo
                facturaById = new Factura( // guardo los valores en la variable que voy a devolver
                        rs.getInt("idFactura"),
                        rs.getInt("idCliente")
                );
            } else { // si no trae nada, lo informo
                System.out.println("No existe la factura con id " + idFactura);
            }
        } catch (SQLException e) { // por si falla la bdd
            e.printStackTrace();
            throw new SQLException("Error al buscar la factura con id " + idFactura, e);
        }

        return facturaById;
    }


    // LISTAR TODAS LAS FACTURAS
    public List<Factura> findAllFacturas() throws SQLException {
        List<Factura> listaFacturas = new ArrayList<>();
        String sql = "SELECT * FROM factura";

        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Factura factura = new Factura(rs.getInt("idFactura"),
                        rs.getInt("idFactura"));
                listaFacturas.add(factura);
            }
        }
        return listaFacturas;
    }


    // ELIMINAR UNA FACTURA
    public void deleteByIdFactura(Factura f) throws SQLException {
        String sql = "DELETE FROM factura WHERE idFactura = ?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, f.getIdFactura());
            int filasEliminadas = statement.executeUpdate();
            conn.getConex().commit(); // confirma los cambios

            if (filasEliminadas > 0) {
                System.out.println("Factura eliminado!");
            } else {
                System.out.println("No existe factura con ese id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar factura con id " + f.getIdFactura(), e);
        }
    }



}
