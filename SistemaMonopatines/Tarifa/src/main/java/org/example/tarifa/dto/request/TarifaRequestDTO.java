package org.example.tarifa.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaRequestDTO {

    private Double monto;
    private Long viajeId;
    private Long usuarioId;

}
