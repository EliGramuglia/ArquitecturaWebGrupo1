package org.example.repository;

import org.example.entity.Estudiante;

public interface EstudianteRepository {
    Estudiante create(Estudiante estudiante);
    Estudiante findByLU(Integer id);
    void delete(Estudiante estudiante);


}
