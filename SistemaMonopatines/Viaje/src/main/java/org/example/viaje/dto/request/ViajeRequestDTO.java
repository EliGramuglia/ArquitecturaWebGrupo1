package org.example.viaje.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.viaje.dto.response.PausaResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeRequestDTO {
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha de finalización es obligatoria")
    private LocalDateTime fechaHoraFin;

    @NotNull(message = "Los km recorridos son obligatorios")
    private Double kmRecorridos;

    @NotNull(message = "La parada de inicio es obligatoria")
    private Long idParadaInicio;

    @NotNull(message = "La parada final es obligatoria")
    private Long idParadaFinal;

    @NotNull(message = "El monopatin utilizado es obligatorio")
    private Long idMonopatin;

    @NotNull(message = "El cliente que hizo uso del monopatin es obligatorio")
    private Long idUsuario;

    private List<PausaRequestDTO> pausas;
}
