package org.example.viaje.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Viaje viajeId;

    @Column(nullable = false)
    private Double extra;

    public Tarifa(@NotNull(message = "El monto es obligatorio") Double monto, @NotNull(message = "El viajeId es obligatorio") Long viajeId, @NotNull(message = "El usuarioId es obligatorio") Long usuarioId) {
        this.monto = monto;
        this.viajeId = viajeId;
    }

    public Double getExtra() {
        return monto + extra;
    }
}
