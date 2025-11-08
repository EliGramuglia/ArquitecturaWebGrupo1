package org.example.monopatin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinUsoResponseDTO {

    private String monopatinId;
    private Double totalKm;
    private Long totalMinutos;          // calculado según booleano incluirPausas
    private Long totalMinutosConPausa;  // siempre
    private boolean requiereMantenimiento;
}
