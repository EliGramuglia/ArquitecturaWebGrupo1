package org.example.viaje.service;

import jakarta.transaction.Transactional;
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

        validarFechasTarifa(nuevaTarifa);

        // Verificar que las fechas no se pisen: si no terminó la anterior, no puede entrar en vigencia la nueva tarifa
        tarifaRepository.findFirstByOrderByFechaFinVigenciaDesc()
                .ifPresent(ultimaTarifa -> {
                    if (ultimaTarifa.getFechaFinVigencia() != null &&
                            nuevaTarifa.getFechaInicioVigencia().isBefore(ultimaTarifa.getFechaFinVigencia())) {
                        throw new RuntimeException("La nueva tarifa no puede comenzar antes de que finalice la anterior");
                    }
                });

        // Si la nueva tarifa empieza hoy y hay una tarifa activa, primero la desactivo para actualizarla
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


    @Transactional
    public TarifaResponseDTO updateFechaFinTarifaActiva(LocalDate nuevaFechaFin) {
        // Buscar la tarifa activa actual
        Tarifa tarifaActiva = tarifaRepository.findFirstByActivaTrueOrderByFechaInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("No hay una tarifa activa para actualizar"));

        validarFechasTarifa(tarifaActiva);

        // Verificar que no se solape con la siguiente tarifa (si existe)
        tarifaRepository.findFirstByFechaInicioVigenciaAfterOrderByFechaInicioVigenciaAsc(LocalDate.now())
                .ifPresent(tarifaFutura -> {
                    // Verificar que la nueva fecha no se solape
                    if (!nuevaFechaFin.isBefore(tarifaFutura.getFechaInicioVigencia())) {
                        throw new RuntimeException("La nueva fecha de fin se solapa con una tarifa futura programada (empieza el "
                                + tarifaFutura.getFechaInicioVigencia() + ").");
                    }
                });

        // Actualizar la fecha de fin y desactivar la tarifa
        tarifaActiva.setFechaFinVigencia(nuevaFechaFin);
        // Solo desactivar si la nueva fecha de fin ya pasó o es hoy
        if (!nuevaFechaFin.isAfter(LocalDate.now())) {
            tarifaActiva.setActiva(false);
        }

        Tarifa actualizada = tarifaRepository.save(tarifaActiva);
        return TarifaMapper.convertToDTO(actualizada);
    }


    private void validarFechasTarifa(Tarifa nuevaTarifa) {
        // Validar que la fecha de fin de vigencia no sea antes que la del inicio
        if (nuevaTarifa.getFechaFinVigencia().isBefore(nuevaTarifa.getFechaInicioVigencia())) {
            throw new RuntimeException("La fecha de fin de vigencia no puede ser anterior a la fecha de inicio.");
        }

        // Validar que no sea anterior a hoy
        if (nuevaTarifa.getFechaInicioVigencia().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de inicio de vigencia no puede ser anterior a la fecha actual.");
        }
    }

    /* Contexto: el admin tiene un tarifa activa con fecha de inicio y fin, pero se arrepiente y quiere
    aumentarle el precio: para crear una nueva tarifa tiene que editar la fecha de fin de
    vigencia de la tarifa activa actual. */


}