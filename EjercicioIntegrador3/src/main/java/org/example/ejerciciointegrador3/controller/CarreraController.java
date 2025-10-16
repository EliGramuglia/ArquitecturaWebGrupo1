package org.example.ejerciciointegrador3.controller;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.CarreraDTO;
import org.example.ejerciciointegrador3.service.CarreraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
@AllArgsConstructor
public class CarreraController {
    private final CarreraService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // POST: crea una nueva carrera
    @PostMapping("")
    public ResponseEntity<CarreraDTO> create(@RequestBody CarreraDTO carrera){
        CarreraDTO nuevo = service.save(carrera);
        return ResponseEntity.ok(nuevo);
    }

    // GET: obtener todas las carreras
    @GetMapping("")
    public ResponseEntity<List<CarreraDTO>> getAll(){
        List<CarreraDTO> carreras = service.findAll();
        return ResponseEntity.ok(carreras);
    }

    // GET ID: obtener una carrera
    @GetMapping("/{id}")
    public ResponseEntity<CarreraDTO> get(@PathVariable Integer id){
        CarreraDTO carrera = service.findById(id);
        return ResponseEntity.ok(carrera);
    }

    // PUT: editar una carrera
    @PutMapping("/{id}")
    public ResponseEntity<CarreraDTO> update(@PathVariable Integer id, @RequestBody CarreraDTO request){
        CarreraDTO carreraActualizada = service.update(id, request);
        return ResponseEntity.ok(carreraActualizada);
    }

    // DELETE: borrar una carrera
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*-------------------------- ENDPOINTS ESPECÍFICOS --------------------------*/

}
