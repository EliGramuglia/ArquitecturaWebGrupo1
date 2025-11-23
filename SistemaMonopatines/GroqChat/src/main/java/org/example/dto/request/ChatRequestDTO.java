package org.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDTO {
    private Long idUsuario;           // el usuario que pregunta
    private String pregunta;          // lenguaje natural
    // Podrías agregar fechas opcionales, etc.
}