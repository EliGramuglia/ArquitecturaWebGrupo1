package org.example.ejerciciointegrador3.controller;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestDTO;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestUpdateDTO;
import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
@AllArgsConstructor
public class InscripcionController {
    private InscripcionService service;

    /*-------------------------- ENDPOINTS PARA EL CRUD --------------------------*/
    // POST:
    @PostMapping("")
    public ResponseEntity<InscripcionResponseDTO> create(@RequestBody InscripcionRequestDTO inscripcion){
        InscripcionResponseDTO nuevo = service.save(inscripcion);
        return ResponseEntity.ok(nuevo);
    }

    // GET:
    @GetMapping("")
    public ResponseEntity<List<InscripcionResponseDTO>> getAll(){
        List<InscripcionResponseDTO> inscripcion = service.findAll();
        return ResponseEntity.ok(inscripcion);
    }

    // GET ID: obtener una carrera
    @GetMapping("/{dni}/{idCarrera}")
    public ResponseEntity<InscripcionResponseDTO> get(@PathVariable Integer dni,  @PathVariable Integer idCarrera){
        InscripcionResponseDTO inscripcion = service.findById(dni, idCarrera);
        return ResponseEntity.ok(inscripcion);
    }

    // PUT: editar una carrera
    @PutMapping("/{dni}/{idCarrera}")
    public ResponseEntity<InscripcionResponseDTO> update(@PathVariable Integer dni, @PathVariable Integer idCarrera, @RequestBody InscripcionRequestUpdateDTO request){
        InscripcionResponseDTO inscripcionActualizada = service.updateGraduacion(dni,idCarrera, request.getFechaGraduacion());
        return ResponseEntity.ok(inscripcionActualizada);
    }

    // DELETE: borrar una carrera
    @DeleteMapping("/{dni}/{idCarrera}")
    public ResponseEntity<Void> delete(@PathVariable Integer dni, @PathVariable Integer idCarrera) {
        service.delete(dni, idCarrera);
        return ResponseEntity.noContent().build();
    }


    /*-------------------------- ENDPOINTS ESPECÍFICOS --------------------------*/

}
