package org.example.viaje.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.response.UsuariosConMasViajesDTO;
import org.example.viaje.client.dto.response.ViajeMonopatinResponseDTO;
import org.example.viaje.dto.request.PausaRequestDTO;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.*;
import org.example.viaje.service.ViajeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@AllArgsConstructor
@Tag(name = "Viajes", description = "Gestión de viajes, pausas y reportes")
public class ViajeController {
    private final ViajeService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/

    @Operation(
            summary = "Crear un nuevo viaje",
            description = "Registra un nuevo viaje en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Viaje creado correctamente")
    @PostMapping("")
    public ResponseEntity<ViajeResponseDTO> create(@RequestBody ViajeRequestDTO viaje){
        ViajeResponseDTO viajeNuevo = service.save(viaje);
        return ResponseEntity.ok(viajeNuevo);
    }

    @Operation(
            summary = "Listar todos los viajes",
            description = "Obtiene el listado completo de viajes"
    )
    @GetMapping("")
    public ResponseEntity<List<ViajeResponseDTO>> getAll(){
        List<ViajeResponseDTO> viajes = service.findAll();
        return ResponseEntity.ok(viajes);
    }

    @Operation(
            summary = "Buscar viaje por ID",
            description = "Devuelve un viaje específico"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> getById(
            @Parameter(description = "ID del viaje") @PathVariable Long id){
        ViajeResponseDTO viaje = service.findById(id);
        return ResponseEntity.ok(viaje);
    }

    @Operation(
            summary = "Actualizar un viaje",
            description = "Edita los datos de un viaje existente"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody ViajeRequestDTO viajeDTO){
        ViajeResponseDTO viajeEditado = service.update(id, viajeDTO);
        return ResponseEntity.ok(viajeEditado);
    }


    @Operation(
            summary = "Eliminar un viaje",
            description = "Elimina un viaje por su ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // caso exitoso 204
    }

    /*-------------------- ENDPOINTS ANIDADOS PARA PAUSAR EL VIAJE -----------------------*/
    // Crear una pausa para un viaje específico
    @Operation(
            summary = "Agregar una pausa a un viaje",
            description = "Registra una pausa para el viaje indicado"
    )
    @PostMapping("/{viajeId}/pausas")
    public ResponseEntity<PausaResponseDTO> agregarPausa(@PathVariable Long viajeId,
                                                         @RequestBody PausaRequestDTO dto){
        PausaResponseDTO pausa = service.createPausa(viajeId, dto);
        return ResponseEntity.ok(pausa);
    }

    // Listar todas las pausas de un viaje
    @Operation(
            summary = "Obtener pausas de un viaje",
            description = "Devuelve todas las pausas registradas para un viaje"
    )
    @GetMapping("/{viajeId}/pausas")
    public ResponseEntity<List<PausaResponseDTO>> getPausas(@PathVariable Long viajeId){
        List<PausaResponseDTO> pausas = service.getPausas(viajeId);
        return ResponseEntity.ok(pausas);
    }

    /*------------------------- ENDPOINTS SERVICIOS  ----------------------------*/
    // d) Como administrador quiero consultar el total facturado en un rango de meses de cierto año
    // Ejemplo: http://localhost:8080/viajes/total-facturado?anio=2025&mesInicio=1&mesFin=6
    @Operation(
            summary = "Total facturado en un período",
            description = "Calcula el total facturado para un rango de meses de un año específico"
    )
    @GetMapping("/total-facturado")
    public ResponseEntity<TotalFacturadoDTO> getTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {

        TotalFacturadoDTO total = service.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(total);
    }

    // c) Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
    // Ejemplo: http://localhost:8080/viajes/monopatines-mas-viajes?anio=2025&cantidadMinima=2
    @Operation(
            summary = "Monopatines con más viajes",
            description = "Devuelve los monopatines que superan cierta cantidad de viajes en un año"
    )
    @GetMapping("/monopatines-mas-viajes")
    public ResponseEntity<List<MonopatinViajesDTO>> getMonopatinesMasViajes(
            @RequestParam int anio,
            @RequestParam long cantidadMinima){
        List<MonopatinViajesDTO> resultado = service.obtenerMonopatinesConMasViajes(anio, cantidadMinima);
        return ResponseEntity.ok(resultado);
    }

    // e) Como administrador quiero ver los usuarios que más utilizan los monopatines,
    // filtrando por período y por tipo de usuario (pemium/no premium)
    @Operation(
            summary = "Usuarios más activos",
            description = "Devuelve los usuarios que más viajes hicieron en un período"
    )
    @GetMapping("/usuarios/mas-activos")
    public ResponseEntity<List<UsuariosConMasViajesDTO>> getUsuariosMasActivos(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam("premium") Boolean tipoUsuario){
        List<UsuariosConMasViajesDTO> usuariosActivos = service.obtenerUsuariosMasActivos(inicio, fin, tipoUsuario);
        return ResponseEntity.ok(usuariosActivos);
    }

    // h)Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si
    //otros usuarios relacionados a mi cuenta los han usado.
    @Operation(
            summary = "Uso de monopatín por usuario",
            description = "Cuenta cuántos viajes hizo un usuario en un período"
    )
    @GetMapping("/uso-monopatin/cantidad")
    public ResponseEntity<UsoMonopatinUsuarioDTO> contarViajesPorUsuario(
            @RequestParam Long idUsuario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        UsoMonopatinUsuarioDTO resultado = service.contarViajesPorUsuario(idUsuario, inicio, fin);
        return ResponseEntity.ok(resultado);
    }

    @Operation(
            summary = "Viajes por monopatín",
            description = "Devuelve todos los viajes asociados a un monopatín"
    )
    @GetMapping("/monopatin/{monopatinId}")
    public ResponseEntity<List<ViajeMonopatinResponseDTO>> getByMonopatin(@PathVariable String monopatinId) {
        return ResponseEntity.ok(service.getByMonopatin(monopatinId));
    }

}

