package org.example.facturacion.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturacionRequestDTO {
    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "La cuenta es obligatorio")
    private Long idCuenta;

}