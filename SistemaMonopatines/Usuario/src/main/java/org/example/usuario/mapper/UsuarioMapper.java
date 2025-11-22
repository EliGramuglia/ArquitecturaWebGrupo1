package org.example.usuario.mapper;

import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.entity.Authority;
import org.example.usuario.entity.Cuenta;
import org.example.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
                usuario.getCuentas().stream()
                        .map(Cuenta::getNroCuenta)
                        .collect(Collectors.toList()),
                usuario.getAuthorities().stream()
                        .map(Authority::getName)
                        .collect(Collectors.toSet())
        );
    }

    public static Usuario convertToEntity(UsuarioRequestDTO usuarioRequestDTO) {
        List<Cuenta> cuentas = new ArrayList<>();
        Set<Authority> authorities = new HashSet<>();
        usuarioRequestDTO.getAuthorities().stream().map(Authority::new).forEach(authorities::add);
        return new Usuario(
                usuarioRequestDTO.getNombre(),
                usuarioRequestDTO.getApellido(),
                usuarioRequestDTO.getEmail(),
                usuarioRequestDTO.getNroCelular(),
                authorities,
                usuarioRequestDTO.getPassword(),
                cuentas
        );
    }
}
