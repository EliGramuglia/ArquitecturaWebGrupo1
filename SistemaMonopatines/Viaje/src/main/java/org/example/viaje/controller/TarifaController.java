package org.example.viaje.controller;

import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.TarifaRequestDTO;
import org.example.viaje.dto.response.TarifaResponseDTO;
import org.example.viaje.service.TarifaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/viajes/tarifas")
public class TarifaController {
    private final TarifaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
    // habilite los nuevos precios.

    // Ajustar tarifa (solo administrador) -> Se crea una nueva, ya que las anteriores sirven para llevar un historial
    @PostMapping("/ajustar")
    public ResponseEntity<TarifaResponseDTO> create(@RequestBody TarifaRequestDTO tarifa){
        TarifaResponseDTO nuevo = service.save(tarifa);
        return ResponseEntity.ok(nuevo);
    }

    // Método que actualiza la fecha de fin de vigencia
    @PutMapping("/fecha-fin")
    public ResponseEntity<TarifaResponseDTO> updateFechaFinVigencia(@RequestParam("fecha-fin")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFechaFin){
        TarifaResponseDTO tarifaActualizada = service.updateFechaFinTarifaActiva(nuevaFechaFin);
        return ResponseEntity.ok(tarifaActualizada);
    }

    // Obtener todas las tarifas (ver el historial)
    @GetMapping("")
    public ResponseEntity<List<TarifaResponseDTO>> getAll(){
        List<TarifaResponseDTO> tarifas = service.findAll();
        return ResponseEntity.ok(tarifas);
    }

    // Obtener la tarifa activa actual
    @GetMapping("/activa")
    public ResponseEntity<TarifaResponseDTO> getById(){
        TarifaResponseDTO tarifaActiva = service.getTarifaActiva();
        return ResponseEntity.ok(tarifaActiva);
    }

}
