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


        //Editando cliente que ya existe
        Cliente clienteParaEditar = new Cliente(1, "adas lala", "asd@gmail");
        clienteDAO.updateClient(clienteParaEditar);

        //Obtener un cliente por ID
        clienteDAO.findByIdCliente(clienteParaEditar.getIdCliente());
        clienteDAO.findByIdCliente(5);




    }
}
