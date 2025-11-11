package org.example.usuario.mapper;

import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.entity.Cuenta;
import org.example.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public static UsuarioResponseDTO convertToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getNroCelular(),
                usuario.getRol(),
                usuario.getCuentas().stream()
                        .map(Cuenta::getNroCuenta)
                        .collect(Collectors.toList())
        );
    }

    public static Usuario convertToEntity(UsuarioRequestDTO usuarioResponseDTO) {
        List<Cuenta> cuentas = new ArrayList<>();
        return new Usuario(
                usuarioResponseDTO.getNombre(),
                usuarioResponseDTO.getApellido(),
                usuarioResponseDTO.getEmail(),
                usuarioResponseDTO.getNroCelular(),
                usuarioResponseDTO.getRol(),
                cuentas
        );
    }
}
