package org.example.tarifa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaResponseDTO {

    private Long id;
    private Double monto;
    private Long viajeId;
    private Long usuarioId;

}
