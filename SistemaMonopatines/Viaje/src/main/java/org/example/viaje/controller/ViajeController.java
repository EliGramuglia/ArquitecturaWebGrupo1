package org.example.viaje.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.PausaRequestDTO;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.PausaResponseDTO;
import org.example.viaje.dto.response.TotalFacturadoDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.service.ViajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/viajes")
@AllArgsConstructor
public class ViajeController {
    private final ViajeService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<ViajeResponseDTO> create(@Valid @RequestBody ViajeRequestDTO viaje){
        ViajeResponseDTO viajeNuevo = service.save(viaje);
        return ResponseEntity.ok(viajeNuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<ViajeResponseDTO>> getAll(){
        List<ViajeResponseDTO> viajes = service.findAll();
        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> getById(@PathVariable Long id){
        ViajeResponseDTO viaje = service.findById(id);
        return ResponseEntity.ok(viaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody ViajeRequestDTO viajeDTO){
        ViajeResponseDTO viajeEditado = service.update(id, viajeDTO);
        return ResponseEntity.ok(viajeEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // caso exitoso 204
    }

    /*-------------------- ENDPOINTS ANIDADOS PARA PAUSAR EL VIAJE -----------------------*/
    // Crear una pausa para un viaje específico
    @PostMapping("/{viajeId}/pausas")
    public ResponseEntity<PausaResponseDTO> agregarPausa(@PathVariable Long viajeId,
                                                         @RequestBody PausaRequestDTO dto){
        PausaResponseDTO pausa = service.createPausa(viajeId, dto);
        return ResponseEntity.ok(pausa);
    }

    // Listar todas las pausas de un viaje
    @GetMapping("/{viajeId}/pausas")
    public ResponseEntity<List<PausaResponseDTO>> getPausas(@PathVariable Long viajeId){
        List<PausaResponseDTO> pausas = service.getPausas(viajeId);
        return ResponseEntity.ok(pausas);
    }

    /*------------------------- ENDPOINTS SERVICIOS  ----------------------------*/
    // Como administrador quiero consultar el total facturado en un rango de meses de cierto año
    // Ejemplo: http://localhost:8080/viajes/total-facturado?anio=2025&mesInicio=1&mesFin=6
    @GetMapping("/total-facturado")
    public ResponseEntity<TotalFacturadoDTO> getTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {

        TotalFacturadoDTO total = service.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(total);
    }
}

