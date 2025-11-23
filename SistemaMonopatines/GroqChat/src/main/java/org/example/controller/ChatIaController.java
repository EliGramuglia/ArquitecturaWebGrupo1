package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.ChatRequestDTO;
import org.example.dto.response.ChatResponseDTO;
import org.example.service.ChatIaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-ia")
@RequiredArgsConstructor
public class ChatIaController {

    private final ChatIaService chatIaService;

    @PostMapping("/preguntar")
    public ResponseEntity<ChatResponseDTO> preguntar(@RequestBody ChatRequestDTO request) {
        ChatResponseDTO resp = chatIaService.procesarPregunta(request);
        return ResponseEntity.ok(resp);
    }
}