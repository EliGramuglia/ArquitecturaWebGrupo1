package org.example.usuario.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "El email es obligatorio")
    private String email;

    @NotNull(message = "El nroCelular es obligatorio")
    private Integer nroCelular;

/*    @NotNull(message = "El rol es obligatorio")
    private Rol rol;  YA NO NECESITAMOS UN ROL ??? porque tenemos un set de roles abajo*/

    private Long idCuenta;

    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    @NotNull( message = "Los roles son un campo requerido." )
    @NotEmpty( message = "Los roles son un campo requerido." )
    private List<String> authorities;
}