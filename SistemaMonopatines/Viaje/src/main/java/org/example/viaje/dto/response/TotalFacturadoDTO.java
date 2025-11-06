package org.example.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalFacturadoDTO {
    private int anio;
    private int mesInicio;
    private int mesFin;
    private Double totalFacturado;
}
