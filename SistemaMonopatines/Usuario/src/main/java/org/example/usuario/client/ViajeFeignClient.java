package org.example.usuario.client;

import org.example.usuario.client.viaje.dto.UsoMonopatinUsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "viaje-service", url = "${viaje.service.url}")
public interface ViajeFeignClient {
    @GetMapping("/viajes/usuarios/{id}/cantidad-viajes")
    UsoMonopatinUsuarioDTO getCantidadViajesUsuario(
            @PathVariable("id") Long idUsuario,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    );
}
