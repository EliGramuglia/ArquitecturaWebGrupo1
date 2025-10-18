package org.example.ejerciciointegrador3.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarreraCantInscriptosDTO {
    private String nombre;
    private Long cantidadInscriptos;

    public CarreraCantInscriptosDTO(String nombre, Long cantidadInscriptos) {
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return nombre + " Cantidad de Inscriptos " + cantidadInscriptos;
    }
}
