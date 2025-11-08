package org.example.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.cuenta.EstadoCuenta;

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

    @Column
    private LocalDate fecha_alta;

    @Column
    private EstadoCuenta estadoCuenta;

    @Column
    private Integer monto;

    @Column
    private boolean premium;

    @ManyToMany
    private List<Usuario> clientes;

    public Cuenta(@NotNull(message = "La fecha de alta es obligatoria") LocalDate fechaAlta, @NotNull(message = "El estado es obligatorio") EstadoCuenta estadoCuenta, @NotNull(message = "El monto es obligatorio") Integer monto) {
        this.fecha_alta = fechaAlta;
        this.estadoCuenta = estadoCuenta;
        this.monto = monto;
    }
}
