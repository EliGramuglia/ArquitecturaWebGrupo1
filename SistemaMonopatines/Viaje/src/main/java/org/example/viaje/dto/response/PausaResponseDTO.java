package org.example.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PausaResponseDTO {
    private Long id;
    private LocalTime inicio;
    private LocalTime fin;
    private Long duracionMinutos;

    public PausaResponseDTO(Long id, LocalTime inicio, LocalTime fin) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
    }
}
