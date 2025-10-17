package org.example.ejerciciointegrador3.mapper;

import org.example.ejerciciointegrador3.dto.response.InscripcionResponseDTO;
import org.example.ejerciciointegrador3.dto.request.InscripcionRequestDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.springframework.stereotype.Component;

@Component
public class InscripcionMapper {

    // de InscripcionResquestDTO a Entidad
    public static Inscripcion convertToEntity(InscripcionRequestDTO dto){
        return new Inscripcion(new Estudiante(dto.getDni())
        );
    }

    // de Inscripcion a InscripcionResponseDTO, para devolver al front
    public InscripcionResponseDTO convertToDTO(Inscripcion entity){
        InscripcionResponseDTO dto = new InscripcionResponseDTO();
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setLU(entity.getLU());

        return dto;
    }

}