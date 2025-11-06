package org.example.usuario.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.usuario.utils.Rol;

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

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}