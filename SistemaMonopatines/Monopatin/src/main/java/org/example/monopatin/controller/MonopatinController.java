package org.example.monopatin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.monopatin.dto.request.MonopatinRequestDTO;
import org.example.monopatin.dto.request.ReporteUsoRequestDTO;
import org.example.monopatin.dto.response.MonopatinResponseDTO;
import org.example.monopatin.dto.response.ReporteUsoResponseDTO;
import org.example.monopatin.service.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/monopatines")
@Tag(name = "Monopatín", description = "Endpoints para gestión de monopatines")
public class MonopatinController {

    private final MonopatinService service;

    /*-------------------------- CRUD --------------------------*/

    @Operation(summary = "Crear monopatín", description = "Crea un nuevo monopatín en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatin creado correctamente",
                    content = @Content(schema = @Schema(implementation = MonopatinResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<MonopatinResponseDTO> create(
            @RequestBody MonopatinRequestDTO monopatin) {
        MonopatinResponseDTO nuevo = service.save(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Listar monopatines", description = "Devuelve todos los monopatines registrados.")
    @GetMapping("")
    public ResponseEntity<List<MonopatinResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar monopatín por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el monopatín")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> getById(
            @Parameter(description = "ID del monopatín") @PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Actualizar monopatín")
    @PutMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> update(
            @PathVariable String id,
            @RequestBody MonopatinRequestDTO monopatin) {
        return ResponseEntity.ok(service.update(id, monopatin));
    }

    @Operation(summary = "Eliminar monopatín")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del monopatín a eliminar") @PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*-------------------------- SERVICIOS --------------------------*/

    @Operation(summary = "Cambiar el estado del monopatín",
            description = "Estados posibles: disponible, en_uso, mantenimiento, fuera_de_servicio.")
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<MonopatinResponseDTO> cambiarEstadoMonopatin(
            @PathVariable String id,
            @PathVariable String estado) {
        return ResponseEntity.ok(service.cambiarEstadoMonopatin(id, estado));
    }

    @Operation(summary = "Ubicar monopatín en una parada")
    @PutMapping("/{id}/parada/{paradaId}")
    public ResponseEntity<MonopatinResponseDTO> ubicarEnParada(
            @PathVariable String id,
            @Parameter(description = "ID de la parada") @PathVariable Long paradaId) {
        return ResponseEntity.ok(service.ubicarEnParada(id, paradaId));
    }

    @Operation(summary = "Obtener monopatines cercanos",
            description = "Devuelve monopatines dentro de un radio cercano a las coordenadas enviadas.")
    @GetMapping("/cercanos")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesCercanos(
            @RequestParam double latitud,
            @RequestParam double longitud) {
        return ResponseEntity.ok(service.buscarMonopatinesCercanos(latitud, longitud));
    }

    @Operation(summary = "Generar reporte de uso",
            description = "Retorna un reporte con distancia recorrida, tiempo de uso y otros datos.")
    @PostMapping("/reporte-uso")
    public ResponseEntity<ReporteUsoResponseDTO> generarReporteUso(
            @RequestBody ReporteUsoRequestDTO request) {
        return ResponseEntity.ok(service.generarReporteUso(request));
    }

}
