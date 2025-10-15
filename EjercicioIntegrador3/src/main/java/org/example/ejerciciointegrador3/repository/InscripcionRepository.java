package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.entity.Inscripcion;
import java.time.LocalDate;

public interface InscripcionRepository {
    Inscripcion create(Integer idEstudiante, Integer idCarrera, LocalDate fechaInscripcion, LocalDate fechaGraduacion);
    Inscripcion findById(int idCarrera, int luEstudiante);
    void delete(Inscripcion inscripcionId);

}
