package org.example.monopatin.client;

import org.example.monopatin.client.parada.dto.response.ParadaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "parada-service", url = "http://localhost:8082/api/paradas")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<ParadaResponseDTO> getById(@PathVariable Long id);

}