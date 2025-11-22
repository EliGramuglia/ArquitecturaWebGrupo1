package org.example.usuario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /*@Column(nullable = false)
    private Rol rol;*/

    @Column( nullable = false )
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    private List<Authority> authorities;

    public Usuario(String nombre, String apellido, String email, Integer nroCelular, List<Authority> authorities, String password, List<Cuenta> cuentasId) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.nroCelular = nroCelular;
        this.authorities = authorities;
        this.cuentas = cuentasId;
        this.password = password;
    }

}