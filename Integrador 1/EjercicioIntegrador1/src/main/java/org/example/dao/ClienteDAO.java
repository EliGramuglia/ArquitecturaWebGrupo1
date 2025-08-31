package org.example.dao;

import org.example.entity.Cliente;

import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAll();
    void add(List<Cliente> c);
}
