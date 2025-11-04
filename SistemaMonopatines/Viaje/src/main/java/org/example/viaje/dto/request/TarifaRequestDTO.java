package org.example.viaje.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaRequestDTO {
    @NotNull(message = "El precio es obligatorio")
    private Double precioBase;

    private Double precioRecargaPorPausa;

    @NotNull(message = "La fecha en la que entra en vigencia la tarifa es obligatoria")
    private LocalDate fechaInicioVigencia;

    private LocalDate fechaFinVigencia;
}
