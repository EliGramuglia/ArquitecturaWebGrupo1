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

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/

    @Operation(summary = "Crear monopatín", description = "Crea un nuevo monopatín en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatin creado correctamente",
                    content = @Content(schema = @Schema(implementation = MonopatinResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<MonopatinResponseDTO> create(@RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO nuevo = service.save(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Listar monopatines", description = "Devuelve todos los monopatines registrados.")
    @GetMapping("")
    public ResponseEntity<List<MonopatinResponseDTO>> getAll(){
        List<MonopatinResponseDTO> monopatines = service.findAll();
        return ResponseEntity.ok(monopatines);
    }

    @Operation(summary = "Buscar monopatín por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el monopatín")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> getById(@PathVariable String id){
        MonopatinResponseDTO monopatin = service.findById(id);
        return ResponseEntity.ok(monopatin);
    }

    @Operation(summary = "Actualizar monopatín")
    @PutMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> update(@PathVariable String id, @RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO monopatinEditado = service.update(id, monopatin);
        return ResponseEntity.ok(monopatinEditado);
    }


    @Operation(summary = "Eliminar monopatín")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS PARA EL SERVICIOS --------------------------*/
    @Operation(summary = "Cambiar el estado del monopatín",
            description = "Estados posibles: disponible, en_uso, mantenimiento, fuera_de_servicio.")
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<MonopatinResponseDTO> cambiarEstadoMonopatin(@PathVariable String id, @PathVariable String estado){
        MonopatinResponseDTO monopatinEstado = service.cambiarEstadoMonopatin(id, estado);
        return ResponseEntity.ok(monopatinEstado);
    }

    @Operation(summary = "Ubicar monopatín en una parada")
    @PutMapping("/{id}/parada/{paradaId}")
    public ResponseEntity<MonopatinResponseDTO> ubicarEnParada(@PathVariable String id,
                                                               @PathVariable Long paradaId) {
        MonopatinResponseDTO dto = service.ubicarEnParada(id, paradaId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Obtener monopatines cercanos",
            description = "Devuelve monopatines dentro de un radio cercano a las coordenadas enviadas.")
    @GetMapping("/cercanos")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesCercanos(@RequestParam double latitud,
                                                                             @RequestParam double longitud) {
        List<MonopatinResponseDTO> monopatinesCercanos = service.buscarMonopatinesCercanos(latitud, longitud);
        return ResponseEntity.ok(monopatinesCercanos);
    }

    @Operation(summary = "Generar reporte de uso",
            description = "Retorna un reporte con distancia recorrida, tiempo de uso y otros datos.")
    @GetMapping("/reporte-uso")
    public ResponseEntity<ReporteUsoResponseDTO> generarReporteUso(@RequestParam boolean incluirPausas, @RequestParam Double umbralKmMantenimiento) {
        ReporteUsoResponseDTO reporte = service.generarReporteUso(incluirPausas, umbralKmMantenimiento);
        return ResponseEntity.ok(reporte);
    }

}