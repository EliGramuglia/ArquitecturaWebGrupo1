package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.impl.ClienteDAOImpl;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        ClienteDAO clienteDAO = new ClienteDAOImpl(new ArrayList<>());

        clienteDAO.add();

    }
}
