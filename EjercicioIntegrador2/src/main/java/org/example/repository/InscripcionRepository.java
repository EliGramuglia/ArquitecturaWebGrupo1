package org.example.repository;

import org.example.entity.Carrera;
import org.example.entity.Inscripcion;

public interface InscripcionRepository {
    Inscripcion create(Inscripcion inscripcion);
    Inscripcion findById(Integer idInscripcion);
    void delete(Inscripcion inscripcion);


}
