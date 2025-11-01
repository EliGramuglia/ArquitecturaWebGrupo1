package org.example.monopatin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.monopatin.utils.Estado;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinRequestDTO {

    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private Long viajeId; // Debemos enviar un id de viaje a la hora de crear un nuevo monopatín?
    private Estado estado;
    private Integer horasUso;

}
