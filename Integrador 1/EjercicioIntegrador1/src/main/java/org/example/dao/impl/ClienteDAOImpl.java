package org.example.dao.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.App;
import org.example.dao.ClienteDAO;
import org.example.entity.Cliente;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ClienteDAOImpl implements ClienteDAO {

    private final List<Cliente> listClientes;

    public ClienteDAOImpl(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    @Override
    public List<Cliente> findAllByMaxFacturacion() {
        return null;
    }

    @Override
    public void add() throws IOException {

        try (CSVParser client = CSVFormat.DEFAULT.builder()
                .setHeader()                 // interpreta la primera fila como header
                .setSkipHeaderRecord(true)   // salta la fila de cabecera al iterar
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(ClienteDAOImpl.class.getResourceAsStream("/clientes.csv"))))) {

            for (CSVRecord record : client) {
                String id = record.get("idCliente");
                String nombre = record.get("nombre");
                String email = record.get("email");

                System.out.println(id);
                System.out.println(nombre);
                System.out.println(email);
                //insert clientes
                listClientes.add(new Cliente(Integer.valueOf(id), nombre, email));
            }
        }

    }
}
