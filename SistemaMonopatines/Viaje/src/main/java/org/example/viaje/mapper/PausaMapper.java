package org.example.viaje.mapper;

import org.example.viaje.dto.PausaDTO;
import org.example.viaje.entity.Pausa;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PausaMapper {

    // Convierte de entidad a dto
    public static PausaDTO convertToDTO(Pausa pausa) {
        if (pausa == null) return null;
        PausaDTO dto = new PausaDTO(
                pausa.getInicio(),
                pausa.getFin()
        );
        if (pausa.getInicio() != null && pausa.getFin() != null) {
            long duracion = Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes();
            dto.setDuracionMinutos(duracion);
        }
        return dto;
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
