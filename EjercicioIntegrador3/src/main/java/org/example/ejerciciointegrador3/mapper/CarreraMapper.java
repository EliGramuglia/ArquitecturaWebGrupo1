package org.example.ejerciciointegrador3.mapper;

import org.example.ejerciciointegrador3.dto.CarreraDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.springframework.stereotype.Component;


@Component
public class CarreraMapper {

    public static Carrera convertToEntity(CarreraDTO dto){
        return new Carrera(
                dto.getNombre(),
                dto.getDuracion()
        );
    }

    public CarreraDTO convertToDTO(Carrera entity){
        CarreraDTO dto = new CarreraDTO();
        dto.setIdCarrera(entity.getIdCarrera());
        dto.setNombre(entity.getNombre());
        dto.setDuracion(entity.getDuracion());
        return dto;
    }
}
