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


    // POST: crea un nuevo estudiante
    @PostMapping("")
    public ResponseEntity<EstudianteResponseDTO> create(@RequestBody EstudianteRequestDTO estudiante){
        EstudianteResponseDTO nuevo = service.save(estudiante);
        return ResponseEntity.ok(nuevo);
    }

    // GET: obtener todos los estudiantes
    @GetMapping("/all")
    public ResponseEntity<List<EstudianteResponseDTO>> getAll(){
        List<EstudianteResponseDTO> estudiantes = service.findAll();
        return ResponseEntity.ok(estudiantes);
    }

    // GET ID: obtener un estudiante
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> get(@PathVariable Integer dni){ //PathVariable indica que el valor del parámetro {id} viene en la URL
        EstudianteResponseDTO estudiante = service.findById(dni);
        return ResponseEntity.ok(estudiante);
    }





}