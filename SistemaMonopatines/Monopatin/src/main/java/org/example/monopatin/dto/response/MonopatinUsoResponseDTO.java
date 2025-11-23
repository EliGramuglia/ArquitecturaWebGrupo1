package org.example.monopatin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Resultados del análisis de uso de un monopatín")
public class MonopatinUsoResponseDTO {

    @Schema(description = "ID del monopatín")
    private String monopatinId;

    @Schema(description = "Total de kilómetros recorridos")
    private Double totalKm;

    @Schema(description = "Tiempo total de uso (según incluirPausas)")
    private Long totalMinutos;

    @Schema(description = "Tiempo total incluyendo pausas")
    private Long totalMinutosConPausa;

    @Schema(description = "Indica si el monopatín requiere mantenimiento")
    private boolean requiereMantenimiento;
}
