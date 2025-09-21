package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrera;

    @Column
    private String nombre;

    @Column
    private Integer duracion;

    @Column
    private List<Estudiante> estudiantes;
}
