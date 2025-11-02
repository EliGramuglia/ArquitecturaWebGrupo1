package org.example.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Usuario cliente;

    @Column(nullable = false)
    private Cuenta cuenta;

    public Facturacion(Usuario cliente, Cuenta cuenta) {
        this.cliente = cliente;
        this.cuenta = cuenta;
    }
}
