package org.example.monopatin.client.parada.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParadaResponseDTO {
    private Long id;
    private String nombre;
    private float latitud;
    private float longitud;
}