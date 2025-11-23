package org.example.monopatin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.monopatin.utils.EstadoMonopatin;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monopatines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Monopatin {
    @Id
    private String id; // Mongo genera ObjectId por defecto

    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private EstadoMonopatin estadoMonopatin;
    private Integer horasUso;

    public Monopatin(Integer latitud, Integer longitud, Integer kmRecorridos, EstadoMonopatin estadoMonopatin) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.kmRecorridos = kmRecorridos;
        this.estadoMonopatin = estadoMonopatin;
    }

}