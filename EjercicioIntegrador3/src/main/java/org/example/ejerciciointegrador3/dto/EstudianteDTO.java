package org.example.ejerciciointegrador3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    private Integer LU;
    private String nombre;
    private String apellido;
    private String genero;
    private Integer dni;


    @Override
    public String toString() {
        return "Dni: " + dni + " " + nombre + " " + apellido + ", Género: " + genero + " LU: " + LU;
    }
}
