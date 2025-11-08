package org.example.viaje.client.cuenta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class CuentaResponseDTO {
    private Long nroCuenta;
    private LocalDate fecha_alta;
    private Integer monto;
    private Boolean premium;
    private Double kmAcumuladosMes;
}
