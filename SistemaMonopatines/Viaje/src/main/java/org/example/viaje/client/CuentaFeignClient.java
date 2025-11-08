package org.example.viaje.client;

import org.example.viaje.client.cuenta.dto.response.CuentaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/usuarios/cuentas")
@Component
public interface CuentaFeignClient {
    @GetMapping("/usuario/{idUsuario}")
    CuentaResponseDTO obtenerCuentaPorUsuario(@PathVariable Long idUsuario);

    @PutMapping("/{nroCuenta}")
    void update(@RequestBody CuentaResponseDTO cuenta, @RequestParam Long nroCuenta);

    // verificar y renovar cupo
    @GetMapping("/{nroCuenta}/verificar-cupo")
    CuentaResponseDTO verificarCupo(@PathVariable Long nroCuenta);
}
