package org.example.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.usuario.utils.cuenta.EstadoCuenta;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class CuentaResponseDTO {
    private Long nroCuenta;
    private LocalDate fecha_alta;
    private EstadoCuenta estadoCuenta;
    private Integer monto;
    private Boolean premium;
    private List<Long> idUsuarios;
}
