package org.example.usuario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UsuarioTokenResponseDTO {
    private Long id;
    private String usuario;
    private String password;
    private List<AuthorityResponseDTO> roles;
}
