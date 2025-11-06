package org.example.viaje.controller;

import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.TarifaRequestDTO;
import org.example.viaje.dto.response.TarifaResponseDTO;
import org.example.viaje.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/viajes/tarifas")
public class TarifaController {

    private final TarifaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
    // habilite los nuevos precios.

    // Ajustar tarifa (solo administrador) -> Se crea una nueva, ya que las anteriores sirven para llevar un historial
    @PostMapping("/ajustar")
    public ResponseEntity<TarifaResponseDTO> create(@RequestBody TarifaRequestDTO tarifa){
        TarifaResponseDTO nuevo = service.save(tarifa);
        return ResponseEntity.ok(nuevo);
    }

    // HACER METODO UPDATE PERO SOLO DE FIN DE VIGENCIA

    // Obtener todas las tarifas (ver el historial)
    @GetMapping("")
    public ResponseEntity<List<TarifaResponseDTO>> getAll(){
        List<TarifaResponseDTO> tarifas = service.findAll();
        return ResponseEntity.ok(tarifas);
    }

    // Obtener la tarifa activa actual
    @GetMapping("/activa")
    public ResponseEntity<TarifaResponseDTO> getById(){
        TarifaResponseDTO tarifaActiva = service.getTarifaActiva();
        return ResponseEntity.ok(tarifaActiva);
    }

}
