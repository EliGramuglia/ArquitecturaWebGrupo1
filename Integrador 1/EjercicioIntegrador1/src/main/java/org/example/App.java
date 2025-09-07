package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.FacturaDAOImpl;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.factory.DAOFactory;
import org.example.factory.DBType;
import org.example.repository.MySQLDAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main( String[] args ) throws IOException, SQLException {

        // Creo la fábrica concreta MySQL
        DAOFactory factory = DAOFactory.getInstance(DBType.MYSQL);

        // Creo las clases DAOS pero a través de la fábrica (no directamente desde la clase) -> FACTORY
        ClienteDAO clienteDAO = factory.createClienteDAO();
        FacturaDAO facturaDAO = factory.createFacturaDAO();
        ProductoDAO productoDAO = factory.createProductoDAO();
        FacturaProductoDAO facturaProductoDAO = factory.createFacturaProductoDAO();

        /* ---------------------------- CREACIÓN DE TABLAS --------------------------- */
        //clienteDAO.createTable();
        //facturaDAO.createTable();
        //productoDAO.createTable();
        //facturaDAO.createTable();


        /* ------------------------------- CRUD CLIENTE ------------------------------ */
/*        // Agrega los cvs
        //clienteDAO.add();

        // Agregar un cliente nuevo
        clienteDAO.insert(new Cliente(101, "Eli", "mariaslda@sakjdkaj"));

        // Editar cliente existente
        Cliente clienteParaEditar = new Cliente(1, "Fran", "sdfa@gmail");
        clienteDAO.update(clienteParaEditar);

        // Obtener un cliente por ID
        Cliente clienteMostrar = clienteDAO.findById(clienteParaEditar.getIdCliente());
        System.out.println(clienteMostrar.getNombre());
        //clienteDAO.findById(5);
        //clienteDAO.findById(0);
        //clienteDAO.findById(800);

        // Eliminar un cliente por ID
        //clienteDAO.deleteById(99);

        // Listar todos los clientes
        List<Cliente> clientesList = clienteDAO.findAll();
        for (Cliente c: clientesList){
            System.out.println(c.getNombre());
        }
*/
        // Retorna una lista de clientes, ordenada de forma descendente, segun su facturación
        //clienteDAO.findAllByMaxFacturacion();


    /* --------------------------------- CRUD FACTURA -------------------------------- */
       // Agrega los cvs
/*        facturaDAO.add();

        // Agregar una nueva factura
        facturaDAO.insert(new Factura(512, 1));

        // Editar una factura existente
        Factura facturaParaEditar = new Factura(512, 2);
        facturaDAO.update(facturaParaEditar);

        // Obtener una factura por ID
        Factura faturaMostrar = facturaDAO.findById(facturaParaEditar.getIdFactura());
        System.out.println("Factura " + faturaMostrar.getIdFactura() + " pertenece al cliente " + faturaMostrar.getIdCliente());
        facturaDAO.findById(5);

        // Eliminar un cliente por ID
        facturaDAO.insert(new Factura(513, 1));
        facturaDAO.deleteById(513);

        // Listar todos las facturas
        List<Factura> facturasList = facturaDAO.findAll();
        for (Factura f: facturasList){
            System.out.println("Factura " + f.getIdFactura() + " cliente " + f.getIdCliente());
        }
*/

        /* --------------------------------- CRUD PRODUCTO -------------------------------- */


    }
}
