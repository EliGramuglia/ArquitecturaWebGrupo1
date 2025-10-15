package org.example.ejerciciointegrador3.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {

    private String carrera;
    private Integer anio;
    private Long inscriptos;
    private Long egresados;

}
