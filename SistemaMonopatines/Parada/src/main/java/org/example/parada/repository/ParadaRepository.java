package org.example.parada.repository;

import org.example.parada.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}
