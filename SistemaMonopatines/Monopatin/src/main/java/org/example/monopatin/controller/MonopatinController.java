package org.example.monopatin.controller;

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
        MonopatinResponseDTO monopatinEstado = service.cambiarEstadoMonopatin(id, estado);
        return ResponseEntity.ok(monopatinEstado);
    }

    @PutMapping("/{id}/parada/{paradaId}")
    public ResponseEntity<MonopatinResponseDTO> ubicarEnParada(@PathVariable String id,
                                                               @PathVariable Long paradaId) {
        MonopatinResponseDTO dto = service.ubicarEnParada(id, paradaId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/cercanos")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesCercanos(@RequestParam double latitud,
                                                                             @RequestParam double longitud) {
        List<MonopatinResponseDTO> monopatinesCercanos = service.buscarMonopatinesCercanos(latitud, longitud);
        return ResponseEntity.ok(monopatinesCercanos);
    }

    @PostMapping("/reporte-uso")
    public ResponseEntity<ReporteUsoResponseDTO> generarReporteUso(@RequestBody ReporteUsoRequestDTO request) {
        ReporteUsoResponseDTO reporte = service.generarReporteUso(request);
        return ResponseEntity.ok(reporte);
    }

}