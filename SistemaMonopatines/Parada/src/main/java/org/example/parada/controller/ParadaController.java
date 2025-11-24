package org.example.parada.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.parada.dto.request.ParadaRequestDTO;
import org.example.parada.dto.response.ParadaResponseDTO;
import org.example.parada.service.ParadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paradas")
@AllArgsConstructor
@Tag(name = "Paradas", description = "Endpoints para la gestión de paradas")
public class ParadaController {

    private final ParadaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @Operation(summary = "Crear una nueva parada", description = "Crea una parada con nombre, latitud y longitud")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parada creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<ParadaResponseDTO> create(@Valid @RequestBody ParadaRequestDTO dto) {
        ParadaResponseDTO parada = service.create(dto);
        return ResponseEntity.ok(parada);
    }

    @Operation(summary = "Obtener todas las paradas", description = "Devuelve una lista con todas las paradas registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ParadaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Obtener una parada por ID", description = "Devuelve la información de una parada existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parada encontrada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la parada con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Actualizar una parada", description = "Modifica los datos de una parada existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parada actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "La parada no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody ParadaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Eliminar una parada", description = "Elimina una parada por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Parada eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La parada no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}