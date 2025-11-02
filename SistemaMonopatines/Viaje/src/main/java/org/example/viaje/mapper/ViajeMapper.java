package org.example.viaje.mapper;

import org.example.viaje.dto.PausaDTO;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Viaje;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViajeMapper {

    // Convierte de entidad a dto
    public static ViajeResponseDTO convertToDTO(Viaje entity) {
        List<PausaDTO> pausasDTO = entity.getPausas()
                .stream()
                .map(PausaMapper::convertToDTO)
                .toList();

        return new ViajeResponseDTO(
                entity.getId(),
                entity.getFechaHoraInicio(),
                entity.getFechaHoraFin(),
                entity.getKmRecorridos(),
                entity.getIdParadaInicio(),
                entity.getIdParadaFinal(),
                entity.getTarifa(),
                entity.getIdMonopatin(),
                entity.getIdCliente(),
                pausasDTO
        );
    }

    // Convierte de dto a entidad
    public static Viaje convertToEntity(ViajeRequestDTO dto) {
        Viaje viaje = new Viaje(
                dto.getFechaHoraInicio(),
                dto.getFechaHoraFin(),
                dto.getKmRecorridos(),
                dto.getIdParadaInicio(),
                dto.getIdParadaFinal(),
                dto.getTarifa(),
                dto.getIdMonopatin(),
                dto.getIdCliente()
        );
        if(dto.getPausas() != null && !dto.getPausas().isEmpty()){
            List<Pausa> pausas = dto.getPausas()
                    .stream()
                    .map(PausaMapper::convertToEntity)
                    .toList();

            viaje.setPausas(pausas);
        }
        return viaje;
    }

}
