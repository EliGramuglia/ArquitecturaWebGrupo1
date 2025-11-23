package org.example.monopatin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Parámetros para generar el reporte de uso de monopatines")
public class ReporteUsoRequestDTO {

    @Schema(description = "Indica si se deben incluir las pausas en el cálculo del tiempo")
    private boolean incluirPausas = true;

    @Schema(description = "Umbral de kilómetros para determinar necesidad de mantenimiento")
    private Double umbralKmMantenimiento;
}
