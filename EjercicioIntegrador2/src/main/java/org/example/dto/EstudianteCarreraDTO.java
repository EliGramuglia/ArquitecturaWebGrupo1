package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EstudianteCarreraDTO {
    private Integer LU;
    private String nombre;
    private String apellido;
    private String ciudadResidencia;
    private String carrera;

    @Override
    public String toString() {
        return "Carrera " + carrera + " Ciudad de residencia: "+ ciudadResidencia +
               "\n" + LU + " Alumno: " + nombre + " " + apellido + "\n";
    }
}