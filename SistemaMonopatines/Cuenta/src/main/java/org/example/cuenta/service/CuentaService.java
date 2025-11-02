package org.example.cuenta.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.cuenta.dto.request.CuentaRequestDTO;
import org.example.cuenta.dto.response.CuentaResponseDTO;
import org.example.cuenta.entity.Cuenta;
import org.example.cuenta.mapper.CuentaMapper;
import org.example.cuenta.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public CuentaResponseDTO save(@Valid CuentaRequestDTO request) {
        Cuenta nuevo = CuentaMapper.convertToEntity(request);
        Cuenta creado = cuentaRepository.save(nuevo);
        return CuentaMapper.convertToDTO(creado);
    }

    public List<CuentaResponseDTO> findAll() {
        return cuentaRepository.findAll()
                .stream()
                .map(CuentaMapper::convertToDTO)
                .toList();
    }

    public CuentaResponseDTO findById(Long nroCuenta) {
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No existe la cuenta con el id: " + nroCuenta));
        return CuentaMapper.convertToDTO(cuenta);
    }

    public CuentaResponseDTO update(Long nroCuenta, CuentaRequestDTO cuenta) {
        Cuenta cuentaEditar = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id: " + nroCuenta));

        cuentaEditar.setNroCuenta(nroCuenta);
        cuentaEditar.setFecha_alta(cuenta.getFecha_alta());
        cuentaEditar.setEstado(cuenta.getEstado());
        cuentaEditar.setMonto(cuenta.getMonto());
        cuentaRepository.save(cuentaEditar);

        return CuentaMapper.convertToDTO(cuentaEditar);
    }

    public void delete(Long nroCuenta) {
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una cuenta con el id: "+ nroCuenta));
        cuentaRepository.delete(cuenta);
    }
}
