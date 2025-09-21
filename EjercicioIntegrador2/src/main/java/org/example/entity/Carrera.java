package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

    @Column
    private String nombre;

    @Column
    private Integer duracion;
}
