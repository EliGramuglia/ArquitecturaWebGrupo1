package org.example.dto;

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


    // consultar si debe devolver solo el dni ordenado o todos los datos del estudiante
    @Override
    public String toString() {
        return LU + " " + nombre + " " + apellido + ", Género: " + genero +" Dni: " + dni;
    }
}
