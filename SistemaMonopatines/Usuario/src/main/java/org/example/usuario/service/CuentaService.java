package org.example.usuario.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.usuario.dto.request.CuentaRequestDTO;
import org.example.usuario.dto.response.CuentaResponseDTO;
import org.example.usuario.entity.Cuenta;
import org.example.usuario.entity.Usuario;
import org.example.usuario.mapper.CuentaMapper;
import org.example.usuario.repository.CuentaRepository;
import org.example.usuario.repository.UsuarioRepository;
import org.example.usuario.utils.cuenta.EstadoCuenta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;
    private final UsuarioRepository usuarioRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    @Transactional
    public CuentaResponseDTO save(@Valid CuentaRequestDTO request) {
        Cuenta nuevo = CuentaMapper.convertToEntity(request);
        Cuenta cuentaCreada = cuentaRepository.save(nuevo);

        for(Long idUsuario: request.getIdUsuario()){
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("No hay un Usuario con el id: " + idUsuario));
            usuario.getCuentas().add(cuentaCreada);
            cuentaCreada.getClientes().add(usuario);
            usuarioRepository.save(usuario);
        }
        cuentaRepository.save(cuentaCreada);
        return CuentaMapper.convertToDTO(cuentaCreada);
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

    @Transactional
    public CuentaResponseDTO update(Long nroCuenta, CuentaRequestDTO cuenta) {
        Cuenta cuentaEditar = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id: " + nroCuenta));

        cuentaEditar.setNroCuenta(nroCuenta);
        cuentaEditar.setFecha_alta(cuenta.getFecha_alta());
        cuentaEditar.setEstadoCuenta(cuenta.getEstadoCuenta());
        cuentaEditar.setMonto(cuenta.getMonto());
        cuentaEditar.setPremium(cuenta.getPremium());
        cuentaEditar.setKmAcumuladosMes(cuenta.getKmAcumuladosMes());
        for (Long idUsuario : cuenta.getIdUsuario()) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("No hay un Usuario con el id: " + idUsuario));

            if(!cuentaEditar.getClientes().contains(usuario)){
                cuentaEditar.getClientes().add(usuario);
                usuario.getCuentas().add(cuentaEditar);
                usuarioRepository.save(usuario);

            }
        }
        cuentaRepository.save(cuentaEditar);
        return CuentaMapper.convertToDTO(cuentaEditar);
    }

    @Transactional
    public void delete(Long nroCuenta) {
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una cuenta con el id: "+ nroCuenta));
        cuentaRepository.delete(cuenta);
    }

    /** Verifica si debe renovarse el cupo mensual de 100 km */
    @Transactional
    public CuentaResponseDTO verificarYRnovarCupo(Long nroCuenta) {
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (Boolean.TRUE.equals(cuenta.getPremium())) {
            LocalDate hoy = LocalDate.now();

            if (cuenta.getUltimaRenovacionCupo() == null ||
                    cuenta.getUltimaRenovacionCupo().getMonthValue() != hoy.getMonthValue() ||
                    cuenta.getUltimaRenovacionCupo().getYear() != hoy.getYear()) {

                cuenta.setKmAcumuladosMes(100.0);
                cuenta.setUltimaRenovacionCupo(hoy);
                cuentaRepository.save(cuenta);
            }
        }

        return CuentaMapper.convertToDTO(cuenta);
    }
    /*------------------------ ENDPOINTS PARA LOS SERVICIOS ------------------------*/
    @Transactional
    public CuentaResponseDTO anularCuenta(Long nroCuenta, String estado) {
        Cuenta cuenta = cuentaRepository.findById(nroCuenta)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));

        EstadoCuenta estadoCuentaEnum;
        try {
            estadoCuentaEnum = EstadoCuenta.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado incorrecto. Valores permitidos: "
                    + java.util.Arrays.toString(EstadoCuenta.values()));
        }

        cuenta.setEstadoCuenta(estadoCuentaEnum);
        cuentaRepository.save(cuenta);

        return cuentaMapper.convertToDTO(cuenta);
    }

    public CuentaResponseDTO obtenerCuentaPorUsuario(Long idUsuario) {
        // Buscamos todas las cuentas del usuario
        List<Cuenta> cuentas = cuentaRepository.findCuentasByUsuarioId(idUsuario);

        if (cuentas.isEmpty()) {
            throw new RuntimeException("No se encontró una cuenta para el usuario con ID: " + idUsuario);
        }

        // Tomamos la primera cuenta asociada (como si fuera la principal para decirdir si es o no premium)
        Cuenta cuenta = cuentas.get(0);

        // Mapeamos a DTO
        return CuentaMapper.convertToDTO(cuenta);
    }
}
