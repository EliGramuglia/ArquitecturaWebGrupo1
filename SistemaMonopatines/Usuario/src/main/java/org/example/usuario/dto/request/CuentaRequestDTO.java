package org.example.usuario.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.cuenta.EstadoCuenta;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaRequestDTO {
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate fecha_alta;

    @NotNull(message = "El estado es obligatorio")
    private EstadoCuenta estadoCuenta;

    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

}
