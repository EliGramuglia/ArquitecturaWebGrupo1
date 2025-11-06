package org.example.viaje.repository;

import org.example.viaje.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PausaRepository extends JpaRepository<Pausa, Long> {
    List<Pausa> findByViajeId(Long viajeId);
}
