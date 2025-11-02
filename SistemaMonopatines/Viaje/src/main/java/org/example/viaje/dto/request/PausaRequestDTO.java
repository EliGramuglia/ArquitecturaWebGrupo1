package org.example.viaje.dto.request;

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
    private LocalTime inicio;
    private LocalTime fin;
    private Long duracionMinutos;

    public PausaRequestDTO(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
}
