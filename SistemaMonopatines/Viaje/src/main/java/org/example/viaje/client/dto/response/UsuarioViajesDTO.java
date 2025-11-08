package org.example.viaje.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.viaje.utils.usuario.Rol;

@AllArgsConstructor
@Getter
@Setter
public class UsuarioViajesDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private Long cantidadViajes;

}
