package org.example.viaje.client.cuenta.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaRequestDTO {
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate fecha_alta;

    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private Boolean premium;
}
