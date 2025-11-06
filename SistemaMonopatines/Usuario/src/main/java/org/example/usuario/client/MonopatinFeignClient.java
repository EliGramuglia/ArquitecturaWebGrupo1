package org.example.usuario.client;

import org.example.usuario.client.monopatin.dto.request.MonopatinRequestDTO;
import org.example.usuario.client.monopatin.dto.response.MonopatinResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "monopatin-service", url = "http://localhost:8081/monopatines")
@Component
public interface MonopatinFeignClient {

        @PostMapping()
        ResponseEntity<MonopatinResponseDTO> create(@RequestBody MonopatinRequestDTO monopatin);

        @GetMapping("/cercanos")
        List<MonopatinResponseDTO> getMonopatinesCercanos(
                @RequestParam("latitud") double latitud,
                @RequestParam("longitud") double longitud
        );
}