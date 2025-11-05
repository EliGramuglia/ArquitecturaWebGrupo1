package org.example.monopatin.client.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViajeResponseDTO {
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Double kmRecorridos;
    private Long idParadaInicio;
    private Long idParadaFinal;
    private Long idMonopatin;
    private Long idCliente;
}
