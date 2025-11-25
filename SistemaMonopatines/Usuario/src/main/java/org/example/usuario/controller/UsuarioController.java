package org.example.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.usuario.client.monopatin.dto.request.MonopatinRequestDTO;
import org.example.usuario.client.monopatin.dto.response.MonopatinResponseDTO;
import org.example.usuario.client.pagomock.dto.response.CrearPagoResponseDTO;
import org.example.usuario.client.pagomock.dto.response.EstadoPagoResponseDTO;
import org.example.usuario.client.viaje.dto.UsuarioDTO;
import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsoMonopatinCuentaDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios y sus servicios asociados")
public class UsuarioController {
    private final UsuarioService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Crea un usuario validando el contenido del request. Si los datos son inválidos devuelve un error."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos / error en la creación",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioRequestDTO usuario){
        try {
            UsuarioResponseDTO nuevo = service.save(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
        List<UsuarioResponseDTO> usuarios = service.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(
            @Parameter(description = "ID del usuario", example = "12")
            @PathVariable Long id){
        UsuarioResponseDTO usuario = service.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Actualizar un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @Parameter(description = "ID del usuario", example = "12")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioEditado = service.update(id, usuario);
        return ResponseEntity.ok(usuarioEditado);
    }


    @Operation(summary = "Eliminar un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> delete(
            @Parameter(description = "ID del usuario") @PathVariable Long id){
       service.delete(id);
       return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS PARA LOS SERVICIOS --------------------------*/
    @Operation(
            summary = "Crear un monopatín (servicio externo)",
            description = "Envía el request al microservicio de Monopatines mediante Feign"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatín creado correctamente")
    })
    @PostMapping("/monopatines/create")
    public ResponseEntity<MonopatinResponseDTO> create(@RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO nuevo = service.saveMonopatin(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(
            summary = "Buscar monopatines cercanos",
            description = "Devuelve la lista de monopatines disponibles cerca de una geolocalización"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado generado correctamente")
    })
    @GetMapping("/monopatines-cercanos")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesCercanos(
            @Parameter(description = "Latitud del usuario", example = "-34.6097") @RequestParam double latitud,
            @Parameter(description = "Longitud del usuario", example = "-58.3816") @RequestParam double longitud)  {
        List<MonopatinResponseDTO> cercanos = service.buscarMonopatinesCercanos(latitud, longitud);
        return ResponseEntity.ok(cercanos);
    }

    @Operation(
            summary = "Obtener uso de monopatines por un usuario",
            description = "Permite consultar el uso de monopatines por fecha para un usuario, utilizado por reportes"
    )
    @GetMapping("/{idUsuario}/uso-monopatines")
    public ResponseEntity<UsoMonopatinCuentaDTO> obtenerUsoMonopatines(
            @Parameter(description = "ID del usuario", example = "12")
            @PathVariable Long idUsuario,

            @Parameter(description = "Fecha de inicio del período", example = "2024-01-01")
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

            @Parameter(description = "Fecha de fin del período", example = "2024-01-31")
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        UsoMonopatinCuentaDTO resultado = service.obtenerUsoMonopatines(idUsuario, inicio, fin);
        return ResponseEntity.ok(resultado);
    }

    // Endpoint que necesita el feigClient que esta en viaje
    @Operation(
            summary = "Obtener usuario para Feign",
            description = "Endpoint utilizado por el microservicio de viajes para obtener datos del usuario"
    )
    @ApiResponse(responseCode = "200", description = "Usuario enviado correctamente")
    @GetMapping("/feign/{id}")
    public ResponseEntity<UsuarioDTO> findByIdFeign(
            @Parameter(description = "ID del usuario", example = "5")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerUsuarioDTO(id));
    }

    @Operation(
            summary = "Recarga de saldo",
            description = "Endpoint utilizado para la recarga de saldo en la cuenta de un usuario determinado"
    )
    @ApiResponse(responseCode = "200", description = "Pago realizado correctamente")
    @PostMapping("/cargar-saldo")
    public CrearPagoResponseDTO cargarSaldo(@RequestParam Long idUsuario, @RequestParam Double monto, @RequestParam String descripcion) {
        return service.cargarSaldo(idUsuario, monto, descripcion);
    }

    @GetMapping("/{pagoId}/estado-carga")
    public EstadoPagoResponseDTO obtenerEstado(@PathVariable Long pagoId) {
        return service.consultarEstado(pagoId);
    }

}