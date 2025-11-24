package org.example.parada.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.parada.dto.request.ParadaRequestDTO;
import org.example.parada.dto.response.ParadaResponseDTO;
import org.example.parada.entity.Parada;
import org.example.parada.mapper.ParadaMapper;
import org.example.parada.repository.ParadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class ParadaService {

    private final ParadaRepository paradaRepository;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    @Transactional
    public ParadaResponseDTO create(ParadaRequestDTO dto) {
        Parada parada = ParadaMapper.convertToEntity(dto);
        paradaRepository.save(parada);
        return ParadaMapper.convertToDTO(parada);
    }

    public List<ParadaResponseDTO> getAll() {
        return paradaRepository.findAll()
                .stream()
                .map(ParadaMapper::convertToDTO)
                .toList();
    }

    public ParadaResponseDTO getById(Long id) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la parada con id: " + id));
        return ParadaMapper.convertToDTO(parada);
    }

    @Transactional
    public ParadaResponseDTO update(Long id, ParadaRequestDTO dto) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la parada con id: " + id));

        parada.setNombre(dto.getNombre());
        parada.setLatitud(dto.getLatitud());
        parada.setLongitud(dto.getLongitud());

        paradaRepository.save(parada);

        return ParadaMapper.convertToDTO(parada);
    }

    @Transactional
    public void delete(Long id) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la parada con id: " + id));
        paradaRepository.delete(parada);
    }
}