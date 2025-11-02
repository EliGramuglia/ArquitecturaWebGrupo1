package org.example.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.usuario.utils.Rol;

@AllArgsConstructor
@Setter
@Getter
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Integer nroCelular;
    private Rol rol;

}
