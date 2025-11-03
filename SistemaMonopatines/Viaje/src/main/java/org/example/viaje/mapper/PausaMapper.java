package org.example.viaje.mapper;

import org.example.viaje.dto.request.PausaRequestDTO;
import org.example.viaje.dto.response.PausaResponseDTO;
import org.example.viaje.entity.Pausa;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PausaMapper {

    // Convierte de entidad a dto
    public static PausaResponseDTO convertToDTO(Pausa pausa) {
        if (pausa == null) return null;
        PausaResponseDTO dto = new PausaResponseDTO(
                pausa.getId(),
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
    public static Pausa convertToEntity(PausaRequestDTO dto) {
        if (dto == null) return null;
        return new Pausa(
                dto.getInicio(),
                dto.getFin()
        );
    }

}
