package org.example.viaje.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.PausaResponseDTO;
import org.example.viaje.dto.response.TotalFacturadoDTO;
import org.example.viaje.dto.response.ViajeResponseDTO;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Tarifa;
import org.example.viaje.entity.Viaje;
import org.example.viaje.mapper.PausaMapper;
import org.example.viaje.mapper.ViajeMapper;
import org.example.viaje.repository.PausaRepository;
import org.example.viaje.repository.TarifaRepository;
import org.example.viaje.repository.ViajeRepository;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.List;



@AllArgsConstructor
@Service
public class ViajeService {
    private final ViajeRepository viajeRepository;
    private final TarifaRepository tarifaRepository;
    private final PausaRepository pausaRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public ViajeResponseDTO save(ViajeRequestDTO viaje) {
        Viaje viajeNuevo = ViajeMapper.convertToEntity(viaje);
        // Buscamos la tarifa activa
        Tarifa tarifaActual = tarifaRepository.findFirstByActivaTrueOrderByFechaInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("No hay una tarifa activa configurada"));

        // Asignamos la tarifa actual al viaje
        viajeNuevo.setTarifa(tarifaActual);

        // Calculo el costo total del viaje
        Double costoViaje = calcularCostoTotal(viajeNuevo, tarifaActual);
        viajeNuevo.setCostoTotal(costoViaje);

        Viaje viajePersistido = viajeRepository.save(viajeNuevo);
        return ViajeMapper.convertToDTO(viajePersistido);
    }

    // Método para calcular cúanto costo el Viaje, y persistirlo luego en la base
    private Double calcularCostoTotal(Viaje viaje, Tarifa tarifa) {
        Double km = viaje.getKmRecorridos();
        List<Pausa> pausas = viaje.getPausas();

        // Si el viaje no tuvo pausas, multiplico los km por el precio normal (precioBase)
        if(pausas == null || pausas.isEmpty()) {
            return km * tarifa.getPrecioBase();
        }

        // Si tuvo pausas, entonces sumo la duración para ver si pasaron los 15 minutos
        long minutosTotales = 0;
        for (Pausa pausa : pausas) {
            if (pausa.getFin() != null) {
                long duracion = Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes();
                minutosTotales += duracion;
                if (minutosTotales > 15) {
                    break; // No hace falta seguir porque ya supero los 15 min
                }
            }
        }

        // Decido el tipo de tarifa que se va a aplicar
        if(minutosTotales >= 15){
            return km * tarifa.getPrecioRecargaPorPausa();
        } else {
            return km * tarifa.getPrecioBase();
        }
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
        viajeEditar.setIdMonopatin(viajeDTO.getIdMonopatin());
        viajeEditar.setIdParadaInicio(viajeDTO.getIdParadaInicio());
        viajeEditar.setIdParadaFinal(viajeDTO.getIdParadaFinal());
        viajeEditar.setKmRecorridos(viajeDTO.getKmRecorridos());
        viajeEditar.setIdUsuario(viajeDTO.getIdUsuario());

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

    /*-------------------- ENDPOINTS ANIDADOS PARA PAUSAR EL VIAJE -----------------------*/
    public PausaResponseDTO iniciarPausa(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        boolean hayPausaAbierta = viaje.getPausas().stream()
                .anyMatch(p -> p.getFin() == null);

        if (hayPausaAbierta) {
            throw new RuntimeException("Ya hay una pausa abierta en este viaje");
        }
        Pausa pausa = new Pausa();
        pausa.setInicio(LocalTime.now());
        pausa.setFin(null);
        pausa.setViaje(viaje);

        Pausa pausaGuardada = pausaRepository.save(pausa);

        return PausaMapper.convertToDTO(pausaGuardada);
    }

    @Transactional // Si cualquier error ocurre dentro del método, toda la transacción se revierte automáticamente y la base de datos queda como estaba antes de llamar ese método.
    public PausaResponseDTO finalizarPausa(Long viajeId, Long pausaId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
        // Buscar la pausa dentro de la lista
        Pausa pausa = viaje.getPausas().stream()
                .filter(p -> p.getId().equals(pausaId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pausa no encontrada en este viaje"));

        pausa.setFin(LocalTime.now());
        // Calcular duración en minutos
        long duracionMinutos = Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes();
        if (duracionMinutos > 15) {
            // Marcar que el monopatín se volvio a usar (actualizar el estado del viaje, tarifa, etc)
        }

        viajeRepository.save(viaje);
        return PausaMapper.convertToDTO(pausa);
    }

    public List<PausaResponseDTO> getPausas(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        return viaje.getPausas().stream()
                .map(PausaMapper::convertToDTO)
                .toList();
    }

    /*-------------------- ENDPOINTS PARA LOS SERVICIOS -----------------------*/
    public TotalFacturadoDTO obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        // Crea la fecha que representa el primer día del mes de inicio, a las 00:00 hs.
        LocalDateTime inicio = LocalDateTime.of(anio, mesInicio, 1,0,0);
        // Representa el ultimo dia de un mes en especifico, segun el año
        LocalDate ultimoDia = YearMonth.of(anio, mesFin).atEndOfMonth();
        // Le agrega la última hora del día a ese ultimo día del mes
        LocalDateTime fin = ultimoDia.atTime(23, 59, 59);

        List<Viaje> viajes = viajeRepository.findByFechaHoraInicioBetween(inicio, fin);

        Double total = viajes.stream()
                .mapToDouble(v -> v.getCostoTotal() != null ? v.getCostoTotal() : 0.0) //Sirve para que si en algún momento, por error, se guardó un viaje incompleto o con un costo sin calcular no tire un NullPointerException.
                .sum();

        return new TotalFacturadoDTO(anio, mesInicio, mesFin, total);
    }


}
