package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.dto.CarreraDTO;
import org.example.ejerciciointegrador3.dto.ReporteDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import java.util.List;

public interface CarreraRepository {
    Carrera create(Carrera carrera);
    Carrera findById(Integer idCarrera);
    void delete(Integer idCarrera);
    List<CarreraDTO> findCarreraOrderByCantInscriptos();
    List<ReporteDTO> generarReporte();

}
