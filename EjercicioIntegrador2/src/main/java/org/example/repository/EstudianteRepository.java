package org.example.repository;

import org.example.dto.EstudianteCarreraDTO;
import org.example.dto.EstudianteDTO;
import org.example.entity.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    Estudiante create(Estudiante estudiante);
    Estudiante findByLU(Integer id);
    void delete(Integer LU);
    List<EstudianteDTO> findAllOrderByDniAsc();
    List<EstudianteDTO> findAllByGenero(String gene);
    List<EstudianteCarreraDTO> findAllEstudianteByCarreraAndCiudad(String nombreCarrera, String ciudad);
}
