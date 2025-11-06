package org.example.parada.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParadaRequestDTO {

    private String nombre;
    private float latitud;
    private float longitud;

}