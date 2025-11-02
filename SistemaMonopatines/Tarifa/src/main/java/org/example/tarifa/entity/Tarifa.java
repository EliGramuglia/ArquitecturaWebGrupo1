package org.example.tarifa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Long viajeId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double extra;

    public Tarifa(@NotNull(message = "El monto es obligatorio") Double monto, @NotNull(message = "El viajeId es obligatorio") Long viajeId, @NotNull(message = "El usuarioId es obligatorio") Long usuarioId) {
        this.monto = monto;
        this.viajeId = viajeId;
        this.usuarioId = usuarioId;
    }

    public Double getExtra() {
        return monto + extra;
    }
    // Debo guardar también info de idMonopatin o el viaje ya lo tiene?

}