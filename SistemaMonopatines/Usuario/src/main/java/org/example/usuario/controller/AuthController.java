package org.example.usuario.controller;

import lombok.AllArgsConstructor;
import org.example.usuario.dto.response.UsuarioTokenResponseDTO;
import org.example.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioService usuarioService;

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioTokenResponseDTO> getUserByUsername(@PathVariable(name= "email") String email) {
        UsuarioTokenResponseDTO usuario = usuarioService.findOneWithAuthoritiesByEmailIgnoreCase(email);
        return ResponseEntity.ok(usuario);
    }
}
