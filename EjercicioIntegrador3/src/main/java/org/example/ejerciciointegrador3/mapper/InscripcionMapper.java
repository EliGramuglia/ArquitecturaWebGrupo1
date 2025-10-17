package org.example.ejerciciointegrador3.mapper;

import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.example.ejerciciointegrador3.repository.CarreraRepository;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InscripcionMapper {
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    public InscripcionMapper(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository) {
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
    }

    public Inscripcion convertToEntity(InscripcionRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getDni())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + dto.getDni()));

        Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada: " + dto.getIdCarrera()));

        // La fecha de inscripción la generamos automáticamente (hoy)
        Inscripcion inscripcion = new Inscripcion(estudiante, carrera);
        inscripcion.setFechaInscripcion(LocalDate.now());
        // La fecha de graduación queda null por defecto

        return inscripcion;
    }

    public InscripcionResponseDTO convertToDTO(Inscripcion entity) {
        InscripcionResponseDTO dto = new InscripcionResponseDTO();
        dto.setDni(entity.getEstudiante().getDni());
        dto.setIdCarrera(entity.getCarrera().getIdCarrera());
        dto.setFechaInscripcion(entity.getFechaInscripcion());
        dto.setFechaGraduacion(entity.getFechaGraduacion());
        return dto;
    }
}
