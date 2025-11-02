package org.example.viaje.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.PausaResponseDTO;
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
    public ResponseEntity<PausaResponseDTO> iniciarPausa(@PathVariable Long viajeId){
        PausaResponseDTO pausa = service.iniciarPausa(viajeId);
        return ResponseEntity.ok(pausa);
    }

    // Finalizar una pausa
    @PutMapping("/{viajeId}/pausas/{pausaId}/finalizar")
    public ResponseEntity<PausaResponseDTO> finalizarPausa(@PathVariable Long viajeId,
                                                   @PathVariable Long pausaId) {
        PausaResponseDTO pausaFinalizada = service.finalizarPausa(viajeId, pausaId);
        return ResponseEntity.ok(pausaFinalizada);
    }

    // Listar todas las pausas de un viaje
    @GetMapping("/{viajeId}/pausas")
    public ResponseEntity<List<PausaResponseDTO>> getPausas(@PathVariable Long viajeId){
        List<PausaResponseDTO> pausas = service.getPausas(viajeId);
        return ResponseEntity.ok(pausas);
    }

}

// VALID SE USA EN EL CONTROLLER, no en el service ??
// HACEMOS UNA INERFACE PARA EL SERVICE ???? -> BUENA PRACTICA PARA LOS TESTS Y ESCALABILIDAD
// EN LOS METODOS UPDATES SACAR EL SET(ID)->No es necesario porque el objeto ya tiene ese id (lo encontraste por findById).
/*En el update, después de hacer el save, mejor guardar el resultado en una variable y devolverlo (como hiciste en el save) para asegurarte que el DTO contenga datos actualizados:
Viaje viajeGuardado = viajeRepository.save(viajeEditar);
return ViajeMapper.convertToDTO(viajeGuardado);*/