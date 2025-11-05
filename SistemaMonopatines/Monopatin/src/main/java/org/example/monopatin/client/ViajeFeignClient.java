package org.example.monopatin.client;

import org.example.monopatin.client.viaje.dto.request.ViajeRequestDTO;
import org.example.monopatin.client.viaje.dto.response.ViajeResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "viaje-service", url = "http://localhost:8083/viajes") // cambiar por puerto que vamos a utilizar para viajes
public interface ViajeFeignClient {

    @PostMapping
    ResponseEntity<ViajeResponseDTO> create(@RequestBody ViajeRequestDTO dto);

    @GetMapping("/{id}")
    ResponseEntity<ViajeResponseDTO> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<ViajeResponseDTO>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

}