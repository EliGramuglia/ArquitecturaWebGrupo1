package org.example.tarifa.mapper;

import org.example.tarifa.dto.request.TarifaRequestDTO;
import org.example.tarifa.dto.response.TarifaResponseDTO;
import org.example.tarifa.entity.Tarifa;
import org.springframework.stereotype.Component;

@Component
public class TarifaMapper {

    public static TarifaResponseDTO convertToDTO(Tarifa tarifa) {
        return new TarifaResponseDTO(
                tarifa.getId(),
                tarifa.getMonto(),
                tarifa.getViajeId(),
                tarifa.getUsuarioId()
        );
    }

    public static Tarifa convertToEntity(TarifaRequestDTO dto) {
        return new Tarifa(
                dto.getMonto(),
                dto.getViajeId(),
                dto.getUsuarioId()
        );
    }

}
