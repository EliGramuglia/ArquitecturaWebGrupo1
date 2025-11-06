package org.example.viaje.repository;

import org.example.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    List<Viaje> findByFechaHoraInicioBetween(LocalDateTime inicio, LocalDateTime fin);
}
