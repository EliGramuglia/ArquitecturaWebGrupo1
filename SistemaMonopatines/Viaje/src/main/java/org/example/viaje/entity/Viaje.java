package org.example.viaje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Viaje {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin",nullable = false)
    private LocalDateTime fechaHoraFin;

    @Column(name = "km_recorridos", nullable = false)
    private Double kmRecorridos;

    @Column(name = "parada_inicio", nullable = false)
    private Long idParadaInicio;

    @Column(name = "parada_final", nullable = false)
    private Long idParadaFinal;

    @Column(name="id_monopatin", nullable = false)
    private Long idMonopatin;

    @Column(name="id_cliente", nullable = false)
    private Long idCliente; // ponerle Usuario

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pausa> pausas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // Una misma tarifa se aplica a muchos viajes (por ejemplo, todos los viajes del mes de noviembre usan la misma tarifa).
    @JoinColumn(name = "tarifa_id") // Nombre de la columna que se va a crear en la tabla viaje para guardar el ID de la tarifa asociada
    private Tarifa tarifa;

    public Viaje(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Double kmRecorridos, Long idParadaInicio, Long idParadaFinal, Tarifa tarifa, Long idMonopatin, Long idCliente) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.kmRecorridos = kmRecorridos;
        this.idParadaInicio = idParadaInicio;
        this.idParadaFinal = idParadaFinal;
        this.tarifa = tarifa;
        this.idMonopatin = idMonopatin;
        this.idCliente = idCliente;
    }
}

/* cascade = CascadeType.ALL ->
    Si haces algo sobre un Viaje, se aplica automaticamente a todas sus Pausas.
*/
