package org.example.dto;

public class CarreraDTO {
    private Integer idCarrera;
    private String nombre;
    private Integer duracion;


    @Override
    public String toString() {
        return idCarrera + " " + nombre + " " + duracion;
    }
}
