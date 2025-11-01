package org.example.usuario.mapper;

import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public static UsuarioResponseDTO convertToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getNroCelular()
        );
    }

    public static Usuario convertToEntity(UsuarioRequestDTO usuarioResponseDTO) {
        return new Usuario(
                usuarioResponseDTO.getNombre(),
                usuarioResponseDTO.getApellido(),
                usuarioResponseDTO.getEmail(),
                usuarioResponseDTO.getNroCelular()
        );
    }
}
