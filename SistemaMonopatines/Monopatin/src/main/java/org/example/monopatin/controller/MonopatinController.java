package org.example.monopatin.controller;

import lombok.AllArgsConstructor;
import org.example.monopatin.dto.request.MonopatinRequestDTO;
import org.example.monopatin.dto.response.MonopatinResponseDTO;
import org.example.monopatin.service.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/monopatines")
public class MonopatinController {

    private final MonopatinService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<MonopatinResponseDTO> create(@RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO nuevo = service.save(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<MonopatinResponseDTO>> getAll(){
        List<MonopatinResponseDTO> monopatines = service.findAll();
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> getById(@PathVariable String id){
        MonopatinResponseDTO monopatin = service.findById(id);
        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> update(@PathVariable String id, @RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO monopatinEditado = service.update(id, monopatin);
        return ResponseEntity.ok(monopatinEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS PARA EL SERVICIOS --------------------------*/
    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<MonopatinResponseDTO> cambiarEstadoMonopatin(@PathVariable String id, @PathVariable String estado){
        MonopatinResponseDTO monopatinEstado = service.enMantenimiento(id, estado);
        return ResponseEntity.ok(monopatinEstado);
    }
}
