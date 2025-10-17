package org.example.ejerciciointegrador3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionResponseDTO {
    private Integer idCarrera;
    private Integer dni;
    private LocalDate fechaInscripcion;
    private LocalDate fechaGraduacion;

}
