package org.example.viaje.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class    Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Precio base del viaje (puede ser por min o km)
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    // Recargo si la pausa supera los 15 minutos
    @Column(name = "precio_recarga_pausa")
    private Double precioRecargaPorPausa;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate fechaInicioVigencia;

    // Puede ser null si sigue vigente
    @Column(name = "fecha_fin_vigencia", nullable = false)
    private LocalDate fechaFinVigencia;

    // Tarifa activa o no(para invalidar tarifas sin borrarlas)
    @Column(nullable = false)
    private Boolean activa = true;


    public Tarifa(Double precioBase, Double precioRecargaPorPausa, LocalDate fechaInicioVigencia, LocalDate fechaFinVigencia) {
        this.precioBase = precioBase;
        this.precioRecargaPorPausa = precioRecargaPorPausa;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
    }
}
