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

        clienteDAO.createTable();
        clienteDAO.add();

        List<Cliente> lista = clienteDAO.findAllByMaxFacturacion();

        for (Cliente cliente : lista) {
            System.out.println(cliente.getNombre());
        }


    }
}
