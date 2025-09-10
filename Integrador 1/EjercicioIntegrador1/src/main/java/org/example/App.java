package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.entity.FacturaProducto;
import org.example.entity.Producto;
import org.example.factory.ConnectionManagerMySQL;
import org.example.factory.DAOFactory;
import org.example.factory.DBType;
import org.example.utils.DBUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class App {

    public static void main( String[] args ) throws IOException, SQLException {
        ConnectionManagerMySQL connectionManager = ConnectionManagerMySQL.getInstance();
        DBUtils dbUtils = new DBUtils(connectionManager); // inicializa el static conn

        // Creo la fábrica concreta MySQL
        DAOFactory factory = DAOFactory.getInstance(DBType.MYSQL);

        // Creo las clases DAOS pero a través de la fábrica (no directamente desde la clase) -> FACTORY
        ClienteDAO clienteDAO = factory.createClienteDAO();
        FacturaDAO facturaDAO = factory.createFacturaDAO();
        ProductoDAO productoDAO = factory.createProductoDAO();
        FacturaProductoDAO facturaProductoDAO = factory.createFacturaProductoDAO();

        /* ---------------------------- CREACIÓN DE TABLAS --------------------------- */
        DBUtils.createTableCliente();
        DBUtils.createTableFactura();
        DBUtils.createTableProducto();
        DBUtils.createTableFacturaProducto();

        /* --------------------------- CARGA DE ARCHIVOS CSV -------------------------- */
        dbUtils.addCliente(clienteDAO);
        dbUtils.addFactura(facturaDAO);
        dbUtils.addProducto(productoDAO);
        dbUtils.addFacturaProducto(facturaProductoDAO);

        // Producto que más recaudó
        System.out.println(productoDAO.findProductMaxFacturacion().toStringTotalRecaudado());

        // Retorna una lista de clientes, ordenada de forma descendente, segun su facturación
        List<Cliente> clientes = clienteDAO.findAllByMaxFacturacion();
        System.out.println("Cliente por orden de facturación: ");
        for(Cliente c:clientes){
            System.out.println("idCliente: " + c.getIdCliente()+ ", Nombre: " + c.getNombre()+ ", Total facturado: " + c.getTotalFacturado());
        }

//        /* ------------------------------- CRUD CLIENTE ------------------------------ */
//        // Agregar un cliente nuevo
//        clienteDAO.insert(new Cliente(101, "Eli", "mariaslda@sakjdkaj"));
//
//        // Editar cliente existente
//        Cliente clienteParaEditar = new Cliente(1, "Fran", "sdfa@gmail");
//        clienteDAO.update(clienteParaEditar);
//
//        // Obtener un cliente por ID
//        Cliente clienteMostrar = clienteDAO.findById(clienteParaEditar.getIdCliente());
//        System.out.println(clienteMostrar.getNombre());
//        //clienteDAO.findById(5);
//        //clienteDAO.findById(0);
//        //clienteDAO.findById(800);
//
//        // Eliminar un cliente por ID
//        //clienteDAO.deleteById(99);
//
//        // Listar todos los clientes
//        List<Cliente> clientesList = clienteDAO.findAll();
//        for (Cliente c: clientesList){
//            System.out.println(c.getNombre());
//        }
//
//
//    /* --------------------------------- CRUD FACTURA -------------------------------- */
//      // Agregar una nueva factura
//        facturaDAO.insert(new Factura(512, 1));
//
//        // Editar una factura existente
//        Factura facturaParaEditar = new Factura(512, 2);
//        facturaDAO.update(facturaParaEditar);
//
//        // Obtener una factura por ID
//        Factura faturaMostrar = facturaDAO.findById(facturaParaEditar.getIdFactura());
//        System.out.println("Factura " + faturaMostrar.getIdFactura() + " pertenece al cliente " + faturaMostrar.getIdCliente());
//        System.out.println(facturaDAO.findById(5));
//
//        // Eliminar un cliente por ID
//        facturaDAO.insert(new Factura(513, 1));
//        facturaDAO.deleteById(513);
//
//        // Listar todos las facturas
//        List<Factura> facturasList = facturaDAO.findAll();
//        for (Factura f: facturasList){
//            System.out.println("Factura " + f.getIdFactura() + " cliente " + f.getIdCliente());
//        }
//
//
//        /* --------------------------------- CRUD PRODUCTO -------------------------------- */
//        // Producto que más recaudó
//        // System.out.println(productoDAO.findProductMaxFacturacion()); // Imprime el toString por defecto (con todos los datos)
//
//
//        Producto pruebaProducto = new Producto(101, "Baggio", 5F);
//        productoDAO.insert(pruebaProducto);
//        productoDAO.update(new Producto(101, "Baggio", 6F));
//        productoDAO.deleteById(101);
//        System.out.println(productoDAO.findById(3));
//        List<Producto> productos = productoDAO.findAll();
//        for (Producto p: productos) {
//            System.out.println(p.getNombre());
//        }
//
//        /* ----------------------------- CRUD FACTURA-PRODUCTO ---------------------------- */
//
//        Cliente clienteTest = new Cliente(999, "Cliente Test", "cliente@test.com");
//        clienteDAO.insert(clienteTest);
//        Factura facturaTest = new Factura(5000, clienteTest.getIdCliente());
//        facturaDAO.insert(facturaTest);
//        Producto productoTest = new Producto(3000, "Producto Test", 150.0f);
//        productoDAO.insert(productoTest);
//        FacturaProducto pruebaFP = new FacturaProducto(5000,3000,5);
//        facturaProductoDAO.insert(pruebaFP);
//        System.out.println("FacturaProducto insertado correctamente: "
//                + "Factura " + pruebaFP.getIdFactura()
//                + ", Producto " + pruebaFP.getIdProducto()
//                + ", Cantidad " + pruebaFP.getCantidad());
//        facturaProductoDAO.deleteById(pruebaFP);
//        facturaDAO.deleteById(facturaTest.getIdFactura());
//        productoDAO.deleteById(productoTest.getIdProducto());
//        clienteDAO.deleteById(clienteTest.getIdCliente());
//        facturaDAO.findById(5000);
//        System.out.println(8);
    }
}
