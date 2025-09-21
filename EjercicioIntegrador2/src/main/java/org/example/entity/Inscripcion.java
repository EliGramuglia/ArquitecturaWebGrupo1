package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_inscripcion")
    private Integer idInscripcion;

    @Column
    private Integer antiguedad;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "id_carrera")
    private Carrera carrera;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "LU")
    private Estudiante estudiante;

    @Column (name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @Column
    private boolean graduado;
}
