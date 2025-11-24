package org.example.viaje.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/viajes/tarifas")
@Tag(name = "Tarifas", description = "Gestión de tarifas de viajes y ajustes de precios")
public class TarifaController {
    private final TarifaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
    // habilite los nuevos precios.

    // Ajustar tarifa (solo administrador) -> Se crea una nueva, ya que las anteriores sirven para llevar un historial
    @Operation(summary = "Ajustar tarifa",
            description = "Crea una nueva tarifa con nuevos precios a partir de la fecha indicada. Solo administrador.")
    @PostMapping("/ajustar")
    public ResponseEntity<TarifaResponseDTO> create(@RequestBody TarifaRequestDTO tarifa){
        TarifaResponseDTO nuevo = service.save(tarifa);
        return ResponseEntity.ok(nuevo);
    }

    // Método que actualiza la fecha de fin de vigencia
    @Operation(summary = "Actualizar fecha de fin de vigencia",
            description = "Modifica la fecha de fin de vigencia de la tarifa activa")
    @PutMapping("/fecha-fin")
    public ResponseEntity<TarifaResponseDTO> updateFechaFinVigencia(
            @Parameter(description = "Nueva fecha de fin de vigencia", example = "2025-12-31")
            @RequestParam("fecha-fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFechaFin){
        TarifaResponseDTO tarifaActualizada = service.updateFechaFinTarifaActiva(nuevaFechaFin);
        return ResponseEntity.ok(tarifaActualizada);
    }

    // Obtener todas las tarifas (ver el historial)
    @Operation(summary = "Listar todas las tarifas",
            description = "Obtiene el historial completo de tarifas")
    @GetMapping("")
    public ResponseEntity<List<TarifaResponseDTO>> getAll(){
        List<TarifaResponseDTO> tarifas = service.findAll();
        return ResponseEntity.ok(tarifas);
    }

    // Obtener la tarifa activa actual

    @Operation(summary = "Obtener tarifa activa",
            description = "Devuelve la tarifa actualmente vigente")
    @GetMapping("/activa")
    public ResponseEntity<TarifaResponseDTO> getById(){
        TarifaResponseDTO tarifaActiva = service.getTarifaActiva();
        return ResponseEntity.ok(tarifaActiva);
    }

}
