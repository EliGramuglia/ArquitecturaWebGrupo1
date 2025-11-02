package org.example.tarifa.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.tarifa.dto.request.TarifaRequestDTO;
import org.example.tarifa.dto.response.TarifaResponseDTO;
import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.mapper.TarifaMapper;
import org.example.tarifa.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TarifaService {

    private TarifaRepository tarifaRepository;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public TarifaResponseDTO save(@Valid TarifaRequestDTO request) {
        Tarifa nuevo = TarifaMapper.convertToEntity(request);
        Tarifa creado = tarifaRepository.save(nuevo);
        return TarifaMapper.convertToDTO(creado);
    }

    public List<TarifaResponseDTO> findAll() {
        return tarifaRepository.findAll()
                .stream()
                .map(TarifaMapper::convertToDTO)
                .toList();
    }

    public TarifaResponseDTO findById(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la tarifa con el id: " + id));
        return TarifaMapper.convertToDTO(tarifa);
    }

    public TarifaResponseDTO update(Long id, TarifaRequestDTO tarifa) {
        Tarifa tarifaEditar = tarifaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la tarifa con id: " + id));

        tarifaEditar.setId(id);
        tarifaEditar.setMonto(tarifa.getMonto());
        tarifaEditar.setViajeId(tarifa.getViajeId());
        tarifaEditar.setUsuarioId(tarifa.getUsuarioId());
        tarifaRepository.save(tarifaEditar);

        return TarifaMapper.convertToDTO(tarifaEditar);
    }

    public void delete(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una tarifa con el id: "+ id));
        tarifaRepository.delete(tarifa);
    }

}