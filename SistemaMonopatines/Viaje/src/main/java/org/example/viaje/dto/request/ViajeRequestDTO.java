package org.example.viaje.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeRequestDTO {
    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha de finalización es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraFin;

    @NotNull(message = "Los km recorridos son obligatorios")
    private Double kmRecorridos;

    @NotNull(message = "La parada de inicio es obligatoria")
    private Long idParadaInicio;

    @NotNull(message = "La parada final es obligatoria")
    private Long idParadaFinal;

    @NotNull(message = "El monopatin utilizado es obligatorio")
    private String idMonopatin;

    @NotNull(message = "El cliente que hizo uso del monopatin es obligatorio")
    private Long idCliente;

}
