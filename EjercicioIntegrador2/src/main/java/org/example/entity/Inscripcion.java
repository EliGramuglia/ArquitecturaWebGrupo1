package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInscripcion;

    @Column
    private Integer antiguedad;

    @Column
    private Carrera carrera;

    @ManyToOne
    @JoinColumn (nullable = false)
    private Estudiante estudiante;

    @Column
    private LocalDate fechaInscripcion;

    @Column
    private LocalDate fechaFinal;

    @Column
    private boolean graduado;
}
