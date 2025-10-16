package org.example.ejerciciointegrador3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private Integer idCarrera;
    private String nombre;
    private Integer duracion;
}
