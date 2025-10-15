package org.example.ejerciciointegrador3.controller;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


   /* @GetMapping("/all")
    public List<EstudianteDTO> findAll() {
        return service.findAll();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    }

    @PutMapping("")

    @PatchMapping*/

}