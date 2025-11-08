package org.example.usuario.mapper;

import org.example.usuario.dto.request.CuentaRequestDTO;
import org.example.usuario.dto.response.CuentaResponseDTO;
import org.example.usuario.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {
    public static CuentaResponseDTO convertToDTO(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getNroCuenta(),
                cuenta.getFecha_alta(),
                cuenta.getEstadoCuenta(),
                cuenta.getMonto(),
                cuenta.getPremium()
        );
    }

    public static Cuenta convertToEntity(CuentaRequestDTO cuentaResponseDTO) {
        return new Cuenta(
                cuentaResponseDTO.getFecha_alta(),
                cuentaResponseDTO.getEstadoCuenta(),
                cuentaResponseDTO.getMonto(),
                cuentaResponseDTO.getPremium()
        );
    }
}
