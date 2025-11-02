package org.example.facturacion.mapper;

import org.example.facturacion.dto.request.FacturacionRequestDTO;
import org.example.facturacion.dto.response.FacturacionResponseDTO;
import org.example.facturacion.entity.Facturacion;
import org.springframework.stereotype.Component;

@Component
public class FacturacionMapper {

    public static FacturacionResponseDTO convertToDTO(Facturacion facturacion) {
        return new FacturacionResponseDTO(
                facturacion.getId(),
                facturacion.getCliente(),
                facturacion.getCuenta()
        );
    }

    public static Facturacion convertToEntity(FacturacionRequestDTO facturacionResponseDTO) {
        return new Facturacion(
                facturacionResponseDTO.getCliente(),
                facturacionResponseDTO.getCuenta()
        );
    }
}
