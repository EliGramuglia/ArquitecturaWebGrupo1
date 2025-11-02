package org.example.cuenta.mapper;

import org.example.cuenta.dto.request.CuentaRequestDTO;
import org.example.cuenta.dto.response.CuentaResponseDTO;
import org.example.cuenta.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public static CuentaResponseDTO convertToDTO(Cuenta cuenta) {
        return new CuentaResponseDTO(
                cuenta.getNroCuenta(),
                cuenta.getFecha_alta(),
                cuenta.getEstado(),
                cuenta.getMonto()
        );
    }

    public static Cuenta convertToEntity(CuentaRequestDTO cuentaResponseDTO) {
        return new Cuenta(
                cuentaResponseDTO.getFecha_alta(),
                cuentaResponseDTO.getEstado(),
                cuentaResponseDTO.getMonto()
        );
    }
}
