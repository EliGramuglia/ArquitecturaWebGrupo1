package org.example.tarifa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
    private Integer monto;

    @Column(nullable = false)
    private Long viajeId;

    @Column(nullable = false)
    private Long usuarioId;

    public Tarifa(@NotNull(message = "El monto es obligatorio") Integer monto, @NotNull(message = "El viajeId es obligatorio") Long viajeId, @NotNull(message = "El usuarioId es obligatorio") Long usuarioId) {
        this.monto = monto;
        this.viajeId = viajeId;
        this.usuarioId = usuarioId;
    }

    // Debo guardar también info de idMonopatin o el viaje ya lo tiene?

}