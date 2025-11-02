package org.example.viaje.repository;

import org.example.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
