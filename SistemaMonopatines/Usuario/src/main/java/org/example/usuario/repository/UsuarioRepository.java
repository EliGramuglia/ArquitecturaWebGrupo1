package org.example.usuario.repository;

import jakarta.validation.constraints.NotNull;
import org.example.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(@NotNull(message = "El email es obligatorio") String email);
}
