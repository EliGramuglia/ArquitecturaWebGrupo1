package org.example.usuario.repository;

import org.example.usuario.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    //método que devuelve la lista de cuentas del usuario
    @Query("""
        SELECT c FROM Cuenta c
        JOIN c.clientes u
        WHERE u.id = :idUsuario
    """)
    List<Cuenta> findCuentasByUsuarioId(@Param("idUsuario") Long idUsuario);
}
