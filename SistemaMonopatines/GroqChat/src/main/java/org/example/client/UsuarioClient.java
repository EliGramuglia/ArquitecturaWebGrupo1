package org.example.client;

import org.example.config.FeignConfig;
import org.example.dto.response.UsoMonopatinCuentaDTO;
import org.example.dto.response.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/api/usuarios", configuration = FeignConfig.class)
public interface UsuarioClient {

    @GetMapping("/feign/{id}")
    UsuarioResponseDTO findById(@PathVariable("id") Long id);

    @GetMapping("/{idUsuario}/uso-monopatines")
    UsoMonopatinCuentaDTO obtenerUsoMonopatines(
            @PathVariable Long idUsuario,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    );

}
