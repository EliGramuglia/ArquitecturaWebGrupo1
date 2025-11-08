package org.example.viaje.client;

import org.example.viaje.client.dto.response.UsuarioViajesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/usuarios")
@Component
public interface UsuarioFeignClient {

    @GetMapping("/{id}")
    UsuarioViajesDTO findById(@PathVariable("id") Long id);
}
