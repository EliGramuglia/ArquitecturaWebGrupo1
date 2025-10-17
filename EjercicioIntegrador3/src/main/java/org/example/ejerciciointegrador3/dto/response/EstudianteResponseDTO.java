package org.example.ejerciciointegrador3.dto.response;

// DTO que devuelve la info al frontend (response)

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponseDTO {
    private Integer dni;
    private String nombre;
    private String apellido;
    private String genero;
    private Integer LU;
}

// Se pueden omitir los datos que no quiero exponer