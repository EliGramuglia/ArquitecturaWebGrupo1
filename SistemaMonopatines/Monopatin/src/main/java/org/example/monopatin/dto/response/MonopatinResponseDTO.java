package org.example.monopatin.dto.response;

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
@Schema(description = "Información de un monopatín")
public class MonopatinResponseDTO {

    @Schema(description = "Identificador único del monopatín")
    private String id;

    @Schema(description = "Latitud actual del monopatín")
    private Integer latitud;

    @Schema(description = "Longitud actual del monopatín")
    private Integer longitud;

    @Schema(description = "Kilómetros totales recorridos")
    private Integer kmRecorridos;

    @Schema(description = "Estado actual del monopatín")
    private EstadoMonopatin estadoMonopatin;
}

