package org.example.viaje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private Double kmRerridos;

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
}
