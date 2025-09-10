package org.example.dao.impl;

import org.example.dao.FacturaDAO;
import org.example.entity.Factura;
import org.example.factory.ConnectionManagerMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {
    private ConnectionManagerMySQL conn;
    private static FacturaDAOImpl instance;

    public FacturaDAOImpl(ConnectionManagerMySQL conn) {
        this.conn = conn;
    }

    public static FacturaDAOImpl getInstance(ConnectionManagerMySQL conn) {
        if(instance == null) {
            instance = new FacturaDAOImpl(conn);
        }
        return instance;
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
    @Override
    public void update(Factura f) throws SQLException {
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
    @Override
    public List<Factura> findAll() throws SQLException {
        List<Factura> listaFacturas = new ArrayList<>();
        String sql = "SELECT * FROM factura ORDER BY idFactura";

        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Factura factura = new Factura(rs.getInt("idFactura"),
                        rs.getInt("idCliente"));
                listaFacturas.add(factura);
            }
        }
        return listaFacturas;
    }

    // ELIMINAR UNA FACTURA
    @Override
    public void deleteById(int idFactura) throws SQLException {
        String sql = "DELETE FROM factura WHERE idFactura = ?";
        try (PreparedStatement statement = conn.getConex().prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            int filasEliminadas = statement.executeUpdate();
            conn.getConex().commit(); // confirma los cambios

            if (filasEliminadas > 0) {
                System.out.println("Factura eliminado!");
            } else {
                System.out.println("No existe factura con ese id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar factura con id " + idFactura, e);
        }
    }

}
