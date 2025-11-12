package org.example.viaje.service;

import lombok.AllArgsConstructor;
import org.example.viaje.client.CuentaFeignClient;
import org.example.viaje.client.cuenta.dto.response.CuentaResponseDTO;
import org.example.viaje.client.UsuarioFeignClient;
import org.example.viaje.client.dto.UsuarioViajesCountDTO;
import org.example.viaje.client.dto.response.UsuarioViajesDTO;
import org.example.viaje.client.dto.response.ViajeMonopatinResponseDTO;
import org.example.viaje.dto.request.PausaRequestDTO;
import org.example.viaje.dto.request.ViajeRequestDTO;
import org.example.viaje.dto.response.*;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Tarifa;
import org.example.viaje.entity.Viaje;
import org.example.viaje.mapper.PausaMapper;
import org.example.viaje.mapper.ViajeMapper;
import org.example.viaje.repository.PausaRepository;
import org.example.viaje.repository.TarifaRepository;
import org.example.viaje.repository.ViajeRepository;
import org.example.viaje.utils.usuario.Rol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class ViajeService {
    private final ViajeRepository viajeRepository;
    private final TarifaRepository tarifaRepository;
    private final PausaRepository pausaRepository;
    private final CuentaFeignClient cuentaFeignClient;
    private final Double descuento = 0.5;
    private final UsuarioFeignClient usuarioFeignClient;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    @Transactional
    public ViajeResponseDTO save(ViajeRequestDTO viaje) {
        // Validación: la fecha de fin no puede ser anterior a la de inicio
        if (viaje.getFechaHoraFin().isBefore(viaje.getFechaHoraInicio())) {
            throw new IllegalArgumentException("La fecha de fin del viaje no puede ser anterior a la fecha de inicio");
        }

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

    //al asignar el cobro de un viaje si el usuario es premium debemos restarle los km
    //disponibles que tiene y cobrarle 50% de la tarifa una vez que ya no tenga

    // Método para calcular cúanto costo el Viaje, y persistirlo luego en la base
    private Double calcularCostoTotal(Viaje viaje, Tarifa tarifa) {
        Double km = viaje.getKmRecorridos();
        List<Pausa> pausas = viaje.getPausas();

        CuentaResponseDTO cuenta = cuentaFeignClient.obtenerCuentaPorUsuario(viaje.getIdCliente());
        cuenta = cuentaFeignClient.verificarCupo(cuenta.getNroCuenta());
        Boolean isPremium = cuenta.getPremium();
//        Double kmAcobrar = kmAcobrar(isPremium, cuenta, km);

        // Si el viaje no tuvo pausas, multiplico los km por el precio normal (precioBase)
        if(pausas == null || pausas.isEmpty()) {
//            if(isPremium) {
//               return kmAcobrar * tarifa.getPrecioBase()*descuento;
//            }
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
//            if(isPremium) {
//                return kmAcobrar * tarifa.getPrecioRecargaPorPausa() *  descuento;
//            }
            return km * tarifa.getPrecioRecargaPorPausa();
        }

//        if(isPremium) {
//            return kmAcobrar * tarifa.getPrecioBase()*descuento;
//        }
        return km * tarifa.getPrecioBase();
    }

    /**Metodo para determinar cantidad de km a cobrar segun que tipo de usuario es  */
//    private Double kmAcobrar(Boolean isPremium,  CuentaResponseDTO cuenta, Double kmRecorridos){
//        if(isPremium) {
//            Double kmDisponibles = cuenta.getKmAcumuladosMes();
//            if(kmDisponibles >= kmRecorridos) {
//                Double total = kmDisponibles - kmRecorridos;
//                cuenta.setKmAcumuladosMes(total);
//                cuentaFeignClient.update(cuenta, cuenta.getNroCuenta());
//                return 0.0;
//            }
//            else {
//                Double kmAcobrar = kmRecorridos - kmDisponibles;
//                cuenta.setKmAcumuladosMes(0.0);
//                cuentaFeignClient.update(cuenta, cuenta.getNroCuenta());
//                return kmAcobrar;
//            }
//        }
//        return kmRecorridos;
//    }

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

    @Transactional
    public ViajeResponseDTO update(Long id, ViajeRequestDTO viajeDTO) {
        Viaje viajeEditar = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe viaje con id: " + id));
        viajeEditar.setFechaHoraInicio(viajeDTO.getFechaHoraInicio());
        viajeEditar.setFechaHoraFin(viajeDTO.getFechaHoraFin());
        viajeEditar.setIdMonopatin(viajeDTO.getIdMonopatin());
        viajeEditar.setIdParadaInicio(viajeDTO.getIdParadaInicio());
        viajeEditar.setIdParadaFinal(viajeDTO.getIdParadaFinal());
        viajeEditar.setKmRecorridos(viajeDTO.getKmRecorridos());
        viajeEditar.setIdCliente(viajeDTO.getIdCliente());

        // Recalcular el costo total con la tarifa actual (por si cambió)
        Tarifa tarifaActual = tarifaRepository.findFirstByActivaTrueOrderByFechaInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("No hay una tarifa activa configurada"));
        viajeEditar.setTarifa(tarifaActual);
        Double costoViaje = calcularCostoTotal(viajeEditar, tarifaActual);
        viajeEditar.setCostoTotal(costoViaje);

        Viaje viajePersistido = viajeRepository.save(viajeEditar);
        return ViajeMapper.convertToDTO(viajePersistido);
    }

    @Transactional
    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    /*-------------------- ENDPOINTS ANIDADOS PARA PAUSAR EL VIAJE -----------------------*/
    @Transactional // Si cualquier error ocurre dentro del método, toda la transacción se revierte automáticamente y la base de datos queda como estaba antes de llamar ese método.
    public PausaResponseDTO createPausa(Long viajeId, PausaRequestDTO dto) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        Pausa pausa = PausaMapper.convertToEntity(dto);
        pausa.setViaje(viaje);

        // Calcula la duración de la pausa en minutos
        LocalTime inicio = pausa.getInicio();
        LocalTime fin = pausa.getFin();
        if (inicio != null && fin != null) {
            pausa.setDuracion(calcularDuracionEnMinutos(inicio, fin));
        }

        // Guardamos la pausa
        Pausa pausaGuardada = pausaRepository.save(pausa);
        viaje.getPausas().add(pausaGuardada);

        // Recalcular el costo total del viaje
        Tarifa tarifa = viaje.getTarifa();
        Double nuevoCosto = calcularCostoTotal(viaje, tarifa);
        viaje.setCostoTotal(nuevoCosto);

        viajeRepository.save(viaje);
        return PausaMapper.convertToDTO(pausaGuardada);
    }

    // Calcula la duración de la pausa en minutos
    private long calcularDuracionEnMinutos(LocalTime inicio, LocalTime fin) {
        return Duration.between(inicio, fin.isBefore(inicio) ? fin.plusHours(24) : fin).toMinutes();
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


    public List<MonopatinViajesDTO> obtenerMonopatinesConMasViajes(int anio, long cantidadMinima) {
        return viajeRepository.findMonopatinesConMasViajes(anio, cantidadMinima);
    }

    // Consultar los usuarios que más utilizan los monopatines, filtrando por período y por tipo de usuario.
    public List<UsuarioViajesDTO> obtenerUsuariosMasActivos(LocalDate inicio, LocalDate fin, Rol tipoUsuario) {
        List<UsuarioViajesCountDTO> viajesPorUsuario = viajeRepository.contarViajesPorUsuario(inicio, fin);

        List<UsuarioViajesDTO> resultado = new ArrayList<>();

        for(UsuarioViajesCountDTO dtoCount: viajesPorUsuario) {
            UsuarioViajesDTO usuario = usuarioFeignClient.findById(dtoCount.getUsuarioId());

            if(usuario.getRol().equals(tipoUsuario)) {
                resultado.add(new UsuarioViajesDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getRol(),
                        dtoCount.getCantidadViajes()
                ));
            }
        }
        return resultado;
    }

    // Cuantas veces use los monopatines en el periodo y usuarios relacionados a mi cuenta
    public UsoMonopatinUsuarioDTO contarViajesPorUsuario(Long idUsuario, LocalDate inicio, LocalDate fin) {
        LocalDateTime fechaInicio = inicio.atStartOfDay();
        LocalDateTime fechaFin = fin.atTime(23, 59, 59);

        UsoMonopatinUsuarioDTO resultado = viajeRepository.contarViajesPorUsuarioYRango(idUsuario, fechaInicio, fechaFin);
        if (resultado == null) {
            return new UsoMonopatinUsuarioDTO(idUsuario, 0L); // Si no hizo viajes, devolver 0
        }
        return resultado;
    }

    public List<ViajeMonopatinResponseDTO> getByMonopatin(Long monopatinId) {
        List<Viaje> viajes = viajeRepository.findByIdMonopatin(monopatinId);
        List<ViajeMonopatinResponseDTO> out = new ArrayList<>();

        for (Viaje v : viajes) {
            long minutosPausa = calcularMinutosPausa(v); // ← clave

            out.add(new ViajeMonopatinResponseDTO(
                    v.getId(),
                    v.getKmRecorridos(),
                    v.getFechaHoraInicio(),
                    v.getFechaHoraFin(),
                    minutosPausa
            ));
        }
        return out;
    }

    private long calcularMinutosPausa(Viaje v) {
        if (v.getPausas() == null || v.getPausas().isEmpty()) return 0L;

        long total = 0L;
        for (Pausa p : v.getPausas()) {
            if (p.getInicio() != null && p.getFin() != null) {
                total += java.time.Duration.between(p.getInicio(), p.getFin()).toMinutes();
            }
        }
        return Math.max(total, 0L);
    }

}
