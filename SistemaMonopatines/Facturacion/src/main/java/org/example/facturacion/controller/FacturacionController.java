package org.example.facturacion.controller;

import lombok.AllArgsConstructor;
import org.example.facturacion.dto.request.FacturacionRequestDTO;
import org.example.facturacion.dto.response.FacturacionResponseDTO;
import org.example.facturacion.service.FacturacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturaciones")
@AllArgsConstructor
public class FacturacionController {
    private final FacturacionService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<FacturacionResponseDTO> create(@RequestBody FacturacionRequestDTO facturacion){
        FacturacionResponseDTO nuevo = service.save(facturacion);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<FacturacionResponseDTO>> getAll(){
        List<FacturacionResponseDTO> facturacion = service.findAll();
        return ResponseEntity.ok(facturacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturacionResponseDTO> getById(@PathVariable Long id){
        FacturacionResponseDTO facturacion = service.findById(id);
        return ResponseEntity.ok(facturacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturacionResponseDTO> update(@PathVariable Long id, @RequestBody FacturacionRequestDTO facturacion){
        FacturacionResponseDTO facturaEditado = service.update(id, facturacion);
        return ResponseEntity.ok(facturaEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FacturacionResponseDTO> delete(@PathVariable Long id){
       service.delete(id);
       return ResponseEntity.noContent().build();
    }
}
/*
@PreAuthorize
@PreAuthorize(ADMINISTRADOR)
 */