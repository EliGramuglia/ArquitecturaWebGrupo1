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

    @PostMapping("/cargar-saldo")
    public ResponseEntity<CrearPagoResponseDTO> cargarSaldo(@RequestParam Long idUsuario, @RequestParam Double monto, @RequestParam String descripcion) {
        CrearPagoResponseDTO resp = new CrearPagoResponseDTO();
        resp.setPagoId(UUID.randomUUID().toString());
        resp.setStatus("Carga pendiente");
        resp.setInitPoint("https://mock.mercadopago.com/cargar-saldo/" + resp.getPagoId());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{pagoId}/estado")
    public ResponseEntity<EstadoPagoResponseDTO> obtenerEstado(@PathVariable Long pagoId) {
        EstadoPagoResponseDTO resp = new EstadoPagoResponseDTO();
        resp.setPagoId(pagoId);
        // lógica mock: siempre approved
        resp.setStatus("Carga aprobada.");
        return ResponseEntity.ok(resp);
    }
}