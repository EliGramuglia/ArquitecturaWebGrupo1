package org.example.parada.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.parada.dto.request.ParadaRequestDTO;
import org.example.parada.dto.response.ParadaResponseDTO;
import org.example.parada.service.ParadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paradas")
@AllArgsConstructor
public class ParadaController {

    private final ParadaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping
    public ResponseEntity<ParadaResponseDTO> create(@Valid @RequestBody ParadaRequestDTO dto) {
        ParadaResponseDTO parada = service.create(dto);
        return ResponseEntity.ok(parada);
    }

    @GetMapping
    public ResponseEntity<List<ParadaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody ParadaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}