package org.example.usuario.repository;

import jakarta.validation.constraints.NotNull;
import org.example.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(@NotNull(message = "El email es obligatorio") String email);
    //devuelve los IDs de otros usuarios que comparten una o más cuentas con el usuario indicado
    @Query("""
        SELECT DISTINCT u.id
        FROM Usuario u
        JOIN u.cuentas c
        WHERE c.nroCuenta IN :cuentasIds
          AND u.id <> :idUsuario
    """)
    List<Long> findIdsByCuentasIdExcepto(
            @Param("cuentasIds") List<Long> cuentasIds,
            @Param("idUsuario") Long idUsuario
    );
}
