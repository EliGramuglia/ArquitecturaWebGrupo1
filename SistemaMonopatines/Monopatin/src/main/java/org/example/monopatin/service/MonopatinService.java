package org.example.monopatin.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.monopatin.dto.request.MonopatinRequestDTO;
import org.example.monopatin.dto.response.MonopatinResponseDTO;
import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.mapper.MonopatinMapper;
import org.example.monopatin.repository.MonopatinRepository;
import org.example.monopatin.utils.Estado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MonopatinService {

    private final MonopatinRepository monopatinRepository;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public MonopatinResponseDTO save(@Valid MonopatinRequestDTO request) {
        Monopatin nuevo = MonopatinMapper.convertToEntity(request);
        Monopatin creado = monopatinRepository.save(nuevo);
        return MonopatinMapper.convertToDTO(creado);
    }

    public List<MonopatinResponseDTO> findAll() {
        return monopatinRepository.findAll()
                .stream()
                .map(MonopatinMapper::convertToDTO)
                .toList();
    }

    public MonopatinResponseDTO findById(String id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Monopatin con el id: " + id));
        return MonopatinMapper.convertToDTO(monopatin);
    }

    public MonopatinResponseDTO update(String id, MonopatinRequestDTO monopatin) {
        Monopatin monopatinEditar = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Monopatin con id: " + id));

        monopatinEditar.setId(id);
        monopatinEditar.setLatitud(monopatin.getLatitud());
        monopatinEditar.setLongitud(monopatin.getLongitud());
        monopatinEditar.setKmRecorridos(monopatin.getKmRecorridos());
        monopatinEditar.setViajeId(monopatin.getViajeId());
        monopatinEditar.setEstado(monopatin.getEstado());
        monopatinEditar.setHorasUso(monopatin.getHorasUso());
        monopatinRepository.save(monopatinEditar);

        return MonopatinMapper.convertToDTO(monopatinEditar);
    }

    public void delete(String id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un Monopatin con el id: "+ id));
        monopatinRepository.delete(monopatin);
    }

    /*-------------------------- ENDPOINTS SERVICIOS --------------------------*/
    public MonopatinResponseDTO cambiarEstadoMonopatin(String id, String estado) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Monopatin con id: " + id));
        switch (estado){
                case "mantenimiento":
                    monopatin.setEstado(Estado.EN_MANTENIMIENTO);
                    break;
                case "en-uso":
                    monopatin.setEstado(Estado.EN_USO);
                    break;
                default:
                    monopatin.setEstado(Estado.DISPONIBLE);
        }
        monopatinRepository.save(monopatin);
        return MonopatinMapper.convertToDTO(monopatin);
    }

}