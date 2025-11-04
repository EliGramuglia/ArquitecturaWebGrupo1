package org.example.viaje.repository;

import org.example.viaje.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    // Busca la tarifa activa más reciente (la de fecha de inicio más nueva)
    Optional<Tarifa> findFirstByActivaTrueOrderByFechaInicioVigenciaDesc();

    Optional<Tarifa> findFirstByOrderByFechaFinVigenciaDesc();
}
