package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estudiante {
    @Id
    private Integer dni; // Id -> en los csv el dni es el ID

    @Column (unique = true, nullable = false)
    private Integer LU;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column (name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column
    private String genero;

    @Column (name = "ciudad_residencia")
    private String ciudadResidencia;

    @OneToMany (mappedBy = "estudiante", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Inscripcion> inscripciones;

    @Override
    public String toString() {
        return dni + " " + nombre + " " + apellido + ", Género: " + genero +" LU: " + LU;
    }

    public Estudiante(Integer dni, String nombre, String apellido, LocalDate fechaNacimiento, String genero, String ciudadResidencia, Integer LU) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.dni = dni;
        this.ciudadResidencia = ciudadResidencia;
        this.LU = LU;
    }

}
