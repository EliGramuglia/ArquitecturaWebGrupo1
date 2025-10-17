package org.example.ejerciciointegrador3.mapper;

import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.springframework.stereotype.Component;
/* Esta clase se encarga de:
    - Convertir un EstudianteRequestDTO → Estudiante (para guardar en la base)
    - Convertir un Estudiante → EstudianteResponseDTO (para devolver al frontend)*/

@Component
public class EstudianteMapper {


    // de EstudianteResquestDTO a Entidad
    public static Estudiante convertToEntity(EstudianteRequestDTO dto){
        return new Estudiante(
                dto.getDni(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getFechaNacimiento(),
                dto.getGenero(),
                dto.getCiudadResidencia(),
                dto.getLU()
        );
    }

    // de Entidad a EstudianteResponseDTO, para devolver al front
    public EstudianteResponseDTO convertToDTO(Estudiante entity){
        EstudianteResponseDTO dto = new EstudianteResponseDTO();
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setGenero(entity.getGenero());
        dto.setLU(entity.getLU());

        return dto;
    }
}
