package org.example.viaje.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsuariosConMasViajesDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean premium;
    private Long cantidadViajes;

}
