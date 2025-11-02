package org.example.cuenta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cuenta.utils.Estado;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroCuenta;

    @Column(nullable = false)
    private LocalDate fecha_alta;

    @Column (nullable = false)
    private Estado estado;

    @Column
    private Integer monto;

    @ElementCollection
    private List<Integer> clientesId; // Guarda todos los ids de las clientes asociados

    public Cuenta(@NotNull(message = "La fecha de alta es obligatoria") LocalDate fechaAlta, @NotNull(message = "El estado es obligatorio") Estado estado, @NotNull(message = "El monto es obligatorio") Integer monto) {
    this.fecha_alta = fechaAlta;
    this.estado = estado;
    this.monto = monto;
    }
}
