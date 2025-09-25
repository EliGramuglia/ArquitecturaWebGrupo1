package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {

    private String carrera;
    private Integer anio;
    private Long inscriptos;
    private Long egresados;

}
