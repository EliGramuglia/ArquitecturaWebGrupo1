package org.example.factory;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

// Fábrica de fábricas
public abstract class DAOFactory {
    private static volatile DAOFactory instance;

    // Fábrica SINGLETON, para que sólo sea una en toda la app
    public static DAOFactory getInstance(DBType dbtype) {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    switch (dbtype){
                        case MYSQL:
                            instance = MySQLDAOFactory.getInstance();
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de base de datos no soportado");
                    }
                }
            }
        }
        return instance;
    }

    public abstract ClienteDAO createClienteDAO();
    public abstract ProductoDAO createProductoDAO();
    public abstract FacturaDAO createFacturaDAO();
    public abstract FacturaProductoDAO createFacturaProductoDAO();
}
