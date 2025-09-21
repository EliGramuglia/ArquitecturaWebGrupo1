package org.example.entity;

import jakarta.persistence.*;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInscripcion;

    @Column
    private Integer antiguedad;

    //Va a tener un estudiante y una carrea (crear relaciones)
}
