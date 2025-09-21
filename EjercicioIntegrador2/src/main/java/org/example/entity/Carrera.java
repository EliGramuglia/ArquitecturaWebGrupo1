package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_carrera")
    private Integer idCarrera;

    @Column
    private String nombre;

    @Column
    private Integer duracion;

    @OneToMany (mappedBy = "idInscripcion") // Por defecto Lazy
    private List<Inscripcion> alumnosInscriptos;


}
