package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.mapper.EstudianteMapper;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper mapper;


    // Lógica de negocio para dar de alta un Estudiante
    public EstudianteResponseDTO save(EstudianteRequestDTO request) {
        // Primero validamos que los datos enviados sean correctos:
        if(request.getDni() == null) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if(estudianteRepository.existsById(request.getDni())) {
            throw new IllegalArgumentException("El DNI ya existe");
        }
        if(request.getLU() == null) {
            throw new IllegalArgumentException("El LU es obligatorio");
        }

        // El método recibe un dto, no una entidad, por lo que hay que convertirlo a
        // un Estudiante real, que JPA sepa guardar en la base
        Estudiante estudianteNuevo = mapper.convertToEntity(request);

        // Guardo el estudiante en la base
        Estudiante estudianteGuardado = estudianteRepository.save(estudianteNuevo);

        // Devuelvo al front sólo lo que quiero mostrar
        return mapper.convertToDTO(estudianteGuardado);
    }


    // Obtener todos los estudiantes
    public List<EstudianteResponseDTO> findAll() {
        return estudianteRepository.findAll() // devuelve una lista de <Estudiante>
                .stream() // convierte la lista para poder hacer el mapping
                .map(mapper::convertToDTO) // map() transforma cada elemento de la lista a un dto
                .toList(); // convierte el stream de nuevo en lista para devolverlo al front
    }

    // Obtener 1 estudiante, usando su id
    public EstudianteResponseDTO findById(Integer dni) {
        Estudiante e = estudianteRepository.findById(dni) // devuelve un Optional<Estudiante>
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un estudiante con el DNI: " + dni));
    // tengo que chequear si encontró el estudiante. Sino, devuelve una excepción

        return mapper.convertToDTO(e); // convierte la Entidad a un DTO para devolverla al front
    }
}
