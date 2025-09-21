package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer LU; // libreta universitaria

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column (name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column
    private String genero;

    @Column
    private Integer dni;

    @Column (name = "ciudad_residencia")
    private String ciudadResidencia;

    @OneToMany (mappedBy = "idInscripcion")
    private List<Inscripcion> inscripciones;

}
