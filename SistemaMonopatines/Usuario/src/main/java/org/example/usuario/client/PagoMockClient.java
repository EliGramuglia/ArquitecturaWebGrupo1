package org.example.usuario.client;

import org.example.usuario.client.pagomock.dto.request.CrearPagoRequestDTO;
import org.example.usuario.client.pagomock.dto.response.CrearPagoResponseDTO;
import org.example.usuario.client.pagomock.dto.response.EstadoPagoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "pago-mock-service",
        url = "http://localhost:8086/api/pagos-mock"
)
public interface PagoMockClient {

    @PostMapping("/crear")
    CrearPagoResponseDTO crearPago(@RequestBody CrearPagoRequestDTO request);

    @GetMapping("/{pagoId}/estado")
    EstadoPagoResponseDTO obtenerEstado(@PathVariable("pagoId") String pagoId);

}