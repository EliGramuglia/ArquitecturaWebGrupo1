package org.example.repository;

import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;

public interface InscripcionRepository {
    //Inscripcion create(Integer dniEstudiante, String nombreCarrera);
    Inscripcion findById(int idCarrera, int luEstudiante);
    void delete(Inscripcion inscripcionId);
    Inscripcion create(Estudiante e, Carrera c);

}
