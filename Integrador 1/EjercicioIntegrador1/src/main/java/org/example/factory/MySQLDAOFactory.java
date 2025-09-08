package org.example.factory;

import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.FacturaDAOImpl;
import org.example.dao.impl.FacturaProductoDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;

// Fábrica concreta
public class MySQLDAOFactory extends DAOFactory {
    private ConnectionManagerMySQL conn; // Le pasa una conexion MySQL porque está dentro de la fábrica MySQL

    public MySQLDAOFactory() {
        this.conn = ConnectionManagerMySQL.getInstance(); // Singleton
    }

    @Override
    public ClienteDAOImpl createClienteDAO() {
        return ClienteDAOImpl.getInstance(conn);
    }

    @Override
    public ProductoDAOImpl createProductoDAO() {
        return ProductoDAOImpl.getInstance(conn);
    }

    @Override
    public FacturaDAO createFacturaDAO() {
        return FacturaDAOImpl.getInstance(conn);
    }

    @Override
    public FacturaProductoDAO createFacturaProductoDAO() {
        return FacturaProductoDAOImpl.getInstance(conn);
    }

}
