package org.example.usuario.client.pagomock.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearPagoRequestDTO {

    private Long idUsuario;
    private Double monto;
    private String descripcion;

}