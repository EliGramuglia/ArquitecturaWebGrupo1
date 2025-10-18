package org.example.ejerciciointegrador3.mapper;

import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InscripcionMapper {


    public Inscripcion convertToEntity(Estudiante e, Carrera c) {
        // La fecha de inscripción la generamos automáticamente (hoy)
        Inscripcion inscripcion = new Inscripcion(e, c);
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
