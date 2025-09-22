package org.example.repository;

import org.example.dto.EstudianteDTO;
import org.example.entity.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    Estudiante create(Estudiante estudiante);
    Estudiante findByLU(Integer id);
    void delete(Estudiante estudiante);
    List<EstudianteDTO> findAllOrderByDniAsc();
    List<EstudianteDTO> findAllByGenero(String gene);
    Estudiante findByNombre(String nom);
}
