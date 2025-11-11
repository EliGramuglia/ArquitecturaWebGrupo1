package org.example.usuario.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.cuenta.EstadoCuenta;

import java.time.LocalDate;
import java.util.List;

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

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private Boolean premium;

    @NotNull(message = "El usuario es obligatorio")
    private List<Long> idUsuario;
}
