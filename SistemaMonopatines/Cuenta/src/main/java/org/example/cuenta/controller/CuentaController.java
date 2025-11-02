package org.example.cuenta.controller;

import lombok.AllArgsConstructor;
import org.example.cuenta.dto.request.CuentaRequestDTO;
import org.example.cuenta.dto.response.CuentaResponseDTO;
import org.example.cuenta.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<CuentaResponseDTO> create(@RequestBody CuentaRequestDTO cuenta){
        CuentaResponseDTO nuevo = service.save(cuenta);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<CuentaResponseDTO>> getAll(){
        List<CuentaResponseDTO> cuentas = service.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> getById(@PathVariable Long nroCuenta){
        CuentaResponseDTO cuenta = service.findById(nroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> update(@PathVariable Long nroCuenta, @RequestBody CuentaRequestDTO cuenta){
        CuentaResponseDTO cuentaEditada = service.update(nroCuenta, cuenta);
        return ResponseEntity.ok(cuentaEditada);
    }

    @DeleteMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> delete(@PathVariable Long nroCuenta){
       service.delete(nroCuenta);
       return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS SERVICIOS --------------------------*/

    @PutMapping("/{nroCuenta}/estado/{estado}")
    public ResponseEntity<CuentaResponseDTO> anularCuenta(@PathVariable Long nroCuenta, @PathVariable String estado) {
        CuentaResponseDTO cuentaAnulada = service.anularCuenta(nroCuenta, estado);
        return ResponseEntity.ok(cuentaAnulada);
    }

}
/*
@PreAuthorize
@PreAuthorize(ADMINISTRADOR)
 */