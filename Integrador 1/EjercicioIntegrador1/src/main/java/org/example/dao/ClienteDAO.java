package org.example.dao;

import org.example.entity.Cliente;

import java.io.IOException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAllByMaxFacturacion();
    void add() throws IOException;
}
