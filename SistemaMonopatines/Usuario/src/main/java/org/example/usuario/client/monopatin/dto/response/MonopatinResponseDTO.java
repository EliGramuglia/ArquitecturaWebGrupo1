package org.example.usuario.client.monopatin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.monopatin.EstadoMonopatin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinResponseDTO {

    private String id;
    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private Long viajeId;
    private EstadoMonopatin estadoMonopatin;

}
