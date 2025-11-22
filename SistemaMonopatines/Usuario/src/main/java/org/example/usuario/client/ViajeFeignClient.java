package org.example.usuario.client;

import org.example.usuario.client.viaje.dto.UsoMonopatinUsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

//Como necesitás la cantidad de viajes del usuario, el microservicio usuario consulta a viaje

@FeignClient(name = "viaje-service", url = "http://localhost:8084/api/viajes")
public interface ViajeFeignClient {
    @GetMapping("/uso-monopatin/cantidad")
    UsoMonopatinUsuarioDTO contarViajesPorUsuario(
            @RequestParam("idUsuario") Long idUsuario,
            @RequestParam("inicio") String inicio,
            @RequestParam("fin") String fin
    );
}
