package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.entity.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class App 
{
    public static void main( String[] args ) throws IOException, SQLException {
        ClienteDAO clienteDAO = new ClienteDAOImpl(new ArrayList<>());

        //clienteDAO.createTable();
        //clienteDAO.add(); // Agrega los cvs

        //Editar cliente existente
        Cliente clienteParaEditar = new Cliente(1, "adas lala", "asd@gmail");
        clienteDAO.updateClient(clienteParaEditar);

        //Obtener un cliente por ID
        clienteDAO.findByIdClient(clienteParaEditar.getIdCliente());
        clienteDAO.findByIdClient(5);
        //clienteDAO.findByIdClient(0);
        //clienteDAO.findByIdClient(800);

        //Eliminar un cliente por ID
        //clienteDAO.deleteByIdClient(99);

        //Listar todos los clientes
        List<Cliente> clientesList = clienteDAO.findAllClients();
        for (Cliente c: clientesList){
            System.out.println(c.getNombre());
        }




    }
}
