package org.example.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaResponseDTO {
    private Long id;
    private Double precioBase;
    private Double precioRecargaPorPausa;
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinVigencia;
    private Boolean activa = true;
}
