package org.example.viaje.client;

import org.example.viaje.client.cuenta.dto.request.CuentaRequestDTO;
import org.example.viaje.client.cuenta.dto.response.CuentaResponseDTO;
import org.example.viaje.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/api/usuarios/cuentas", configuration = FeignConfig.class)
@Component
public interface CuentaFeignClient {
    @GetMapping("/usuario/{idUsuario}")
    CuentaResponseDTO obtenerCuentaPorUsuario(@PathVariable Long idUsuario);

    @PutMapping("/{nroCuenta}")
    void update(@RequestBody CuentaRequestDTO cuenta, @RequestParam Long nroCuenta);

    // verificar y renovar cupo
    @GetMapping("/{nroCuenta}/verificar-cupo")
    CuentaResponseDTO verificarCupo(@PathVariable Long nroCuenta);
}
