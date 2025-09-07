package org.example.repository;

import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.FacturaDAOImpl;
import org.example.dao.impl.FacturaProductoDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
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
    public ProductoDAOImpl createProductoDAO() {
        return new ProductoDAOImpl(conn);
    }

    @Override
    public FacturaDAO createFacturaDAO() {
        return new FacturaDAOImpl(conn);
    }

    @Override
    public FacturaProductoDAO createFacturaProductoDAO() {
        return new FacturaProductoDAOImpl(conn);
    }


}
