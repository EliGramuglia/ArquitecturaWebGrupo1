package org.example.usuario.client.monopatin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.monopatin.EstadoMonopatin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinRequestDTO {

    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private EstadoMonopatin estadoMonopatin;
    private Integer horasUso;

}
