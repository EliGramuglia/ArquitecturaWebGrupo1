package org.example.parada.mapper;

import org.example.parada.dto.request.ParadaRequestDTO;
import org.example.parada.dto.response.ParadaResponseDTO;
import org.example.parada.entity.Parada;
import org.springframework.stereotype.Component;

@Component
public class ParadaMapper {

    public static Parada convertToEntity(ParadaRequestDTO dto) {
        Parada parada = new Parada();
        parada.setNombre(dto.getNombre());
        parada.setLatitud(dto.getLatitud());
        parada.setLongitud(dto.getLongitud());
        return parada;

    }

    public static ParadaResponseDTO convertToDTO(Parada entity) {
        return new ParadaResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getLatitud(),
                entity.getLongitud()
        );

    }

}