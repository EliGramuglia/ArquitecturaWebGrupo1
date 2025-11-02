package org.example.viaje.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.repository.ViajeRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ViajeService {
    private final ViajeRepository viajeRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public ViajeResponseDTO save(@Valid ViajeRequestDTO viaje) {

    }
}
