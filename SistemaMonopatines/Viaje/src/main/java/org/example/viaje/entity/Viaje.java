package org.example.viaje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private Double tarifa;

    @Column(nullable = false)
    private Long idMonopatin;

    @Column(nullable = false)
    private Long idCliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Un viaje puede tener muchas pausas
    @JoinColumn(name = "viaje_id") // En la tabla Pausa existe la column viaje_id
    private List<Pausa> pausas;

    public Viaje(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Double kmRecorridos, Long idParadaInicio, Long idParadaFinal, Double tarifa, Long idMonopatin, Long idCliente) {
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

// Navegación Unidireccional (por eso en Pausa no se anota nada más de la relación)