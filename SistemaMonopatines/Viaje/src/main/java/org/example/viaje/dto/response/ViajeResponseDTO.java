package org.example.viaje.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraInicio;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraFin;
    private Double kmRecorridos;
    private Long idParadaInicio;
    private Long idParadaFinal;
    private String idMonopatin;
    private Long idCliente;
    private List<PausaResponseDTO> pausas;
    private TarifaResponseDTO tarifa;
    private Double costoTotal;
}
