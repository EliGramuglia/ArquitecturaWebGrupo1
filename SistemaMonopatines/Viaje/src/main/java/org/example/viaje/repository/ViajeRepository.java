package org.example.viaje.repository;

import org.example.viaje.client.dto.UsuarioViajesCountDTO;
import org.example.viaje.client.dto.response.ViajeMonopatinResponseDTO;
import org.example.viaje.dto.response.MonopatinViajesDTO;
import org.example.viaje.dto.response.UsoMonopatinUsuarioDTO;
import org.example.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    // Consultar los usuarios que más utilizan los monopatines, filtrando por período y por tipo de usuario.
    @Query("""
    SELECT new org.example.viaje.client.dto.UsuarioViajesCountDTO(v.idCliente, COUNT(v))
    FROM Viaje v
    WHERE v.fechaHoraInicio BETWEEN :inicio AND :fin
    GROUP BY v.idCliente
    """)
    List<UsuarioViajesCountDTO> contarViajesPorUsuario( @Param("inicio")LocalDate inicio, @Param("fin") LocalDate fin);

    //cantidad de viajes del usuario en el periodo
    @Query("""
        SELECT new org.example.viaje.dto.response.UsoMonopatinUsuarioDTO(
            v.idCliente,
            COUNT(v)
        )
        FROM Viaje v
        WHERE v.idCliente = :idUsuario
          AND v.fechaHoraInicio BETWEEN :inicio AND :fin
        GROUP BY v.idCliente
    """)
    UsoMonopatinUsuarioDTO contarViajesPorUsuarioYRango(
            @Param("idUsuario") Long idUsuario,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    List<Viaje> findByIdMonopatin(String idMonopatin);

}