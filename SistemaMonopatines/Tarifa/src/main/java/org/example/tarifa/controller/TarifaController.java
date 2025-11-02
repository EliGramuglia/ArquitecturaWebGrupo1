package org.example.tarifa.controller;

import lombok.AllArgsConstructor;
import org.example.tarifa.dto.request.TarifaRequestDTO;
import org.example.tarifa.dto.response.TarifaResponseDTO;
import org.example.tarifa.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tarifas")
public class TarifaController {

    private final TarifaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<TarifaResponseDTO> create(@RequestBody TarifaRequestDTO tarifa){
        TarifaResponseDTO nuevo = service.save(tarifa);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<TarifaResponseDTO>> getAll(){
        List<TarifaResponseDTO> tarifas = service.findAll();
        return ResponseEntity.ok(tarifas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> getById(@PathVariable Long id){
        TarifaResponseDTO tarifa = service.findById(id);
        return ResponseEntity.ok(tarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> update(@PathVariable Long id, @RequestBody TarifaRequestDTO tarifa){
        TarifaResponseDTO tarifaEditada = service.update(id, tarifa);
        return ResponseEntity.ok(tarifaEditada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
