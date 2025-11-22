package org.example.usuario.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.usuario.client.monopatin.dto.request.MonopatinRequestDTO;
import org.example.usuario.client.monopatin.dto.response.MonopatinResponseDTO;
import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsoMonopatinCuentaDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    @PostMapping("")
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO usuario){
        UsuarioResponseDTO nuevo = service.save(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
        List<UsuarioResponseDTO> usuarios = service.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id){
        UsuarioResponseDTO usuario = service.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id,@Valid @RequestBody UsuarioRequestDTO usuario){
        UsuarioResponseDTO usuarioEditado = service.update(id, usuario);
        return ResponseEntity.ok(usuarioEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> delete(@PathVariable Long id){
       service.delete(id);
       return ResponseEntity.noContent().build();
    }

    /*-------------------------- ENDPOINTS PARA LOS SERVICIOS --------------------------*/
    @PostMapping("/monopatines/create")
    public ResponseEntity<MonopatinResponseDTO> create(@RequestBody MonopatinRequestDTO monopatin){
        MonopatinResponseDTO nuevo = service.saveMonopatin(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/monopatines-cercanos")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesCercanos(
            @RequestParam double latitud,
            @RequestParam double longitud
    ) {
        List<MonopatinResponseDTO> cercanos = service.buscarMonopatinesCercanos(latitud, longitud);
        return ResponseEntity.ok(cercanos);
    }

    @GetMapping("/{idUsuario}/uso-monopatines")
    public ResponseEntity<UsoMonopatinCuentaDTO> obtenerUsoMonopatines(
            @PathVariable Long idUsuario,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        UsoMonopatinCuentaDTO resultado = service.obtenerUsoMonopatines(idUsuario, inicio, fin);
        return ResponseEntity.ok(resultado);
    }

}