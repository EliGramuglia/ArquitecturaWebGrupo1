package org.example.controller;

import org.example.dto.request.CrearPagoRequestDTO;
import org.example.dto.response.CrearPagoResponseDTO;
import org.example.dto.response.EstadoPagoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagos-mock")
public class PagoMockController {

    @PostMapping("/crear")
    public ResponseEntity<CrearPagoResponseDTO> crearPago(@RequestBody CrearPagoRequestDTO request) {
        CrearPagoResponseDTO resp = new CrearPagoResponseDTO();
        resp.setPagoId(UUID.randomUUID().toString());
        resp.setStatus("pending");
        resp.setInitPoint("https://mock.mercadopago.com/pagar/" + resp.getPagoId());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{pagoId}/estado")
    public ResponseEntity<EstadoPagoResponseDTO> obtenerEstado(@PathVariable String pagoId) {
        EstadoPagoResponseDTO resp = new EstadoPagoResponseDTO();
        resp.setPagoId(pagoId);
        // lógica mock: siempre approved
        resp.setStatus("approved");
        return ResponseEntity.ok(resp);
    }
}