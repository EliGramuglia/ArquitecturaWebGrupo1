package org.example.viaje.mapper;

import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.PausaResponseDTO;
import org.example.viaje.dto.response.TarifaResponseDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Viaje;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViajeMapper {

    // Convierte de entidad a dto
    public static ViajeResponseDTO convertToDTO(Viaje entity) {
        List<PausaResponseDTO> pausasDTO = entity.getPausas()
                .stream()
                .map(PausaMapper::convertToDTO)
                .toList();

        TarifaResponseDTO tarifaDTO = TarifaMapper.convertToDTO(entity.getTarifa());

        return new ViajeResponseDTO(
                entity.getId(),
                entity.getFechaHoraInicio(),
                entity.getFechaHoraFin(),
                entity.getKmRecorridos(),
                entity.getIdParadaInicio(),
                entity.getIdParadaFinal(),
                entity.getIdMonopatin(),
                entity.getIdCliente(),
                pausasDTO,
                tarifaDTO
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
                null,
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
