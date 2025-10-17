package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestDTO;
import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.example.ejerciciointegrador3.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper mapper;


    /*----------------------------- CRUD -----------------------------*/
    // Lógica de negocio para dar de alta una inscripcion
    public InscripcionResponseDTO save(InscripcionRequestDTO request) {
        Inscripcion inscripcionNueva = mapper.convertToEntity(request);

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcionNueva);

        return mapper.convertToDTO(inscripcionGuardada);
    }

    public List<InscripcionResponseDTO> findAll() {
        return inscripcionRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .toList();
    }

    // Obtener 1 estudiante, usando su id
    public InscripcionResponseDTO findById(Integer id) {
        Inscripcion c = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una inscripcion con el id: " + id));

        return mapper.convertToDTO(c);
    }

    // Editar un Estudiante
    public InscripcionResponseDTO update(Integer id, InscripcionDTO request) {

        Inscripcion inscripcionEditar = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una inscripcion con ese id: " + id));


        if(request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if(request.getDuracion() == null || request.getDuracion() <= 0) {
            throw new IllegalArgumentException("La duración es obligatoria");
        }

        inscripcionEditar.setNombre(request.getNombre());
        inscripcionEditar.setDuracion(request.getDuracion());

        Inscripcion actualizado = inscripcionRepository.save(inscripcionEditar);

        return mapper.convertToDTO(actualizado);
    }


    // Borra un estudiante
    public void delete(Integer id) {
        Inscripcion e = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una inscripcion con el id: "+ id));
        inscripcionRepository.delete(e);
    }


    /*-------------------------- MÉTODOS ESPECÍFICOS --------------------------*/


}
