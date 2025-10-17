package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestDTO;
import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.example.ejerciciointegrador3.entity.InscripcionId;
import org.example.ejerciciointegrador3.mapper.InscripcionMapper;
import org.example.ejerciciointegrador3.repository.CarreraRepository;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.example.ejerciciointegrador3.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InscripcionService {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper mapper;

    // Crear inscripción
    public InscripcionResponseDTO save(InscripcionRequestDTO request) {
        Inscripcion inscripcionNueva = mapper.convertToEntity(request);
        Inscripcion guardada = inscripcionRepository.save(inscripcionNueva);
        return mapper.convertToDTO(guardada);
    }

    // Listar todas las inscripciones
    public List<InscripcionResponseDTO> findAll() {
        return inscripcionRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .toList();
    }

    // Buscar por DNI + ID de carrera
    public InscripcionResponseDTO findById(Integer dni, Integer idCarrera) {
        InscripcionId id = new InscripcionId(dni, idCarrera);
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la inscripción"));
        return mapper.convertToDTO(inscripcion);
    }

    // Actualizar fecha de graduación
    public InscripcionResponseDTO updateGraduacion(Integer dni, Integer idCarrera, LocalDate fechaGraduacion) {
        InscripcionId id = new InscripcionId(dni, idCarrera);
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la inscripción"));

        inscripcion.setFechaGraduacion(fechaGraduacion);
        Inscripcion actualizado = inscripcionRepository.save(inscripcion);
        return mapper.convertToDTO(actualizado);
    }

    // Eliminar inscripción
    public void delete(Integer dni, Integer idCarrera) {
        InscripcionId id = new InscripcionId(dni, idCarrera);
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la inscripción"));
        inscripcionRepository.delete(inscripcion);
    }
}
