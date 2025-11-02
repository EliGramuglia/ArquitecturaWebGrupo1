package org.example.viaje.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PausaDTO {
    private LocalTime inicio;
    private LocalTime fin;
    private Long duracionMinutos;

    public PausaDTO(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
}
