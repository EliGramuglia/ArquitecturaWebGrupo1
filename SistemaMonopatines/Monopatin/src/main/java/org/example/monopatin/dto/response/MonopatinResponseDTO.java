package org.example.monopatin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.monopatin.utils.EstadoMonopatin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinResponseDTO {

    private String id;
    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private EstadoMonopatin estadoMonopatin;


}
