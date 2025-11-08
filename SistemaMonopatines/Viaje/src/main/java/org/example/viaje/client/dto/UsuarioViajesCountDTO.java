package org.example.viaje.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioViajesCountDTO {
    private Long usuarioId;
    private Long cantidadViajes;
}
