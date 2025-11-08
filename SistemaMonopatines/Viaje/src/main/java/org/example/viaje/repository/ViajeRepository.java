package org.example.viaje.repository;

import org.example.viaje.dto.response.MonopatinViajesDTO;
import org.example.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    List<Viaje> findByFechaHoraInicioBetween(LocalDateTime inicio, LocalDateTime fin);

    // Consultar los monopatines con más de X viajes en un cierto año.
    @Query("""
            SELECT new org.example.viaje.dto.response.MonopatinViajesDTO(
                v.idMonopatin,
                COUNT(v)
            )
            FROM Viaje v
            WHERE YEAR(v.fechaHoraInicio) = :anio
            GROUP BY v.idMonopatin
            HAVING COUNT(v) > :cantidadMinima
            ORDER BY COUNT(v) DESC
    """)
    List<MonopatinViajesDTO> findMonopatinesConMasViajes(
            @Param("anio") int anio,
            @Param("cantidadMinima") long cantidadMinima
    );
}