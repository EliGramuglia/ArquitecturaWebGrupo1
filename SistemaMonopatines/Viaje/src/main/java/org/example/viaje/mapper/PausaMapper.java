package org.example.viaje.mapper;

import org.example.viaje.dto.PausaDTO;
import org.example.viaje.entity.Pausa;
import org.springframework.stereotype.Component;

@Component
public class PausaMapper {

    // Convierte de entidad a dto
    public static PausaDTO convertToDTO(Pausa pausa) {
        if (pausa == null) return null;
        return new PausaDTO(
                pausa.getInicio(),
                pausa.getFin()
        );
    }

    // Convierte de dto a entity
    public static Pausa convertToEntity(PausaDTO dto) {
        if (dto == null) return null;
        return new Pausa(
                dto.getInicio(),
                dto.getFin()
        );
    }

}
