package org.example.usuario.service;

import lombok.RequiredArgsConstructor;
import org.example.usuario.client.PagoMockClient;
import org.example.usuario.client.pagomock.dto.request.CrearPagoRequestDTO;
import org.example.usuario.client.pagomock.dto.response.CrearPagoResponseDTO;
import org.example.usuario.client.pagomock.dto.response.EstadoPagoResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoMockClient pagoMockClient;

    public CrearPagoResponseDTO cargarSaldo(Long idUsuario, Double monto, String descripcion) {
        CrearPagoRequestDTO req = new CrearPagoRequestDTO();
        req.setIdUsuario(idUsuario);
        req.setMonto(monto);
        req.setDescripcion(descripcion);

        return pagoMockClient.cargarSaldo(idUsuario, monto, descripcion);
    }

    public EstadoPagoResponseDTO consultarEstado(Long pagoId) {
        return pagoMockClient.obtenerEstado(pagoId);
    }

}