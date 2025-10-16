package org.example.ejerciciointegrador3.controller;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/estudiantes")
@AllArgsConstructor
public class EstudianteController {
    private final EstudianteService service;


    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // POST: crea un nuevo estudiante
    @PostMapping("")
    public ResponseEntity<EstudianteResponseDTO> create(@RequestBody EstudianteRequestDTO estudiante){
        EstudianteResponseDTO nuevo = service.save(estudiante);
        return ResponseEntity.ok(nuevo);
    }

    // GET: obtener todos los estudiantes
    @GetMapping("")
    public ResponseEntity<List<EstudianteResponseDTO>> getAll(){
        List<EstudianteResponseDTO> estudiantes = service.findAll();
        return ResponseEntity.ok(estudiantes);
    }

    // GET ID: obtener un estudiante
    @GetMapping("/{dni}")
    public ResponseEntity<EstudianteResponseDTO> get(@PathVariable Integer dni){ //PathVariable indica que el valor del parámetro {id} viene en la URL
        EstudianteResponseDTO estudiante = service.findById(dni);
        return ResponseEntity.ok(estudiante);
    }

    // PUT: editar un estudiante
    @PutMapping("/{dni}")
    public ResponseEntity<EstudianteResponseDTO> update(@PathVariable Integer dni, @RequestBody EstudianteRequestDTO request){
        EstudianteResponseDTO estudianteActualizado = service.update(dni, request);
        return ResponseEntity.ok(estudianteActualizado);
    }

    // DELETE: borrar un estudiante
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable Integer dni) {
        service.delete(dni);
        return ResponseEntity.noContent().build(); // devuelve 204 (operación exitosa, pero no hay contenido que devovler)
    }


    /*-------------------------- ENDPOINTS ESPECÍFICOS --------------------------*/
    // Recuperar un estudiante en base a su LU
    @GetMapping("/lu/{lu}")
    public ResponseEntity<EstudianteResponseDTO> getByLU(@PathVariable Integer lu){
        EstudianteResponseDTO estudiante = service.findByLU(lu);
        return ResponseEntity.ok(estudiante);
    }
}