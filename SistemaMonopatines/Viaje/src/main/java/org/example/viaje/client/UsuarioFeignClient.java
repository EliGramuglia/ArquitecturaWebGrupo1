package org.example.viaje.client;

import org.example.viaje.client.dto.response.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/api/usuarios")
@Component
public interface UsuarioFeignClient {

    @GetMapping("/feign/{id}")
    UsuarioDTO findByIdFeign(@PathVariable("id") Long id);
}
