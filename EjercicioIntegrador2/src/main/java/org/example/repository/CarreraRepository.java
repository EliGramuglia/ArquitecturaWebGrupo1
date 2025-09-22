package org.example.repository;

import org.example.dto.CarreraDTO;
import org.example.entity.Carrera;

public interface CarreraRepository {
    Carrera create(Carrera carrera);
    Carrera findById(Integer idCarrera);
    void delete(Carrera carrera);
    Carrera findByNombre(String nom);

}
