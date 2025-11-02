package org.example.facturacion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FacturacionResponseDTO {
    private Long id;
    private Long idCliente;
    private Long idCuenta;

}
