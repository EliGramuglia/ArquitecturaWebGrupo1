package org.example.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeResponseDTO {
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Double kmRecorridos;
    private Long idParadaInicio;
    private Long idParadaFinal;
    private Long idMonopatin;
    private Long idCliente;
    private List<PausaResponseDTO> pausas;
    private TarifaResponseDTO tarifa;
    private Double costoTotal;
}
