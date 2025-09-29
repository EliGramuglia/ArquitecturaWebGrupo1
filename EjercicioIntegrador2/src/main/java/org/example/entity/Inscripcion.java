package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ToString(exclude = {"carrera", "estudiante"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inscripcion {
    @EmbeddedId
    private InscripcionId idInscripcion;

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

    @Column (name = "fecha_graduacion")
    private LocalDate fechaGraduacion; //si null no esta graduado


    public void setFechaGraduacion(LocalDate fechaGraduacion) {
        if(fechaGraduacion.isAfter(fechaInscripcion)) {
            this.fechaGraduacion = fechaGraduacion;
        }
    }

    public Inscripcion(Estudiante e, Carrera c) {
        this.estudiante = e;
        this.carrera = c;
        this.fechaInscripcion = LocalDate.now();
        this.idInscripcion = new InscripcionId(e.getLU(), c.getIdCarrera());
    }

}