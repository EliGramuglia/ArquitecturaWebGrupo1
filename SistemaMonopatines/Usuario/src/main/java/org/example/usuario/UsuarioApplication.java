package org.example.usuario;

import org.example.usuario.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class UsuarioApplication {
    @Autowired
    private static RolService service;

    public UsuarioApplication(RolService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(UsuarioApplication.class, args);
        List<String> roles = new ArrayList<>();
        roles.add("ADMINISTRADOR");
        roles.add("CLIENTE");
        roles.add("MANTENIMIENTO");

        service.create(roles);
    }


}