package org.example.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.cuenta.EstadoCuenta;

import java.time.LocalDate;
import java.time.Month;
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
    private Boolean premium;

    @Column
    private Double kmAcumuladosMes;

    @Column
    private int mesCupo;

    @ManyToMany
    private List<Usuario> clientes;

    public Cuenta(LocalDate fechaAlta, EstadoCuenta estadoCuenta, Integer monto, Boolean premium) {
        this.fecha_alta = fechaAlta;
        this.estadoCuenta = estadoCuenta;
        this.monto = monto;
        this.premium = premium;
    }

    public int getMesCupo() {
        LocalDate fecha = LocalDate.now();
        int mesNumero = fecha.getMonthValue();
        return mesNumero;
    }
}
