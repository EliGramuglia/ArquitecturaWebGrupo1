package org.example.repository;

import org.example.dto.CarreraDTO;
import org.example.entity.Carrera;

import java.util.List;

public interface CarreraRepository {
    Carrera create(Carrera carrera);
    Carrera findById(Integer idCarrera);
    void delete(Integer idCarrera);
    List<CarreraDTO> findCarreraOrderByCantInscriptos();

}
