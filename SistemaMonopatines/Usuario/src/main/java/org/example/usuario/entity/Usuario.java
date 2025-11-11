package org.example.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.utils.Rol;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column
    private Integer nroCelular;

    @Column(nullable = false)
    @Email
    private String email;

    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta", // tabla intermedia entre usuario y cuenta
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
    )
    private List<Cuenta> cuentas = new ArrayList<>();

    @Column(nullable = false)
    private Rol rol;

    public Usuario(String nombre, String apellido,String email, Integer nroCelular, Rol rol, List<Cuenta> cuentasId) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.nroCelular = nroCelular;
        this.rol = rol;
        this.cuentas = cuentasId;
    }
}