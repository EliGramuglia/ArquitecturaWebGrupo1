package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsoMonopatinCuentaDTO {
    private Long idUsuario;                 // Usuario principal que consulta
    private Long cantidadViajes;            // Cuántos viajes hizo ese usuario
    private List<Long> idsUsuariosRelacionados;// Otros usuarios que comparten cuenta
}
