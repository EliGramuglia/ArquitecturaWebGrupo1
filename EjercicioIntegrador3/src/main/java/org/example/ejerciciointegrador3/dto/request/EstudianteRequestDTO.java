package org.example.ejerciciointegrador3.dto.request;

// DTO que recibe la info del frontend (request)

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteRequestDTO {
    private Integer dni;
    private Integer LU;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String genero;
    private String ciudadResidencia;
}
