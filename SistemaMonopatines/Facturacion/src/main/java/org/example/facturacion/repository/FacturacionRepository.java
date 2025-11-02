package org.example.facturacion.repository;

import org.example.facturacion.entity.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

}
