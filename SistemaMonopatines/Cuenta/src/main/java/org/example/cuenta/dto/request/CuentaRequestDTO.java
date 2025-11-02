package org.example.cuenta.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cuenta.utils.Estado;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaRequestDTO {
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate fecha_alta;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @NotNull(message = "El monto es obligatorio")
    private Integer monto;

}