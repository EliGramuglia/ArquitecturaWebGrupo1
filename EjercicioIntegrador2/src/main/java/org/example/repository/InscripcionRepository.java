package org.example.repository;

import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;

public interface InscripcionRepository {
    Inscripcion create(Integer idEstudiante, Integer idCarrera);
    Inscripcion findById(int idCarrera, int luEstudiante);
    void delete(Inscripcion inscripcionId);

}
