package org.example.viaje.mapper;

import org.example.viaje.dto.request.TarifaRequestDTO;
import org.example.viaje.dto.response.TarifaResponseDTO;
import org.example.viaje.entity.Tarifa;
import org.springframework.stereotype.Component;

@Component
public class TarifaMapper {

    public static TarifaResponseDTO convertToDTO(Tarifa entity) {
        return new TarifaResponseDTO(
                entity.getId(),
                entity.getPrecioBase(),
                entity.getPrecioRecargaPorPausa(),
                entity.getFechaInicioVigencia(),
                entity.getFechaFinVigencia(),
                entity.getActiva()
        );
    }


    public static Tarifa convertToEntity(TarifaRequestDTO dto) {
        Tarifa entity = new Tarifa(
                dto.getPrecioBase(),
                dto.getPrecioRecargaPorPausa(),
                dto.getFechaInicioVigencia(),
                dto.getFechaFinVigencia()
        );
        return entity;
    }

}
