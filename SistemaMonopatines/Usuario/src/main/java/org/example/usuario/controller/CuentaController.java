package org.example.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.usuario.dto.request.CuentaRequestDTO;
import org.example.usuario.dto.response.CuentaResponseDTO;
import org.example.usuario.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping ("/api/usuarios/cuentas")
@Tag(name = "Cuentas", description = "Operaciones relacionadas con las cuentas de los usuarios")
public class CuentaController {
    private final CuentaService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @Operation(
            summary = "Crear una nueva cuenta",
            description = "Crea una cuenta asociada a un usuario base y devuelve la cuenta generada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta creada con éxito",
                    content = @Content(schema = @Schema(implementation = CuentaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<CuentaResponseDTO> create(@RequestBody CuentaRequestDTO cuenta){
        CuentaResponseDTO nuevo = service.save(cuenta);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(
            summary = "Obtener todas las cuentas",
            description = "Devuelve un listado completo de todas las cuentas registradas"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")

    @GetMapping("")
    public ResponseEntity<List<CuentaResponseDTO>> getAll(){
        List<CuentaResponseDTO> cuentas = service.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @Operation(
            summary = "Obtener una cuenta por número de cuenta"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe una cuenta con ese número")
    })
    @GetMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> getById(
            @Parameter(description = "Número de cuenta", example = "12345")
            @PathVariable Long nroCuenta){
        CuentaResponseDTO cuenta = service.findById(nroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Operation(
            summary = "Actualizar una cuenta",
            description = "Modifica los datos de una cuenta existente"
    )
    @PutMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> update(
            @Parameter(description = "Número de cuenta", example = "12345")
            @PathVariable Long nroCuenta,
            @RequestBody CuentaRequestDTO cuenta){
        CuentaResponseDTO cuentaEditada = service.update(nroCuenta, cuenta);
        return ResponseEntity.ok(cuentaEditada);
    }

    @Operation(
            summary = "Eliminar una cuenta",
            description = "Elimina una cuenta permanentemente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @DeleteMapping("/{nroCuenta}")
    public ResponseEntity<CuentaResponseDTO> delete(
            @Parameter(description = "Número de cuenta") @PathVariable Long nroCuenta){
        service.delete(nroCuenta);
        return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS SERVICIOS --------------------------*/
    @Operation(
            summary = "Cambiar el estado de una cuenta",
            description = "Permite anular, activar o modificar el estado de una cuenta"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/{nroCuenta}/estado/{estado}")
    public ResponseEntity<CuentaResponseDTO> anularCuenta(
            @Parameter(description = "Número de cuenta") @PathVariable Long nroCuenta,
            @Parameter(description = "Nuevo estado de la cuenta", example = "ANULADA") @PathVariable String estado) {
        CuentaResponseDTO cuentaAnulada = service.anularCuenta(nroCuenta, estado);
        return ResponseEntity.ok(cuentaAnulada);
    }

    @Operation(
            summary = "Verificar y renovar cupo",
            description = "Verifica si el cupo de la cuenta está vencido y lo renueva si corresponde"
    )
    @ApiResponse(responseCode = "200", description = "Cupo verificado y renovado si era necesario")
    @GetMapping("/{nroCuenta}/verificar-cupo")
    public ResponseEntity<CuentaResponseDTO> verificarCupo(
            @Parameter(description = "Número de cuenta") @PathVariable Long nroCuenta) {
        CuentaResponseDTO cuenta = service.verificarYRnovarCupo(nroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Operation(
            summary = "Obtener una cuenta por ID de usuario",
            description = "Devuelve la cuenta asociada a un usuario específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe una cuenta para ese usuario")
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorUsuario(
            @Parameter(description = "ID del usuario", example = "7")
            @PathVariable Long idUsuario) {
        CuentaResponseDTO cuenta = service.obtenerCuentaPorUsuario(idUsuario);
        return ResponseEntity.ok(cuenta);
    }

}
