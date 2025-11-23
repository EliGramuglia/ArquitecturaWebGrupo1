package org.example.monopatin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.monopatin.utils.EstadoMonopatin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Datos necesarios para crear o actualizar un monopatín")
public class MonopatinRequestDTO {

    @Schema(description = "Latitud actual del monopatín")
    private Integer latitud;

    @Schema(description = "Longitud actual del monopatín")
    private Integer longitud;

    @Schema(description = "Kilómetros totales recorridos")
    private Integer kmRecorridos;

    @Schema(description = "Estado actual del monopatín")
    private EstadoMonopatin estadoMonopatin;

    @Schema(description = "Cantidad total de horas de uso acumuladas")
    private Integer horasUso;
}

