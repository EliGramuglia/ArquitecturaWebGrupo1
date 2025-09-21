package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numLibreta; // libreta universitaria

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private LocalDate fechaNacimiento;

    @Column
    private String genero;

    @Column
    private Integer dni;

    @Column (name = "ciudad_residencia")
    private String ciudadResidencia;

    @OneToMany // Indicar quien es el dueño de la relación
    private List<Inscripcion> inscripciones;



}
