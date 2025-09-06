package org.example.repository;

import org.example.dao.FacturaDAO;
import org.example.dao.ProductoDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.factory.ConnectionManagerMySQL;
import org.example.factory.DAOFactory;

// Fábrica concreta
public class MySQLDAOFactory extends DAOFactory {
    private ConnectionManagerMySQL conn; // Le pasa una conexion MySQL porque está dentro de la fábrica MySQL

    public MySQLDAOFactory() {
        this.conn = ConnectionManagerMySQL.getInstance(); // Singleton
    }

    @Override
    public ClienteDAOImpl createClienteDAO() {
        return new ClienteDAOImpl(conn);
    }

    @Override
    public ProductoDAO createProductoDAO() {
        return null;
    }

    @Override
    public FacturaDAO createFacturaDAO() {
        return null;
    }


}
