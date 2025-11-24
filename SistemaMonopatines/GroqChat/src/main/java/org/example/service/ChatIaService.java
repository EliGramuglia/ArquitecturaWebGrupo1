package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.client.GroqClient;
import org.example.client.UsuarioClient;
import org.example.dto.request.ChatRequestDTO;
import org.example.dto.response.ChatResponseDTO;
import org.example.dto.response.MonopatinResponseDTO;
import org.example.dto.response.UsoMonopatinCuentaDTO;
import org.example.dto.response.UsuarioResponseDTO;
import org.example.exception.UsuarioNoPremiumException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatIaService {
    private final GroqClient groqClient;
    private final UsuarioClient usuarioClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatResponseDTO procesarPregunta(ChatRequestDTO request) {
        // 1) Chequear que el usuario sea premium consultando al micro de Usuario
        UsuarioResponseDTO usuario = usuarioClient.findById(request.getIdUsuario());
        if (usuario == null || Boolean.FALSE.equals(usuario.getPremium())) {
            throw new UsuarioNoPremiumException();
        }

        // 2) Pedirle a la IA que elija UN tool y parámetros en JSON
        String toolJson = pedirToolALaIa(request);

        // 3) Parsear el JSON
        JsonNode root;
        try {
            root = objectMapper.readTree(toolJson);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "La IA devolvió un formato inválido: " + toolJson);
        }

        String toolName = root.path("tool").asText();
        JsonNode argsNode = root.path("arguments");

        // 4) Ejecutar el tool concreto
        String respuestaNatural;

        switch (toolName) {
            case "consultarUsoMonopatines" -> {
                Long idUsuario = argsNode.path("idUsuario").asLong(request.getIdUsuario());
                String inicioStr = argsNode.path("inicio").asText(LocalDate.now().minusMonths(1).toString());
                String finStr = argsNode.path("fin").asText(LocalDate.now().toString());

                LocalDate inicio = LocalDate.parse(inicioStr);
                LocalDate fin = LocalDate.parse(finStr);

                UsoMonopatinCuentaDTO usoMonopatin = usuarioClient.obtenerUsoMonopatines(idUsuario, inicio, fin);

                respuestaNatural = String.format(
                        "Entre %s y %s, usaste los monopatines %d veces.",
                        inicio.format(DateTimeFormatter.ISO_DATE),
                        fin.format(DateTimeFormatter.ISO_DATE),
                        usoMonopatin.getCantidadViajes()
                );
            }

            case "consultarMonopatinesCercanos" -> {
                double latitud = argsNode.path("latitud").asDouble();
                double longitud = argsNode.path("longitud").asDouble();

                List<MonopatinResponseDTO> monopatinesCercanos = usuarioClient.getMonopatinesCercanos(latitud, longitud);

                if (monopatinesCercanos.isEmpty()) {
                    respuestaNatural = "No hay monopatines cercanos a tu ubicación.";
                } else {
                    StringBuilder sb = new StringBuilder("Monopatines cercanos a tu ubicación:");
                    for (MonopatinResponseDTO m : monopatinesCercanos) {
                        sb.append(String.format("- ID: %s, Latitud: %s , Longitud: %s", m.getId(), m.getLatitud(), m.getLongitud()));
                        // Adaptar según los campos que tengas en MonopatinResponseDTO
                    }
                    respuestaNatural = sb.toString();
                }
            }

            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tool desconocida: " + toolName
            );
        }

        ChatResponseDTO resp = new ChatResponseDTO();
        resp.setRespuesta(respuestaNatural);
        return resp;
    }

    private String pedirToolALaIa(ChatRequestDTO request) {
        String toolsDescription = """
                Sos un asistente que NO responde directamente al usuario.
                Tu trabajo es analizar la pregunta y elegir UNA sola herramienta (tool) de la siguiente lista,
                devolviendo EXCLUSIVAMENTE un JSON.

                REGLAS MUY IMPORTANTES:
                - NO inventes datos que el usuario NO proporcionó.
                - Si la pregunta habla de "este mes":
                       * inicio = primer día del mes actual (estamos en el año 2025, mes de Noviembre) en formato yyyy-MM-dd
                       * fin = último día del mes actual (estamos en el año 2025, , mes de Noviembre) en formato yyyy-MM-dd
                - Si la pregunta NO menciona fechas:
                       * usar por defecto el mes actual (inicio y fin como arriba).
                - Siempre usar el idUsuario que te paso abajo.
                - NO agregar texto fuera del JSON.

                TOOLS DISPONIBLES:

                1) consultarUsoMonopatines
                   - descripción: devuelve cuántas veces el usuario y sus usuarios relacionados
                     han usado los monopatines en un periodo.
                   - argumentos:
                        - idUsuario: número (id del usuario que consulta)
                        - inicio: string con fecha en formato "yyyy-MM-dd"
                        - fin: string con fecha en formato "yyyy-MM-dd"

                2) consultarMonopatinesCercanos
                    - descripción: devuelve un listado de monopatines cercanos a una ubicación.
                    - argumentos:
                       - latitud: número decimal
                       - longitud: número decimal

                FORMATO DE RESPUESTA (MUY IMPORTANTE):
                Devolvé ÚNICAMENTE un JSON como este:

                {
                  "tool": "nombre_de_la_tool",
                  "arguments": {
                     // argumentos según la tool elegida
                  }
                }

                No expliques nada, no uses markdown, no agregues texto fuera del JSON.
                """;

        String prompt = toolsDescription + "\n\nPREGUNTA DEL USUARIO: \"" + request.getPregunta() + "\"\n"
                + "idUsuario: " + request.getIdUsuario();

        return groqClient.chat(prompt);
    }
}