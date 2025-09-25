package org.example.repository;

import org.example.dto.CarreraDTO;
import org.example.dto.ReporteDTO;
import org.example.entity.Carrera;

import java.util.List;

public interface CarreraRepository {
    Carrera create(Carrera carrera);
    Carrera findById(Integer idCarrera);
    void delete(Integer idCarrera);
    List<CarreraDTO> findCarreraOrderByCantInscriptos();
    List<ReporteDTO> generarReporte();

}
