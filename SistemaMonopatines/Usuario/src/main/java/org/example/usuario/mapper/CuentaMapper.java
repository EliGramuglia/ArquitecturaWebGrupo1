package org.example.usuario.mapper;

import org.example.usuario.dto.request.CuentaRequestDTO;
import org.example.usuario.dto.response.CuentaResponseDTO;
import org.example.usuario.entity.Cuenta;
import org.example.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuentaMapper {
    public static CuentaResponseDTO convertToDTO(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getNroCuenta(),
                cuenta.getFecha_alta(),
                cuenta.getEstadoCuenta(),
                cuenta.getMonto(),
                cuenta.getPremium(),
                cuenta.getClientes().stream()
                        .map(Usuario::getId)
                        .collect(Collectors.toList())
        );
    }//aca es donde decido si devolver el id del usuario y no la entidad

    public static Cuenta convertToEntity(CuentaRequestDTO cuentaRequestDTO) {
        return new Cuenta(
                cuentaRequestDTO.getFecha_alta(),
                cuentaRequestDTO.getEstadoCuenta(),
                cuentaRequestDTO.getMonto(),
                cuentaRequestDTO.getPremium()
        );
    }
}
