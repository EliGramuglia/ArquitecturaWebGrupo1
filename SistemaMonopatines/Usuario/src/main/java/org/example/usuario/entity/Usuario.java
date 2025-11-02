package org.example.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usuario.dto.CuentaDTO;
import org.example.usuario.utils.Rol;

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

    @Column (unique = true, nullable = false)
    @Email
    private String email;

    @ElementCollection
    private List<Integer> cuentasId; // Guarda todos los ids de las cuentas asociadas

    @Column (nullable = false)
    private Rol rol;

    public Usuario(@NotNull(message = "El nombre es obligatorio") String nombre, @NotNull(message = "El apellido es obligatorio") String apellido, @NotNull(message = "El email es obligatorio") String email, @NotNull(message = "El nroCelular es obligatorio") Integer nroCelular) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.nroCelular = nroCelular;
    }

    public Usuario(@NotNull(message = "El nombre es obligatorio") String nombre, @NotNull(message = "El apellido es obligatorio") String apellido, @NotNull(message = "El email es obligatorio") String email, @NotNull(message = "El nroCelular es obligatorio") Integer nroCelular, @NotNull(message = "El roll es obligatorio") Rol rol) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.nroCelular = nroCelular;
    this.rol = rol;
    }
}
