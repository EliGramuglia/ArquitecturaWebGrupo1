package org.example.usuario.client.pagomock.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearPagoResponseDTO {

    private String pagoId;
    private String status;      // ej: "pending"
    private String initPoint;   // URL ficticia para "pagar"

}