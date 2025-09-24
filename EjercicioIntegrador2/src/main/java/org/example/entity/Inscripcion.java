package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = {"carrera", "estudiante"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inscripcion {
    @EmbeddedId
    private InscripcionId idInscripcion;

    @Column
    private Integer antiguedad;
    //calcular con fechainscripcion

    @MapsId("idCarrera") // vincula la parte carreraId del embeddable con Carrera.id
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "id_carrera", nullable = false)
    private Carrera carrera;

    @MapsId("LU")
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "LU", nullable=false)
    private Estudiante estudiante;

    @Column (name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @Column
    private LocalDate fechaGraduacion; //si null no esta graduado
}
