package org.example.monopatin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Resultado del reporte de uso de monopatines")
public class ReporteUsoResponseDTO {

    @Schema(description = "Lista de monopatines con sus estadísticas de uso")
    private List<MonopatinUsoResponseDTO> monopatines;
}
