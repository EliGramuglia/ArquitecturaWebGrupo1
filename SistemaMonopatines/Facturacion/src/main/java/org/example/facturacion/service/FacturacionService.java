package org.example.facturacion.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.facturacion.dto.request.FacturacionRequestDTO;
import org.example.facturacion.dto.response.FacturacionResponseDTO;
import org.example.facturacion.entity.Facturacion;
import org.example.facturacion.mapper.FacturacionMapper;
import org.example.facturacion.repository.FacturacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FacturacionService {
    private final FacturacionRepository facturacionRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public FacturacionResponseDTO save(@Valid FacturacionRequestDTO request) {
        Facturacion nuevo = FacturacionMapper.convertToEntity(request);
        Facturacion creado = facturacionRepository.save(nuevo);
        return FacturacionMapper.convertToDTO(creado);
    }

    public List<FacturacionResponseDTO> findAll() {
        return facturacionRepository.findAll()
                .stream()
                .map(FacturacionMapper::convertToDTO)
                .toList();
    }

    public FacturacionResponseDTO findById(Long id) {
        Facturacion facturacion = facturacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la factura con los id: " + id));
        return FacturacionMapper.convertToDTO(facturacion);
    }

    public FacturacionResponseDTO update(Long id, FacturacionRequestDTO facturacion) {
        Facturacion facturacionEditar = facturacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la factura con id: " + id));

        facturacionEditar.setId(id);
        facturacionEditar.setIdCliente(facturacion.getIdCliente());
        facturacionEditar.setIdCuenta(facturacion.getIdCuenta());
        facturacionRepository.save(facturacionEditar);

        return FacturacionMapper.convertToDTO(facturacionEditar);
    }

    public void delete(Long id) {
        Facturacion facturacion = facturacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una factura con el id: "+ id));
        facturacionRepository.delete(facturacion);
    }
}
