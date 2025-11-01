package org.example.monopatin.mapper;

import org.example.monopatin.dto.request.MonopatinRequestDTO;
import org.example.monopatin.dto.response.MonopatinResponseDTO;
import org.example.monopatin.entity.Monopatin;
import org.springframework.stereotype.Component;

@Component
public class MonopatinMapper {

    public static Monopatin convertToEntity(MonopatinRequestDTO dto) {
        return new Monopatin(
                dto.getLatitud(),
                dto.getLongitud(),
                dto.getKmRecorridos(),
                dto.getViajeId(),
                dto.getEstado()
        );
    }

    public static MonopatinResponseDTO convertToDTO(Monopatin entity) {
        return new MonopatinResponseDTO(
                entity.getId(),
                entity.getLatitud(),
                entity.getLongitud(),
                entity.getKmRecorridos(),
                entity.getViajeId(),
                entity.getEstado()
        );
    }

}
