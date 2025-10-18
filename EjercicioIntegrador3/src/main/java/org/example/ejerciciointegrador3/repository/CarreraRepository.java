package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.dto.CarreraCantInscriptosDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    // Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @Query("SELECT new org.example.ejerciciointegrador3.dto.CarreraCantInscriptosDTO(c.nombre, COUNT(i)) " +
            "FROM Carrera c JOIN c.alumnosInscriptos i " +
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(i) DESC")
    List<CarreraCantInscriptosDTO> findCarrerasInscriptos();
}
