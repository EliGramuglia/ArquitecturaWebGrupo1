package org.example.viaje.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Viaje;
import org.example.viaje.mapper.PausaMapper;
import org.example.viaje.mapper.ViajeMapper;
import org.example.viaje.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ViajeService {
    private final ViajeRepository viajeRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public ViajeResponseDTO save(ViajeRequestDTO viaje) {
        Viaje viajeNuevo = ViajeMapper.convertToEntity(viaje);
        Viaje viajePersistido = viajeRepository.save(viajeNuevo);
        return ViajeMapper.convertToDTO(viajePersistido);
    }

    public List<ViajeResponseDTO> findAll() {
        return viajeRepository.findAll()
                .stream()
                .map(ViajeMapper::convertToDTO)
                .toList();
    }

    public ViajeResponseDTO findById(Long id) {
        Viaje viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe viaje con id: " + id));
        return ViajeMapper.convertToDTO(viaje);
    }

    public ViajeResponseDTO update(Long id, ViajeRequestDTO viajeDTO) {
        Viaje viajeEditar = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe viaje con id: " + id));
        viajeEditar.setFechaHoraInicio(viajeDTO.getFechaHoraInicio());
        viajeEditar.setFechaHoraFin(viajeDTO.getFechaHoraFin());
        viajeEditar.setTarifa(viajeDTO.getTarifa());
        viajeEditar.setIdMonopatin(viajeDTO.getIdMonopatin());
        viajeEditar.setIdParadaInicio(viajeDTO.getIdParadaInicio());
        viajeEditar.setIdParadaFinal(viajeDTO.getIdParadaFinal());
        viajeEditar.setKmRecorridos(viajeDTO.getKmRecorridos());
        viajeEditar.setIdCliente(viajeDTO.getIdCliente());

        // Mapea la lista de pausas del DTO a entidades (si es que hay pausas)
        if (viajeDTO.getPausas() != null) {
            List<Pausa> pausas = viajeDTO.getPausas()
                    .stream()
                    .map(PausaMapper::convertToEntity)
                    .toList();

            // Elimina las pausas viejas y agrega las nuevas
            viajeEditar.getPausas().clear();
            viajeEditar.getPausas().addAll(pausas);
        }

        Viaje viajePersistido = viajeRepository.save(viajeEditar);
        return ViajeMapper.convertToDTO(viajePersistido);
    }

    public void delete(Long id) {
        Viaje viajeEliminar = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe viaje con id: "+ id));
        viajeRepository.delete(viajeEliminar);
    }
}
