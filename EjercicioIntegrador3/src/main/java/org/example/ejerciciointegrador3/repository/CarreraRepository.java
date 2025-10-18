package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.dto.CarreraCantInscriptosDTO;
import org.example.ejerciciointegrador3.dto.ReporteDTO;
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

    // Reporte de carreras (inscriptos y egresados por año)
    @Query(value = "SELECT c.nombre AS carrera, " +
            "       CAST(anios.anio AS SIGNED) AS anio, " +
            "       CAST(SUM(CASE WHEN anios.tipo = 'I' THEN 1 ELSE 0 END) AS SIGNED) AS inscriptos, " +
            "       CAST(SUM(CASE WHEN anios.tipo = 'E' THEN 1 ELSE 0 END) AS SIGNED) AS egresados " +
            "FROM carrera c " +
            "JOIN inscripcion i ON i.id_carrera = c.id_carrera " +
            "JOIN (" +
            "       SELECT dni, id_carrera, YEAR(fecha_inscripcion) AS anio, 'I' AS tipo " +
            "       FROM inscripcion WHERE fecha_inscripcion IS NOT NULL " +
            "       AND fecha_graduacion IS NULL " +
            "       UNION ALL " +
            "       SELECT dni, id_carrera, YEAR(fecha_graduacion) AS anio, 'E' AS tipo " +
            "       FROM inscripcion WHERE fecha_graduacion IS NOT NULL " +
            "     ) anios ON anios.dni = i.dni AND anios.id_carrera = i.id_carrera " +
            "GROUP BY c.nombre, anios.anio " +
            "ORDER BY c.nombre ASC, anios.anio ASC", nativeQuery = true)
    List<ReporteDTO> getReporte();

}
