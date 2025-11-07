package org.example.viaje.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.TarifaRequestDTO;
import org.example.viaje.dto.response.TarifaResponseDTO;
import org.example.viaje.entity.Tarifa;
import org.example.viaje.mapper.TarifaMapper;
import org.example.viaje.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TarifaService {

    private TarifaRepository tarifaRepository;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public TarifaResponseDTO save(@Valid TarifaRequestDTO request) {
        Tarifa nuevaTarifa = TarifaMapper.convertToEntity(request);

        if (nuevaTarifa.getFechaFinVigencia().isBefore(nuevaTarifa.getFechaInicioVigencia())) {
            throw new RuntimeException("La fecha de fin de vigencia no puede ser anterior a la fecha de inicio");
        }

        // 1. Validar que la fecha de inicio no sea anterior a hoy
        if (nuevaTarifa.getFechaInicioVigencia().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de inicio de vigencia no puede ser anterior a la fecha actual");
        }

        // 2️. Verificar que las fechas no se pisen: si no terminó la anterior, no puede entrar en vigencia la nueva tarifa
        tarifaRepository.findFirstByOrderByFechaFinVigenciaDesc()
                .ifPresent(ultimaTarifa -> {
                    if (ultimaTarifa.getFechaFinVigencia() != null &&
                            nuevaTarifa.getFechaInicioVigencia().isBefore(ultimaTarifa.getFechaFinVigencia())) {
                        throw new RuntimeException("La nueva tarifa no puede comenzar antes de que finalice la anterior");
                    }
                });

        // 3. Si la nueva tarifa empieza hoy y hay una tarifa activa, primero la desactivo para actualizarla
        if (nuevaTarifa.getFechaInicioVigencia().isEqual(LocalDate.now())) {
            tarifaRepository.findFirstByActivaTrueOrderByFechaInicioVigenciaDesc()
                    .ifPresent(tarifa -> {
                        tarifa.setActiva(false);
                        tarifaRepository.save(tarifa);
                    });
            nuevaTarifa.setActiva(true);
        } else {
            // Si empieza en el futuro, queda inactiva
            nuevaTarifa.setActiva(false);
        }

        Tarifa tarifaPersistida = tarifaRepository.save(nuevaTarifa);
        return TarifaMapper.convertToDTO(tarifaPersistida);
    }


    public List<TarifaResponseDTO> findAll() {
        return tarifaRepository.findAll()
                .stream()
                .map(TarifaMapper::convertToDTO)
                .toList();
    }


    public TarifaResponseDTO getTarifaActiva() {
        Tarifa tarifaActiva = tarifaRepository.findFirstByActivaTrueOrderByFechaInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("No hay una tarifa activa configurada"));
        return TarifaMapper.convertToDTO(tarifaActiva);
    }



}