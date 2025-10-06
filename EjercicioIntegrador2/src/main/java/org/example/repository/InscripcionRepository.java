package org.example.repository;

import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;

import java.time.LocalDate;

public interface InscripcionRepository {
    Inscripcion create(Integer idEstudiante, Integer idCarrera, LocalDate fechaInscripcion, LocalDate fechaGraduacion);
    Inscripcion findById(int idCarrera, int luEstudiante);
    void delete(Inscripcion inscripcionId);

}
