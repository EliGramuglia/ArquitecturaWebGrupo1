package org.example.viaje.controller;

import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.entity.Viaje;
import org.example.viaje.service.ViajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viajes")
@AllArgsConstructor
public class ViajeController {
    private final ViajeService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<ViajeResponseDTO> create(@RequestBody ViajeRequestDTO viaje){
        ViajeResponseDTO viajeNuevo = service.save(viaje);
        return ResponseEntity.ok(viajeNuevo);
    }
}
