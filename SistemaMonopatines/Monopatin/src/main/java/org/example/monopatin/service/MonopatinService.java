package org.example.monopatin.service;

import lombok.AllArgsConstructor;
import org.example.monopatin.client.ParadaFeignClient;
import org.example.monopatin.client.ViajeFeignClient;
import org.example.monopatin.client.parada.dto.response.ParadaResponseDTO;
import org.example.monopatin.client.viaje.dto.response.ViajeMonopatinResponseDTO;
import org.example.monopatin.dto.request.MonopatinRequestDTO;
import org.example.monopatin.dto.request.ReporteUsoRequestDTO;
import org.example.monopatin.dto.response.MonopatinResponseDTO;
import org.example.monopatin.dto.response.MonopatinUsoResponseDTO;
import org.example.monopatin.dto.response.ReporteUsoResponseDTO;
import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.mapper.MonopatinMapper;
import org.example.monopatin.repository.MonopatinRepository;
import org.example.monopatin.utils.EstadoMonopatin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MonopatinService {
    private final MonopatinRepository monopatinRepository;
    private final ParadaFeignClient paradaFeignClient;
    private final ViajeFeignClient viajeFeignClient;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    @Transactional
    public MonopatinResponseDTO save(MonopatinRequestDTO request) {
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

    @Transactional
    public MonopatinResponseDTO update(String id, MonopatinRequestDTO monopatin) {
        Monopatin monopatinEditar = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Monopatin con id: " + id));

        monopatinEditar.setId(id);
        monopatinEditar.setLatitud(monopatin.getLatitud());
        monopatinEditar.setLongitud(monopatin.getLongitud());
        monopatinEditar.setKmRecorridos(monopatin.getKmRecorridos());
        monopatinEditar.setEstadoMonopatin(monopatin.getEstadoMonopatin());
        monopatinEditar.setHorasUso(monopatin.getHorasUso());
        monopatinRepository.save(monopatinEditar);

        return MonopatinMapper.convertToDTO(monopatinEditar);
    }

    @Transactional
    public void delete(String id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un Monopatin con el id: "+ id));
        monopatinRepository.delete(monopatin);
    }

    /*-------------------------- ENDPOINTS SERVICIOS --------------------------*/
    @Transactional
    public MonopatinResponseDTO cambiarEstadoMonopatin(String id, String estado) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Monopatin con id: " + id));

        // Convierte el string a enum, validando el valor
        EstadoMonopatin estadoMonopatinEnum;
        try {
            estadoMonopatinEnum = EstadoMonopatin.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado incorrecto. Valores permitidos: "
                    + java.util.Arrays.toString(EstadoMonopatin.values()));
        }

        monopatin.setEstadoMonopatin(estadoMonopatinEnum);
        monopatinRepository.save(monopatin);

        return MonopatinMapper.convertToDTO(monopatin);
    }

    @Transactional
    public MonopatinResponseDTO ubicarEnParada(String monopatinId, Long paradaId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el monopatín con id: " + monopatinId));

        // Le pido al MS de parada la info de la parada con el id recibido por parámetro
        ParadaResponseDTO parada = paradaFeignClient.getById(paradaId).getBody();
        if (parada == null) {
            throw new IllegalArgumentException("No existe la parada con id: " + paradaId);
        }

        // Le seteo la misma latitud y longitud de la parada al monopatín
        // Parada tiene latitud y longitud de tipo float y monopatin en Integer por eso se usa Math.round para redondear los valores de posición de parada.
        // ^ Consultar eso con las chicas, si mejor no nos conviene cambiar el tipo de latitud y longitud en la entidad Monopatin a float
        monopatin.setLatitud(Math.round(parada.getLatitud()));
        monopatin.setLongitud(Math.round(parada.getLongitud()));

        // Al estar en una parada el monopatín pasa a estar disponible -> consultar con las chicas si es correcto
        monopatin.setEstadoMonopatin(EstadoMonopatin.DISPONIBLE);

        // Se actualiza la info del monopatín
        monopatinRepository.save(monopatin);

        return MonopatinMapper.convertToDTO(monopatin);
    }

    public List<MonopatinResponseDTO> buscarMonopatinesCercanos(double lat, double lon) {
        double rango = 1.0;
        double latMin = lat - rango;
        double latMax = lat + rango;
        double lonMin = lon - rango;
        double lonMax = lon + rango;

        List<Monopatin> monopatines = monopatinRepository.findMonopatinesCercanos(latMin, latMax, lonMin, lonMax);
        return monopatines.stream()
                .map(MonopatinMapper::convertToDTO)
                .toList();
    }

    public ReporteUsoResponseDTO generarReporteUso(ReporteUsoRequestDTO request) {
        // Definimos el umbral de km que determina si un monopatín requiere mantenimiento.
        // Si el cliente no lo envía en el request, usamos un valor por defecto (1000 km).
        double umbral = request.getUmbralKmMantenimiento() != null
                ? request.getUmbralKmMantenimiento()
                : 1000.0;

        // Obtenemos todos los monopatines registrados en la base de datos.
        List<Monopatin> monopatinesTotales = monopatinRepository.findAll();

        List<MonopatinUsoResponseDTO> resultados = new ArrayList<>();

        // Recorremos cada monopatín para calcular su reporte individual.
        for (Monopatin monopatin : monopatinesTotales) {

            List<ViajeMonopatinResponseDTO> viajes = viajeFeignClient.getViajesByMonopatin(monopatin.getId());

            double totalKm = 0.0;             // suma total de kilómetros recorridos
            long totalMinutosConPausa = 0L;   // tiempo total de uso incluyendo pausas
            long totalMinutos = 0L;           // tiempo total ajustado según la configuración "incluirPausas"

            for (ViajeMonopatinResponseDTO viaje : viajes) {

                // Si el viaje tiene un valor de kilómetros, lo sumamos al total.
                if (viaje.getKmRecorridos() != null) {
                    totalKm += viaje.getKmRecorridos();
                }

                long minutosViaje = 0L;
                // Si las fechas de inicio y fin existen, calculamos la duración en minutos.
                if (viaje.getFechaHoraInicio() != null && viaje.getFechaHoraFin() != null) {
                    Duration duracion = Duration.between(viaje.getFechaHoraInicio(), viaje.getFechaHoraFin());
                    minutosViaje = duracion.toMinutes();
                }

                // Si el viaje tiene pausas registradas, las usamos. Si no, es 0
                long minutosPausa = viaje.getMinutosPausa() != null ? viaje.getMinutosPausa() : 0L;

                // Siempre acumulamos el tiempo total con pausas (tiempo real de uso).
                totalMinutosConPausa += minutosViaje;

                // Si el reporte incluye pausas, sumamos el tiempo total del viaje.
                // Si no las incluye, restamos los minutos de pausa (sin permitir valores negativos).
                if (request.isIncluirPausas()) {
                    totalMinutos += minutosViaje;
                } else {
                    long minutosSinPausa = minutosViaje - minutosPausa;
                    if (minutosSinPausa < 0) {
                        minutosSinPausa = 0;
                    }
                    totalMinutos += minutosSinPausa;
                }
            }

            // Si el total de kilómetros supera el umbral configurado, el monopatín requiere mantenimiento.
            boolean requiereMantenimiento = totalKm >= umbral;

            MonopatinUsoResponseDTO dto = new MonopatinUsoResponseDTO(
                    monopatin.getId(),
                    totalKm,
                    totalMinutos,
                    totalMinutosConPausa,
                    requiereMantenimiento
            );

            resultados.add(dto);
        }

        return new ReporteUsoResponseDTO(resultados);
    }

}