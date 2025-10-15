package org.example.ejerciciointegrador3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CarreraDTO {
    private Integer idCarrera;
    private String nombre;
    private Long cantidadInscriptos;

    public CarreraDTO(String nombre, Long cantidadInscriptos) {
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return nombre + " Cantidad de Inscriptos " + cantidadInscriptos;
    }
}
