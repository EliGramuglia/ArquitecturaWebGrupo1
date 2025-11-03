package org.example.viaje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = true)
    private LocalTime fin;

    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;

    public Pausa(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
}
