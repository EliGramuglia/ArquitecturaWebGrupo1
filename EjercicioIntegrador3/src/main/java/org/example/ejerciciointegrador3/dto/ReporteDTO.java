package org.example.ejerciciointegrador3.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {

    private String carrera;
    private Long anio;
    private Long inscriptos;
    private Long egresados;

}