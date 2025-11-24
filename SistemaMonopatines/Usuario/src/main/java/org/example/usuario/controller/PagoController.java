package org.example.usuario.controller;

import lombok.RequiredArgsConstructor;
import org.example.usuario.client.pagomock.dto.response.CrearPagoResponseDTO;
import org.example.usuario.client.pagomock.dto.response.EstadoPagoResponseDTO;
import org.example.usuario.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/iniciar")
    public ResponseEntity<CrearPagoResponseDTO> iniciar(
            @RequestParam Long idUsuario,
            @RequestParam Double monto
    ) {
        CrearPagoResponseDTO resp = pagoService.iniciarPago(idUsuario, monto, "Recarga de saldo");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{pagoId}/estado")
    public ResponseEntity<EstadoPagoResponseDTO> estado(@PathVariable String pagoId) {
        return ResponseEntity.ok(pagoService.consultarEstado(pagoId));
    }

}