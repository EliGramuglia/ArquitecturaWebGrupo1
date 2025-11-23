package org.example.client;

import org.example.config.FeignConfig;
import org.example.dto.response.UsoMonopatinUsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "viaje-service", url = "http://localhost:8084/api/viajes", configuration = FeignConfig.class)
public interface ViajeClient {

    // Ya lo tenés en ViajeController:
    @GetMapping("/uso-monopatin/cantidad")
    UsoMonopatinUsuarioResponseDTO contarUsoMonopatines(
            @RequestParam Long idUsuario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    );

    // acá podrías agregar otros métodos si creas endpoints nuevos,
    // por ejemplo para calcular distancia/precio estimado.
}