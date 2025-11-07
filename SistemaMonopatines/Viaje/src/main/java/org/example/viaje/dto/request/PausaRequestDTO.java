package org.example.viaje.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PausaRequestDTO {
    @NotNull(message = "La hora de inicio de pausa es obligatoria")
    private LocalTime inicio;

    @NotNull(message = "La hora de fin de pausa es obligatoria")
    private LocalTime fin;

    private Long duracionMinutos;

    public PausaRequestDTO(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
}
